package it.academy.cryptorest.repository;

import it.academy.cryptorest.Status;
import it.academy.cryptorest.pojo.BlockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockTransactionRepository extends JpaRepository<BlockTransaction,String> {
    List<BlockTransaction> findAllByBlockHash(String blockHash);

    List<BlockTransaction> findAllByStatus(Status status);

    List<BlockTransaction> findAllByWalletIdTo(String walletId);

    List<BlockTransaction> findAllBySignature(String signature);

    List<BlockTransaction> findAllByWalletIdToOrSignature(String id, String secretKey);

    List<BlockTransaction> findByBlockHashAndSignature(String hash, String signature);


}
