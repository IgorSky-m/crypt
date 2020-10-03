package it.academy.cryptorest.service;

import it.academy.cryptorest.Status;
import it.academy.cryptorest.component.Encoder;
import it.academy.cryptorest.component.HashCalculator;
import it.academy.cryptorest.exception.CreateException;
import it.academy.cryptorest.exception.NotFoundException;
import it.academy.cryptorest.exception.NullEntityException;
import it.academy.cryptorest.pojo.BlockTransaction;
import it.academy.cryptorest.pojo.UserWallet;
import it.academy.cryptorest.pojo.Wallet;
import it.academy.cryptorest.pojo.WalletAmount;
import it.academy.cryptorest.repository.BlockTransactionRepository;
import it.academy.cryptorest.repository.UserWalletRepository;
import it.academy.cryptorest.repository.WalletRepository;
import it.academy.cryptorest.rest.WalletController;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Getter
@Setter
public class WalletService {

    @Autowired
    private HashCalculator hashCalculator;

    @Autowired
    private Encoder encoder;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserWalletRepository userWalletRepository;

    @Autowired
    private BlockTransactionRepository blockTransactionRepository;

    public EntityModel<Wallet> toEntityModel(Wallet wallet){
        return EntityModel.of(wallet,
                linkTo(methodOn(WalletController.class).one(wallet.getId())).withSelfRel(),
                linkTo(methodOn(WalletController.class).all()).withRel("wallets")
                );
    }

    @Transactional(readOnly = true)
    public String getWalletAmount(String walletId) {// walletID + Secret key peredat'
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new  NotFoundException(walletId,Wallet.class));
        Long input =  blockTransactionRepository.findAllByWalletIdTo(walletId)
                .stream().filter( s -> (
                        s.getStatus().equals(Status.IN_PROGRESS)
                                || s.getStatus().equals(Status.APPROVED)
                                || s.getStatus().equals(Status.SAVED)))
                .mapToLong(BlockTransaction::getValue).sum();
        Long output = blockTransactionRepository.findAllBySignature(wallet.getSecretKey())
                .stream().filter( s -> (
                        s.getStatus().equals(Status.IN_PROGRESS)
                        || s.getStatus().equals(Status.APPROVED)
                        || s.getStatus().equals(Status.SAVED)))
                .mapToLong(BlockTransaction::getValue).sum();
        long result = input - output;
        if (result < 0) throw new InvalidParameterException("Amount is negative");
        return String.valueOf(result);
    }




    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }



    public Wallet findById(String id) {
        return walletRepository.findById(id).orElseThrow(() -> new NotFoundException(id,Wallet.class));
    }


    public Wallet save (Wallet wallet) {
        if (isEmpty(wallet)) throw new  NullEntityException(Wallet.class,"save");
        else if (walletRepository.findById(wallet.getId())
                .orElse(null) != null ) throw new CreateException(wallet.getId(),"already exist",Wallet.class);
        return walletRepository.save(wallet);
    }

    public Wallet update (Wallet newWallet, String id) {
        if (isEmpty(newWallet)) throw new NullEntityException(Wallet.class,"update");
        Wallet wallet = walletRepository.findById(id).orElseThrow(()-> new NotFoundException(id,Wallet.class));
        return walletRepository.save(wallet);
    }

    public Optional<Wallet> findByKey(String key){
        return walletRepository.findBySecretKey(key);
    }

    public boolean isEmpty(Wallet wallet){
        return wallet == null;
    }

    public void deleteById(String id) {
        walletRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<WalletAmount> findAllByUserId(String userId) {
        List<String> list = userWalletRepository
                .findAllByUserId(userId)
                .stream()
                .map(UserWallet::getWalletId)
                .collect(Collectors.toList());
        List<Wallet> wallets = walletRepository.findAllByIdIn(list);
        List<WalletAmount> result = new ArrayList<>();
        for (Wallet wallet : wallets) {
            result.add(WalletAmount.builder()
                    .id(wallet.getId())
                    .amount(getWalletAmount(wallet.getId()))
                    .build());
        }
        return result;
    }


    public Wallet generateWallet(){
        Random random = new Random();

        long timestamp = System.currentTimeMillis();

        Wallet wallet= Wallet.builder()
                .timestamp(timestamp)
                .id(hashCalculator
                        .createSHA3Hash(String.valueOf(timestamp + random.nextInt(Integer.MAX_VALUE))))
                .build();

        String secretKey = hashCalculator
                .createSHA1Hash(wallet.getId()+random.nextInt(Integer.MAX_VALUE));

        wallet.setSecretKey(secretKey);

        return wallet;
    }
}
