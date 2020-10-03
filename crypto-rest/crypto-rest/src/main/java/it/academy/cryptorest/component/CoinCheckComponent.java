package it.academy.cryptorest.component;


import it.academy.cryptorest.exception.CoinTransferException;
import it.academy.cryptorest.exception.NotFoundException;
import it.academy.cryptorest.pojo.BlockTransaction;
import it.academy.cryptorest.pojo.Wallet;
import it.academy.cryptorest.repository.BlockTransactionRepository;
import it.academy.cryptorest.repository.WalletRepository;
import it.academy.cryptorest.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Service
@Aspect
public class CoinCheckComponent {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private BlockTransactionRepository blockTransactionRepository;

    @Autowired
    private ApplicationContext context;

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    private Logger log = Logger.getLogger(CoinCheckComponent.class.getName());


    public boolean transfer(BlockTransaction transaction){
        return false;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void checkAmount(BlockTransaction transaction){

        Wallet wallet =  walletService.findByKey(transaction.getSignature()).orElseThrow(() -> new NotFoundException("cant't find wallet", CoinCheckComponent.class));
        String walletFromId =wallet.getId();
        String amount = walletService.getWalletAmount(walletFromId);

        if (Long.parseLong(amount) < transaction.getValue()) {
           throw new CoinTransferException(transaction.getValue().toString());
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BigDecimal depositCoin(String walletId, BigDecimal value){

        return null;
    }

}
