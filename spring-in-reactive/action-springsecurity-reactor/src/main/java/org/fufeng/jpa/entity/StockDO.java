package org.fufeng.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STOCK")
@Data
@NoArgsConstructor
public class StockDO {
    @Id
    private String symbol;

    private String name;
}
