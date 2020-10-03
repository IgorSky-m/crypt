package it.academy.miner.pojo;


import it.academy.miner.Status;
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
    private String dataHash;


    @Value("0x1")
    private String version; //учавств


    @Value("0")
    private int transactionCount; // количество транзакций в блоке учавст

    @Enumerated(EnumType.STRING)
    private Status status; // не учавствует

    @Column(name = "transactions_merkle_root")
    private String transactionsMerkleRoot; // merkle_root транзакций учавств


    @Column(name = "block_hash")
    private String blockHash; // не учавствует



}
