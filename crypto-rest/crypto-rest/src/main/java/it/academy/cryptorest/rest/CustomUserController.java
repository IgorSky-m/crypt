package it.academy.cryptorest.rest;

import it.academy.cryptorest.aspect.UserParamCheck;
import it.academy.cryptorest.pojo.CustomUser;
import it.academy.cryptorest.pojo.CustomUserRole;
import it.academy.cryptorest.service.CustomUserService;
import it.academy.cryptorest.utill.JwtUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@Setter
@Getter
public class CustomUserController {

    @Value("${check.message.if.exist}")
    private String alreadyExistMsgStr;

    @Autowired
    private JwtUtil jwtUtil;



    @Autowired
    private CustomUserService customUserService;

    @GetMapping("/users")
    public CollectionModel<EntityModel<CustomUser>> all(){
        return CollectionModel.of(
                customUserService.findAll().stream()
                .map(customUserService::toEntityModel)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/users/{id}")
    public EntityModel<CustomUser> one(
            @PathVariable String id
    ){

        CustomUser customUser = customUserService.findOneById(id);
        return customUserService.toEntityModel(customUser);
    }




    @GetMapping("/users/info")
    public ResponseEntity<?> oneByToken(
            HttpServletRequest request, HttpServletResponse response
    ) {

            String userName = jwtUtil.getUsernameFromHttpRequest(request);
            CustomUser user = customUserService.findUserByName(userName);
            return ResponseEntity.status(HttpStatus.OK).body(customUserService.toEntityModel(user));
    }



    @UserParamCheck
    @PostMapping(value = "/users")
    public ResponseEntity<?> create(
            @RequestBody CustomUser customUser
    ) {
        if (
                !customUser.getUserName().equals(alreadyExistMsgStr) &&
                        !customUser.getEmailAddress().equals(alreadyExistMsgStr) &&
                        !customUser.getMobile().equals(alreadyExistMsgStr)
        ) {
            customUser.setCustomUserRoles(
                    Set.of(CustomUserRole.builder()
                    .userRole("USER")
                    .build())
            );

            CustomUser savedCustomUser = customUserService.create(customUser);

            return ResponseEntity.created(
                    linkTo(methodOn(CustomUserController.class).one(savedCustomUser.getId())).toUri()
            ).body(customUserService.toEntityModel(customUser));
        }
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(customUser);
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<EntityModel<CustomUser>> update(
            @PathVariable String id,
            @RequestBody CustomUser customUser
    ){

        CustomUser savedCustomUser = customUserService.update(id, customUser);
        return ResponseEntity.ok(customUserService.toEntityModel(savedCustomUser));
    }


    @DeleteMapping("users/{id}/delete")
    public ResponseEntity<EntityModel<CustomUser>> delete(
            @PathVariable String id
    ){

        CustomUser deletedCustomUser = customUserService.findOneById(id);
        customUserService.deleteById(id);
        return ResponseEntity.ok(customUserService.toEntityModel(deletedCustomUser));

    }


    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException, HttpStatus.INTERNAL_SERVER_ERROR);
    }






}
