package com.halilkrkn.usersapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = ?1")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
//@NamedQuery(name = "User.deleteById", query = "DELETE FROM User u WHERE u.id = ?1")
public class User implements Serializable {

    @Id
    @SequenceGenerator(
            name = "customer_id_sequence",
            sequenceName = "customer_id_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "customer_id_sequence"
    )
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String password;
}
