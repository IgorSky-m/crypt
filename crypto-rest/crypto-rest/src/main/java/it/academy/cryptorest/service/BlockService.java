package it.academy.cryptorest.service;

import it.academy.cryptorest.exception.CreateException;
import it.academy.cryptorest.exception.NotFoundException;
import it.academy.cryptorest.exception.NullEntityException;
import it.academy.cryptorest.pojo.Block;
import it.academy.cryptorest.repository.BlockRepository;
import it.academy.cryptorest.rest.BlockController;
import it.academy.cryptorest.rest.BlockDataController;
import it.academy.cryptorest.rest.BlockTransactionController;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@Service
public class BlockService {

    @Autowired
    private final BlockRepository repository;

    public EntityModel<Block> toEntityModel(Block block) {
        if (isBlockEmpty(block)) throw new NullEntityException(this.getClass(),"toEntityModel");
        return EntityModel.of(block,
                linkTo(methodOn(BlockController.class).one(block.getHash())).withSelfRel(),
                linkTo(methodOn(BlockController.class).all()).withRel("blocks"),
                linkTo(methodOn(BlockDataController.class).one(block.getBlockDataHash())).withRel("block-data"),
                linkTo(methodOn(BlockTransactionController.class).allByBlockHash(block.getHash())).withRel("block-transactions")
                );
    }

    public Block save(Block block) {
        if (isBlockEmpty(block)) throw new NullEntityException(this.getClass(),"save");
        if (repository.getOne(block.getHash()).equals(block)) throw new CreateException(block.getHash(),Block.class);
        return repository.save(block);
    }

    public Block update(Block block) {
        if (isBlockEmpty(block)) throw new NullEntityException(this.getClass(),"update");
        return repository.save(block);
    }

    public List<Block> findAll(){
        return repository.findAll();
    }


    public Block findById(String id) {
        return repository.findById(id)
                .orElseThrow(()-> new NotFoundException(id,Block.class));
    }

    private boolean isBlockEmpty (Block block) {
        return block == null;
    }

    public Block getLastBlock() {
        return repository.findLastBlock();
    }
}
