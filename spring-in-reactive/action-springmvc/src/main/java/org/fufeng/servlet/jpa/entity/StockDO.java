package org.fufeng.servlet.jpa.entity;

import jakarta.persistence.*;
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
