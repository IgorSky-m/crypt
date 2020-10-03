package it.academy.miner.pojo;


import it.academy.miner.Status;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BlockTransaction implements Serializable {

    @Id
    @Column(name = "transaction_hash")
    private String hash;

    @Column(name = "wallet_id_to")
    private String walletIdTo; //учавствует

    @Column(name = "secret_key")
    private String signature; // учавствует

    @Value("0")
    private Integer value; // сумма транзакции учавствует

    @Column(name = "timestamp") //учавствует
    private long timestamp;

    @Enumerated(EnumType.STRING)// не учавствует в хеше
    private Status status; //заменить енамом. только сервер может его менять

    @Column(name = "block_hash") //не учавствует в хеше
    private String blockHash;



}

