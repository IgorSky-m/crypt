package it.academy.miner;

import it.academy.miner.pojo.Block;
import it.academy.miner.pojo.BlockTransaction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

@Service
@Getter
@Setter
public class BackendConnector {

    private final RestTemplate restTemplate;
    private final Logger logger;

    @Value("${server.url}")
    private String backendUrl;

    public BackendConnector(RestTemplateBuilder builder) {
        restTemplate = builder.build();
        logger = Logger.getLogger(BackendConnector.class.getName());
    }

    public ResponseEntity<String> getOneRequest(String endPoint) {
        return restTemplate.getForEntity(backendUrl+endPoint,String.class);
    }

    public ResponseEntity<Block> getBlockRequest(String endPoint) {
        return restTemplate.getForEntity(
                backendUrl + endPoint, Block.class);
    }

    public ResponseEntity<List> getListRequests(String endPoint) {
        return restTemplate.getForEntity(
                backendUrl + endPoint, List.class);
    }


    public void postRequest(BlockTransaction transaction, String endPoint) {
            restTemplate.put(backendUrl+endPoint,transaction,BlockTransaction.class);
    }
}


