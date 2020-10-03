package it.academy.cryptorest.rest;

import it.academy.cryptorest.Status;
import it.academy.cryptorest.aspect.ControlTransactionStatus;
import it.academy.cryptorest.aspect.UserParamCheck;
import it.academy.cryptorest.exception.NotFoundException;
import it.academy.cryptorest.pojo.BlockTransaction;
import it.academy.cryptorest.service.BlockTransactionService;
import it.academy.cryptorest.service.WalletService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RestController
@Getter
@Setter
public class BlockTransactionController {

    private static final  String TITLE = "Method not allowed";
    private static final  String DETAIL = "You can't %s a transaction that is in the %s status";

    @Autowired
    private BlockTransactionService blockTransactionService;

    @Autowired
    private WalletService walletService;


    @GetMapping("/transactions")
    public CollectionModel<EntityModel<BlockTransaction>> all(){
        return CollectionModel.of(
                blockTransactionService.findAll().stream()
                        .map(blockTransactionService::toEntityModel)
                        .collect(Collectors.toList()),
                linkTo(methodOn(BlockTransactionController.class).all()).withSelfRel()
        );
    }

    @GetMapping("/transactions/{id}")
    public EntityModel<BlockTransaction> one(
            @PathVariable String id
    ){
        BlockTransaction blockTransaction = blockTransactionService.findById(id);
        return blockTransactionService.toEntityModel(blockTransaction);
    }

    @GetMapping("/transactions/blockHash_{blockHash}")
    public CollectionModel<EntityModel<BlockTransaction>> allByBlockHash(
            @PathVariable String blockHash
    ){
        return CollectionModel.of(blockTransactionService.findAllTransactionsInBlock(blockHash).stream()
                        .map(blockTransactionService::toEntityModel)
                        .collect(Collectors.toList()),
                linkTo(methodOn(BlockTransactionController.class).allByBlockHash(blockHash)).withSelfRel(),
                linkTo(methodOn(BlockController.class).one(blockHash)).withRel("block"));
    }

    @GetMapping("/transactions/status_{blockStatus}")
    public ResponseEntity<List<BlockTransaction>> allByStatus(
            @PathVariable Status blockStatus
    ){

        return   ResponseEntity.ok(blockTransactionService.findAllByStatus(blockStatus));
    }



    @Transactional
    @PutMapping("/transactions/{id}/saved")
    @UserParamCheck
    public ResponseEntity<?> save(
            @PathVariable String id,
            @RequestBody BlockTransaction blockTransaction
    ){
        BlockTransaction inBaseBlockTransaction;

        try {
            inBaseBlockTransaction = blockTransactionService.findById(id);
            blockTransaction = blockTransactionService.checkStatus(blockTransaction,Status.SAVED);

            blockTransactionService.update(blockTransaction);

                return ResponseEntity.ok(blockTransactionService.toEntityModel(blockTransactionService.update(inBaseBlockTransaction)));


        }catch (NotFoundException e) {
            return create(blockTransaction);

        }


    }

    @PutMapping("/transactions/{id}/canceled")
    public ResponseEntity<?> cancel(
            @PathVariable String id
    ){
        BlockTransaction blockTransaction = blockTransactionService.findById(id);
        if (blockTransaction.getStatus() == Status.IN_PROGRESS) {
            blockTransaction.setStatus(Status.CANCELED);
            return ResponseEntity.ok(blockTransactionService.toEntityModel(blockTransactionService.update(blockTransaction)));
        }


        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle(TITLE)
                        .withDetail(String.format(DETAIL,"cancel",blockTransaction.getStatus())));
    }



    @ControlTransactionStatus
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @PostMapping("/transactions")
    public ResponseEntity<?> create(
            @RequestBody BlockTransaction blockTransaction
    ){
        blockTransaction = blockTransactionService.createCheckStatus(blockTransaction);


            BlockTransaction newBlockTransaction = blockTransactionService.save(blockTransaction);

            return ResponseEntity.created(linkTo(methodOn(BlockTransactionController.class).one(newBlockTransaction.getHash())).toUri())
                    .body(blockTransactionService.toEntityModel(blockTransaction));

    }

    @PutMapping("/transactions/{id}/approved")
    public ResponseEntity<?> approve(
            @PathVariable String id
    ){
        BlockTransaction blockTransaction = blockTransactionService.findById(id);
        if (blockTransaction.getStatus() == Status.IN_PROGRESS) {
            blockTransaction.setStatus(Status.APPROVED);
            return ResponseEntity.ok(blockTransactionService.toEntityModel(blockTransactionService.update(blockTransaction)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle(TITLE) //
                        .withDetail(String.format(DETAIL,"approve",blockTransaction.getStatus())));
    }




    @DeleteMapping("/transactions/{id}/deleted")
    public ResponseEntity<?> reject(
            @PathVariable String id
    ){
        BlockTransaction blockTransaction = blockTransactionService.findById(id);
        if (blockTransaction.getStatus() == Status.IN_PROGRESS) {
            blockTransaction.setStatus(Status.DELETED);
            return ResponseEntity.ok(blockTransactionService.toEntityModel(blockTransactionService.update(blockTransaction)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle(TITLE)
                        .withDetail(String.format(DETAIL,"reject",blockTransaction.getStatus())));


    }


    @GetMapping("/wallets/{walletId}/transactions")
    public ResponseEntity<?> allWalletTransactions(
            @PathVariable String walletId
    ){
        String walletSecretKey;
        try {
             walletSecretKey = walletService.findById(walletId).getSecretKey();
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle(NotFoundException.class.getName())
                            .withDetail("Wallet not found"));
        }


         return ResponseEntity.ok(blockTransactionService.findAllWalletTransactions(walletId,walletSecretKey));
    }



}
