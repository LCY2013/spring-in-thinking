package org.fufeng.action.lambda.p4;

import org.fufeng.action.mvc.model.Stock;

import java.util.Comparator;

public class StockComparator implements Comparator<Stock> {
    @Override
    public int compare(Stock o1, Stock o2) {
        return o1.getSymbol().compareTo(o2.getSymbol());
    }
}
