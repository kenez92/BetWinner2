package com.kenez92.betwinner.users;

import com.kenez92.betwinner.common.enums.UserStrategy;
import com.kenez92.betwinner.coupons.Coupon;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "MONEY", nullable = false, columnDefinition = "DECIMAL(19,2)")
    private BigDecimal money;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,
            targetEntity = Coupon.class,
            cascade = CascadeType.ALL,
            mappedBy = "user")
    private List<Coupon> coupons = new ArrayList<>();
}
