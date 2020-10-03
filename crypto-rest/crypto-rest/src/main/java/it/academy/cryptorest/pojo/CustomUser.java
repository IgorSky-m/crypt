package it.academy.cryptorest.pojo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CustomUser implements Serializable {
//Изменить имя на кастомное
    @Id
    private String id;

    private String userName;

    private String userPassword;

    private String emailAddress;

    private String mobile;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<CustomUserRole> customUserRoles;

}
