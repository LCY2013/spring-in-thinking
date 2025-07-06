package org.fufeng.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "APP_ROLE")
@Data
@NoArgsConstructor
public class RoleDO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public RoleDO(String name) {
        this.name = name;
    }
}
