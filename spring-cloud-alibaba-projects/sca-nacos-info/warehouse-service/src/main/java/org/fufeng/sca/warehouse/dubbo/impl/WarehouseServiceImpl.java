/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: customer-service
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2021-03-19
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.sca.warehouse.dubbo.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.fufeng.sca.warehouse.domain.Stock;
import org.fufeng.sca.warehouse.dubbo.WarehouseService;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program customer-service
 * @description 商品服务
 * @create 2021-03-19
 * @since 1.0
 */
@DubboService
public class WarehouseServiceImpl implements WarehouseService {

    @Override
    public Stock getStock(Long skuId) {
        Stock stock = null;
        if(skuId == 1101L){
            //模拟有库存商品
            stock = new Stock(1101L, "Apple iPhone 11 128GB 紫色", 32, "台");
            stock.setDescription("Apple 11 紫色版对应商品描述");
        }else if(skuId == 1102L){
            //模拟无库存商品
            stock = new Stock(1102L, "Apple iPhone 11 256GB 白色", 0, "台");
            stock.setDescription("Apple 11 白色版对应商品描述");
        }else{
            //演示案例，暂不考虑无对应skuId的情况
        }
        return stock;
    }

}
