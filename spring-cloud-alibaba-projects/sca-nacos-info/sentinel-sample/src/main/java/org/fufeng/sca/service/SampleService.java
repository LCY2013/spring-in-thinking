package org.fufeng.sca.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import org.springframework.stereotype.Service;

/**
 * @author luocy
 * @description 演示用的业务逻辑类
 * @program customer-service
 * @create 2021-04-04
 * @since 1.0
 */
@Service
public class SampleService {

    /**
     * 资源点名称为createOrder
     * 模拟创建订单业务
     * 抛出IllegalStateException异常用于模拟业务逻辑执行失败的情况
     */
    @SentinelResource(value = "createOrder",blockHandler = "createOrderBlockHandler")
    public void createOrder(){
        try {
            //模拟处理业务逻辑需要101毫秒
            Thread.sleep(101);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("订单已创建");
    }

    public void createOrderBlockHandler(BlockException e) throws IllegalStateException{
        String msg = null;
        if(e instanceof FlowException){//限流异常
            msg = "资源已被限流";
        }else if(e instanceof DegradeException){//熔断异常
            msg = "资源已被熔断,请稍后再试";
        }else if(e instanceof ParamFlowException){ //热点参数限流
            msg = "热点参数限流";
        }else if(e instanceof SystemBlockException){ //系统规则异常
            msg = "系统规则(负载/....不满足要求)";
        }else if(e instanceof AuthorityException){ //授权规则异常
            msg = "授权规则不通过";
        }
        throw new IllegalStateException(msg);
    }
}
