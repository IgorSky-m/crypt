package it.academy.cryptorest.rest;

import it.academy.cryptorest.Status;
import it.academy.cryptorest.pojo.BlockData;
import it.academy.cryptorest.service.BlockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class BlockDataController {

    @Autowired
    BlockDataService service;

    @GetMapping("/block-data")
    public CollectionModel<EntityModel<BlockData>> all() {
        return CollectionModel.of(
                service.findAll().stream()
                        .map(service::toEntityModel)
                        .collect(Collectors.toList()),
                linkTo(methodOn(BlockDataController.class).all()).withSelfRel()
        );
    }

    @GetMapping("/block-data/{id}")
    public EntityModel<BlockData> one(
            @PathVariable String id
    ) {
        BlockData data = service.findById(id);
        return service.toEntityModel(data);
    }

    @DeleteMapping("/blocks/{blockId}/rejected")
    public ResponseEntity<?> reject(
            @PathVariable String blockId
    ){
        BlockData blockData = service.findByBlockHash(blockId);
        if (blockData.getStatus() == Status.IN_PROGRESS) {
            blockData.setStatus(Status.REJECTED);
            return ResponseEntity.ok(service.toEntityModel(service.update(blockData)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't reject a block that is in the " + blockData.getStatus()+ " status"));

    }

    @PutMapping("/blocks/{blockId}/approved")
    public ResponseEntity<?> approve(
            @PathVariable String blockId
    ){
        BlockData blockData = service.findById(blockId);
        if (blockData.getStatus() == Status.IN_PROGRESS) {
            blockData.setStatus(Status.APPROVED);
            return ResponseEntity.ok(service.toEntityModel(service.update(blockData)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't approve a block that is in the " + blockData.getStatus() + " status"));
    }

}
