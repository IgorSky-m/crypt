package it.academy.cryptorest.service;

import it.academy.cryptorest.Status;
import it.academy.cryptorest.component.CoinCheckComponent;
import it.academy.cryptorest.component.HashCalculator;
import it.academy.cryptorest.exception.CoinTransferException;
import it.academy.cryptorest.exception.NotFoundException;
import it.academy.cryptorest.exception.NullEntityException;
import it.academy.cryptorest.pojo.BlockTransaction;
import it.academy.cryptorest.repository.BlockTransactionRepository;
import it.academy.cryptorest.rest.BlockController;
import it.academy.cryptorest.rest.BlockTransactionController;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidatorFactory;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Getter
@Setter
public class BlockTransactionService {
    @Autowired
    private BlockTransactionRepository repository;

    @Autowired
    private CoinCheckComponent coinCheckComponent;

    @Autowired
    private HashCalculator hashCalculator;


    @Autowired
    private ValidatorFactory validatorFactory;

    @Value("${current.block.reward}")
    private Long blockReward;

    @Value("${public.key}")
    private String publicBlockKey;





    public EntityModel<BlockTransaction> toEntityModel(BlockTransaction blockTransaction) {
        if (isEmpty(blockTransaction)) throw new NullEntityException(this.getClass(), "toEntityModel");
        EntityModel<BlockTransaction> entityModel =  EntityModel.of(blockTransaction,
                linkTo(methodOn(BlockTransactionController.class).one(blockTransaction.getHash())).withSelfRel(),
                linkTo(methodOn(BlockTransactionController.class).all()).withRel("transactions")
                );

        Status blockTransactionStatus = blockTransaction.getStatus();
        if (blockTransactionStatus == Status.IN_PROGRESS) {
            entityModel.add(linkTo(methodOn(BlockTransactionController.class).approve(blockTransaction.getHash())).withRel("approve"));
            entityModel.add(linkTo(methodOn(BlockTransactionController.class).reject(blockTransaction.getHash())).withRel("reject"));
            entityModel.add(linkTo(methodOn(BlockTransactionController.class).cancel(blockTransaction.getHash())).withRel("cancel"));
        } else if (blockTransactionStatus == Status.APPROVED) {
            entityModel.add(linkTo(methodOn(BlockTransactionController.class).save(blockTransaction.getHash(),blockTransaction)).withRel("save"));
            entityModel.add(linkTo(methodOn(BlockTransactionController.class).cancel(blockTransaction.getHash())).withRel("cancel"));
        } else if (blockTransactionStatus == Status.SAVED) {
            entityModel.add(linkTo(methodOn(BlockController.class).one(blockTransaction.getBlockHash())).withRel("block"));
            entityModel.add(linkTo(methodOn(BlockTransactionController.class).allByBlockHash(blockTransaction.getBlockHash())).withRel("block-transactions"));
        }
        return entityModel;
    }

    public List<BlockTransaction> findAll(){
        return repository.findAll();
    }

    private boolean isEmpty(BlockTransaction blockTransaction) {
        return blockTransaction == null;
    }

    public BlockTransaction findById(String id) {
        return repository.findById(id).orElseThrow(()-> new NotFoundException(id,BlockTransaction.class));
    }

    public List<BlockTransaction> findAllTransactionsInBlock(String blockId){
        return repository.findAllByBlockHash(blockId);

    }

    public BlockTransaction save(BlockTransaction blockTransaction) {
        return repository.save(blockTransaction);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BlockTransaction update(BlockTransaction blockTransaction) {
        BlockTransaction transactionTest;
        do {
        repository.deleteById(blockTransaction.getHash());
        repository.flush();
            transactionTest = repository.findById(blockTransaction.getHash()).orElse(null);
        } while (transactionTest != null );
        return repository.saveAndFlush(blockTransaction);
    }

    public List<BlockTransaction> findByHashAndSecretKey(String hash, String secretKey) {
        return repository.findByBlockHashAndSignature(hash,secretKey);
    }


    public List<BlockTransaction> findAllByStatus(Status blockStatus) {
        return repository
                .findAllByStatus(blockStatus);
    }

    public List<BlockTransaction> findAllWalletTransactions(String walletId, String signature) {

        return repository.findAllByWalletIdToOrSignature(walletId,signature);

    }

    public BlockTransaction checkStatus(BlockTransaction blockTransaction, Status status) {
            try {
                if (blockTransaction.getSignature() == null) throw new NotFoundException("not found",BlockTransaction.class);
                if (blockTransaction.getSignature().equals(publicBlockKey)) {

                    checkBlockTransaction(blockTransaction);

                } else {
                    BlockTransaction inBaseTransaction = findById(blockTransaction.getHash());
                    if (equals(blockTransaction,inBaseTransaction)) {
                        blockTransaction.setStatus(status);
                    } else {
                        blockTransaction.setStatus(Status.REJECTED);
                    }
                }

            } catch (NotFoundException e) {
                blockTransaction.setStatus(Status.NOT_SIGNED);
            }
            return blockTransaction;
        }

     public BlockTransaction createCheckStatus(BlockTransaction blockTransaction) {
         StringBuilder builder = new StringBuilder();
         try {
             builder.append(blockTransaction.getValue())
                     .append(blockTransaction.getSignature())
                     .append(blockTransaction.getTimestamp())
                     .append(blockTransaction.getWalletIdTo());

             if (blockTransaction.getSignature().equals(publicBlockKey)) {

                 checkBlockTransaction(blockTransaction);

             } else {
                 coinCheckComponent.checkAmount(blockTransaction);
                 blockTransaction.setStatus(Status.APPROVED);
             }

         } catch (CoinTransferException e) {
             blockTransaction.setStatus(Status.REJECTED);
         } catch (NotFoundException e) {
             blockTransaction.setStatus(Status.NOT_SIGNED);
         } finally {
             if (blockTransaction.getHash() == null) {
                 blockTransaction.setHash(hashCalculator.createSHA3Hash(builder.toString()));
             }
         }
         return blockTransaction;

     }
        private void checkBlockTransaction(BlockTransaction blockTransaction){
            if (!blockTransaction.getValue().equals(blockReward)) {
                blockTransaction.setStatus(Status.REFUSED);
            } else {
                blockTransaction.setStatus(Status.SAVED);
            }
        }

    public boolean equals(BlockTransaction transaction1, BlockTransaction transaction2) {

        return transaction1.getHash().equals(transaction2.getHash())
                && transaction1.getStatus().equals(transaction2.getStatus())
                && transaction1.getValue().equals(transaction2.getValue())
                && transaction1.getSignature().equals(transaction2.getSignature())
                && transaction1.getWalletIdTo().equals(transaction2.getWalletIdTo())
                && transaction1.getTimestamp() == transaction2.getTimestamp();

    }

}