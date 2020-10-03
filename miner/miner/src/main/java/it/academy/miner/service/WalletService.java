package it.academy.miner.service;

import it.academy.miner.exception.StorageServiceException;
import it.academy.miner.pojo.Wallet;
import it.academy.miner.repository.WalletRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public String setWalletId(String walletId){
        String wallet;
        try{
            wallet = getWalletId();
        } catch (StorageServiceException e) {
            walletRepository.save(new Wallet(walletId));
            return walletId;
        }

        if (wallet.equals(walletId)) {
            return walletId;
        }

        walletRepository.deleteById(wallet);
        walletRepository.flush();
        walletRepository.findById(walletId);
        walletRepository.save(new Wallet(walletId));

        return walletId;

    }

    public String getWalletId() {
        Wallet wallet = walletRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new StorageServiceException("please, set wallet"));
        return wallet.getId();

    }

}
