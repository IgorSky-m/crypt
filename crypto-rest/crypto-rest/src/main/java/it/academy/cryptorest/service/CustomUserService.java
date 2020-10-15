package it.academy.cryptorest.service;

import it.academy.cryptorest.exception.CreateException;
import it.academy.cryptorest.exception.NotFoundException;
import it.academy.cryptorest.pojo.CustomUser;
import it.academy.cryptorest.repository.CustomUserRepository;
import it.academy.cryptorest.rest.CustomUserController;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Getter
@Setter
public class CustomUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${check.message.if.exist}")
    private String str;

    public EntityModel<CustomUser> toEntityModel(CustomUser customUser){
        return EntityModel.of(customUser,
                linkTo(methodOn(CustomUserController.class).one(customUser.getId())).withSelfRel(),
                linkTo(methodOn(CustomUserController.class).delete(customUser.getId())).withRel("deleted"),
                linkTo(methodOn(CustomUserController.class).update(customUser.getId(), customUser)).withRel("updated"),
                linkTo(methodOn(CustomUserController.class).all()).withRel("users"));

    }

    @Autowired
    CustomUserRepository repository;

    public List<CustomUser> findAll(){
        return repository.findAll();
    }

    public CustomUser findOneById(String id) {
        return repository.findById(id).orElseThrow(()-> new NotFoundException(id, CustomUser.class));
    }

    public CustomUser findUserByName(String name) {
        return repository.findByUserName(name);
    }

    public CustomUser create(CustomUser customUser) {

            try {
                findOneById(customUser.getId());
            } catch (NotFoundException e) {
                String encodedPassword = passwordEncoder.encode(customUser.getUserPassword());
                customUser.setUserPassword(encodedPassword);
                return repository.saveAndFlush(customUser);
            }
            throw new CreateException(customUser.getId(), CustomUser.class);
    }


    public CustomUser update (String id, CustomUser customUser) {
        CustomUser updatedCustomUser = repository.findById(id).orElseThrow(()-> new NotFoundException(id, CustomUser.class));
        if (updatedCustomUser.equals(customUser)) return updatedCustomUser;
        if (customUser.getId() != null && customUser.getId().equals(updatedCustomUser.getId())) {
            if (!customUser.getEmailAddress()
                    .equals(updatedCustomUser.getEmailAddress()) &&
                    customUser.getEmailAddress() != null) updatedCustomUser.setEmailAddress(updatedCustomUser.getEmailAddress());
            if (!(customUser.getMobile()
                    .equals(updatedCustomUser.getMobile()) &&
                    customUser.getMobile() != null)) updatedCustomUser.setMobile(customUser.getMobile());
            if (!customUser.getUserName()
                    .equals(updatedCustomUser.getUserName()) &&
                    customUser.getUserName() != null) updatedCustomUser.setUserName(customUser.getUserName());
            if (!customUser.getUserPassword().equals(updatedCustomUser.getUserPassword()) &&
                    customUser.getUserPassword() != null) {
                String encodedPassword = passwordEncoder.encode(customUser.getUserPassword());
                updatedCustomUser.setUserPassword(encodedPassword);
            }

        }
        return repository.save(updatedCustomUser);

    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }





    public CustomUser findUserByMobile(String id) {
        return repository.findByMobile(id);
    }
}
