package it.academy.cryptorest.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WalletAmount {
    private String id;
    private String amount;
}
