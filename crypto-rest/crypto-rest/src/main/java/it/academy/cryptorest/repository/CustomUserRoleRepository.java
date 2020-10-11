package it.academy.cryptorest.repository;


import it.academy.cryptorest.pojo.CustomUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRoleRepository extends JpaRepository<CustomUserRole,String> {
}
