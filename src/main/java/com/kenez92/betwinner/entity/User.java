package com.kenez92.betwinner.entity;

import com.kenez92.betwinner.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    private UserRole role;

    @Column(name = "E_MAIL")
    private String eMail;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,
            targetEntity = Order.class,
            mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
}
