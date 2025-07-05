package org.fufeng.r2dbc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

//@Entity
@Table("STOCK")
@Data
@NoArgsConstructor
public class StockDO {
    @Id
    private String symbol;

    private String name;
}
