package orf.fufeng.action.jpa.entity;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
