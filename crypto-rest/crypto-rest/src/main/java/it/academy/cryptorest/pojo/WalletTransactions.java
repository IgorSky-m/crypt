package it.academy.cryptorest.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class WalletTransactions {

    private String walletId;
    private List<BlockTransaction> transactions;

}
