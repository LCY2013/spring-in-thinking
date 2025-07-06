package org.fufeng.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

//@Entity
@Table("STOCK")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockDO {
    @Id
    private String symbol;

    private String name;
}
