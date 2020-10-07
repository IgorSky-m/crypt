package it.academy.cryptorest.rest;

import it.academy.cryptorest.pojo.Block;
import it.academy.cryptorest.service.BlockService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@Getter
@Setter
public class BlockController {

    @Autowired
    private BlockService service;



    @Value("${current.block.reward}")
    private String blockReward;


    @Value("${public.key}")
    private String blockKey;




    @GetMapping("/blocks")
    public CollectionModel<EntityModel<Block>> all(){
        return CollectionModel.of(
            service.findAll().stream()
                    .map(service::toEntityModel)
                    .collect(Collectors.toList()),
            linkTo(methodOn(BlockController.class).all()).withSelfRel()
        );
    }

    @GetMapping("/blocks/{id}")
    public EntityModel<Block> one(
            @PathVariable String id
    ){
        Block block = service.findById(id);
        return service.toEntityModel(block);
    }

    @GetMapping("/blocks/last")
    public ResponseEntity<Block> last(){
       Block block =  service.getLastBlock();
       return ResponseEntity.ok(block);
    }





    @PostMapping("/blocks")
    public ResponseEntity<EntityModel<Block>> create(
            @RequestBody Block block
    ){

        Block savedBlock = service.save(block);
        return ResponseEntity.created(linkTo(methodOn(BlockController.class).one(savedBlock.getHash())).toUri())
                .body(service.toEntityModel(block));
    }


    @GetMapping("/blocks/reward")
    public ResponseEntity<String> reward(){
        return ResponseEntity.ok(blockReward);
    }

    @GetMapping("/blocks/key")
    public ResponseEntity<String> publicKey(){
        return ResponseEntity.ok(blockKey);

    }







}
