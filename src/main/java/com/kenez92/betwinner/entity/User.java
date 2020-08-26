package com.kenez92.betwinner.entity;

import com.kenez92.betwinner.domain.UserRole;
import com.kenez92.betwinner.service.users.strategy.UserStrategy;
import com.kenez92.betwinner.service.users.strategy.factory.UserStrategyConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name = "User.quantity",
                query = "SELECT COUNT(u) FROM User u"
        ),
        @NamedQuery(
                name = "User.usersForSubscription",
                query = "FROM User WHERE subscription is true"
        )
})

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
    private String email;

    @Convert(converter = UserStrategyConverter.class)
    @Column(name = "STRATEGY")
    private UserStrategy userStrategy;

    @Column(name = "SUBSCRIPTION")
    private Boolean subscription;

    @Column(name = "MONEY")
    private BigDecimal money;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,
            targetEntity = Order.class,
            mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
}
