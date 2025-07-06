package org.fufeng.jpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

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
