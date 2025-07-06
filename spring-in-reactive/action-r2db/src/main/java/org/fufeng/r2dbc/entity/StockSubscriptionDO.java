package org.fufeng.r2dbc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

//@Entity
@Table("STOCK_SUBSCRIPTION")
@Data
@NoArgsConstructor
public class StockSubscriptionDO {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String symbol;
}
