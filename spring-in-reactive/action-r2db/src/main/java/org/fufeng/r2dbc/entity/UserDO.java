package org.fufeng.r2dbc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Collection;

//@Entity
@Table("APP_USER")
@Data
@NoArgsConstructor
public class UserDO {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    private String password;

    //        @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "APP_USERS_ROLES", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<String> roles;

    public UserDO(String email, String firstName, String lastName, String password, Collection<String> roles) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roles = roles;
    }
}
