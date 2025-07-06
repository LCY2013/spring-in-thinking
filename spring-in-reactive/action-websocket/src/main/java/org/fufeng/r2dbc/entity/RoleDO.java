package org.fufeng.r2dbc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

//@Entity
@Table("APP_ROLE")
@Data
@NoArgsConstructor
public class RoleDO {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public RoleDO(String name) {
        this.name = name;
    }
}
