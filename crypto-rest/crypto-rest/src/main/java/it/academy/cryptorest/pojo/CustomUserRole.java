package it.academy.cryptorest.pojo;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CustomUserRole implements Serializable {

    @Id
    private long id;

    private String userRole;

}
