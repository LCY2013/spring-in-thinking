package org.fufeng.action.lambda.p4;

import com.viso.mvc.model.Stock;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

public class DemoConstructorReferenceTest {
    @Test
    public void stockConstructorTest() {
        StockCreator creator1 = Stock::new;
        StockCreator2 creator2 = Stock::new;
        Supplier<Stock> creator3 = Stock::new;
        Stock stock1 = creator1.getStock();
        Stock stock2 = creator2.getStock("APPL", "APPLE Inc");
        Stock stock3 = creator3.get();
    }

    //ClassName::new
}
