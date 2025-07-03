package org.fufeng.async.service;

import org.fufeng.jpa.dao.StockDao;
import org.fufeng.mvc.model.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RStockServiceImpl implements RStockService {
    @Autowired
    private StockDao stockDao;

    @Async
    @Override
    public Future<List<Stock>> getAllStocksAsync() {
        return new AsyncResult<>(doGetAllStocks());
    }

    @Override
    public CompletableFuture<List<Stock>> getAllStocks() {
        return CompletableFuture.supplyAsync(() -> doGetAllStocks());
    }

    public List<Stock> doGetAllStocks() {
        log.info("do getAllStocks");
        return stockDao.findAll().stream().map(stockDO -> Stock.builder()
                .symbol(stockDO.getSymbol())
                .name(stockDO.getName())
                .build()
        ).collect(Collectors.toList());
    }

}
