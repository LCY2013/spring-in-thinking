/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-cloud-alibaba-projects
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2021-03-17
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.sca.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.fufeng.sca.order.dto.Stock;
import org.fufeng.sca.order.feign.WarehouseFeignClient;
import org.fufeng.sca.warehouse.dubbo.WarehouseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program spring-cloud-alibaba-projects
 * @description 订单服务
 * @create 2021-03-17
 * @since 1.0
 */
@Slf4j
@RestController
public class OrderController {

    //利用@Resource将IOC容器中自动实例化的实现类对象进行注入
    @Resource
    private WarehouseFeignClient warehouseFeignClient;

    // 引用dubbo服务
    @DubboReference
    private WarehouseService warehouseService;

    /**
     * 创建订单业务逻辑
     * @param skuId 商品类别编号
     * @param salesQuantity 销售数量
     * @return 创建订单信息
     */
    @GetMapping("/create/order/rest/{skuId}/{salesQuantity}")
    public Map<String,Object> createOrder(@PathVariable Long skuId ,
                                          @PathVariable Long salesQuantity){
        Map<String,Object> result = new LinkedHashMap<>();
        //查询商品库存，像调用本地方法一样完成业务逻辑。
        Stock stock = warehouseFeignClient.getStock(skuId);
        log.info(stock.toString());
        if(salesQuantity <= stock.getQuantity()){
            //创建订单相关代码，此处省略
            //CODE=SUCCESS代表订单创建成功
            result.put("code" , "SUCCESS");
            result.put("skuId", skuId);
            result.put("message", "订单创建成功");
        }else{
            //code=NOT_ENOUGN_STOCK代表库存不足
            result.put("code", "NOT_ENOUGH_STOCK");
            result.put("skuId", skuId);
            result.put("message", "商品库存数量不足");
        }
        return result;
    }

    @GetMapping("/create/order/rpc/{skuId}/{salesQuantity}")
    public Map<String,Object> createRpcOrder(@PathVariable Long skuId ,
                                          @PathVariable Long salesQuantity){
        Map<String,Object> result = new LinkedHashMap<>();
        //查询商品库存，像调用本地方法一样完成业务逻辑。
        Stock stock = warehouseFeignClient.getStock(skuId);
        log.info(stock.toString());
        if(salesQuantity <= stock.getQuantity()){
            //创建订单相关代码，此处省略
            //CODE=SUCCESS代表订单创建成功
            result.put("code" , "SUCCESS");
            result.put("skuId", skuId);
            result.put("message", "订单创建成功");
        }else{
            //code=NOT_ENOUGN_STOCK代表库存不足
            result.put("code", "NOT_ENOUGH_STOCK");
            result.put("skuId", skuId);
            result.put("message", "商品库存数量不足");
        }
        return result;
    }
}
