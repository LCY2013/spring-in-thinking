package org.fufeng.servlet.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "STOCK_SUBSCRIPTION")
@Data
@NoArgsConstructor
public class StockSubscriptionDO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String symbol;
}
