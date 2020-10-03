package it.academy.miner.pojo;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Block implements Serializable {
    @Id
    @Column(name = "block_hash")
    private String hash;

    @Column(name = "timestamp")
    private long timestamp; // учавств

    @Column(name = "previous_block_hash")
    private String previousHash; // учавств

    @Column(name = "block_data_hash")
    private String blockDataHash; // учавств

    @Value("0")
    private long nonce; // учавств

}
