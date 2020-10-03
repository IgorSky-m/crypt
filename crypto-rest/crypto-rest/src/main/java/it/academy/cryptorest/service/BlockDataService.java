package it.academy.cryptorest.service;

import it.academy.cryptorest.Status;
import it.academy.cryptorest.exception.CreateException;
import it.academy.cryptorest.exception.NotFoundException;
import it.academy.cryptorest.exception.NullEntityException;
import it.academy.cryptorest.pojo.BlockData;
import it.academy.cryptorest.repository.BlockDataRepository;
import it.academy.cryptorest.rest.BlockController;
import it.academy.cryptorest.rest.BlockDataController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BlockDataService {

    @Autowired
    BlockDataRepository repository;

    public EntityModel<BlockData> toEntityModel(BlockData blockData) {
        if (isEmpty(blockData)) throw new NullEntityException(this.getClass(),"toEntityModel");
        EntityModel<BlockData> entityModel =  EntityModel.of(blockData,
                linkTo(methodOn(BlockDataController.class).one(blockData.getDataHash())).withSelfRel(),
                linkTo(methodOn(BlockController.class).one(blockData.getBlockHash())).withRel("block"));

        if (blockData.getStatus().equals(Status.IN_PROGRESS)) {
            entityModel.add(linkTo(methodOn(BlockDataController.class).approve(blockData.getBlockHash())).withRel("approve"));
            entityModel.add(linkTo(methodOn(BlockDataController.class).reject(blockData.getBlockHash())).withRel("reject"));
        }

        return entityModel;

    }

    public BlockData save(BlockData data) {
        if (isEmpty(data)) throw new NullEntityException(BlockData.class,"save");
        else if (repository.findById(data.getDataHash()).orElse(null) != null ) throw new CreateException(data.getDataHash(),BlockData.class);
        return repository.save(data);
    }

    public BlockData findByBlockHash(String blockHash) {
        return repository.findByBlockHash(blockHash).orElseThrow();
    }



    private boolean isEmpty(BlockData blockData) {
        return blockData == null;
    }

    public BlockData findById(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(id,BlockData.class));
    }


    public BlockData update(BlockData data) {
        BlockData savedData = repository.findById(data.getDataHash()).orElseThrow();
        if (savedData.equals(data)) return data;
        return repository.save(data);
    }

    public List<BlockData> findAll() {
        return repository.findAll();
    }
}
