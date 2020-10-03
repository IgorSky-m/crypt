package it.academy.cryptorest.pojo;


import it.academy.cryptorest.Status;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BlockData implements Serializable {

    @Id
    @Column(name = "block_data_hash")
    private String dataHash;

    @Column(name = "version")
    @Value("0x1")
    private String version; //учавств

    @Column(name = "transactions_count")
    @Value("0")
    private int transactionCount; // количество транзакций в блоке учавст

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status; //не учавствует

    @Column(name = "transaction_merkle_root")
    private String transactionsMerkleRoot; // merkle_root транзакций учавств

    @Column(name = "block_hash")
    private String blockHash; // не учавствует



}
