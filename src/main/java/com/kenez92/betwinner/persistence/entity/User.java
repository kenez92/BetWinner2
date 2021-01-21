package com.kenez92.betwinner.persistence.entity;

import com.kenez92.betwinner.common.enums.UserStrategy;
import com.kenez92.betwinner.domain.UserRole;
import com.sun.istack.NotNull;
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

    @NotNull
    @Column(unique = true, name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private UserRole role;

    @NotNull
    @Column(name = "E_MAIL", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "STRATEGY")
    private UserStrategy userStrategy;

    @Column(name = "SUBSCRIPTION")
    private Boolean subscription;

    @Column(name = "MONEY", nullable = false)
    private BigDecimal money;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,
            targetEntity = Coupon.class,
            cascade = CascadeType.ALL,
            mappedBy = "user")
    private List<Coupon> coupons = new ArrayList<>();
}
