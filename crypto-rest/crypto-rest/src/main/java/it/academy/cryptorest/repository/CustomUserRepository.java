package it.academy.cryptorest.repository;

import it.academy.cryptorest.pojo.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser,String> {
    CustomUser findByUserName(String userName);
    CustomUser findByEmailAddress(String emailAddress);
    CustomUser findByEmailAddressEndingWith(String suffix);
    CustomUser findByMobile(String mobile);
}
