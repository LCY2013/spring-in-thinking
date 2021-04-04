package org.fufeng.sca.domain;

import lombok.Data;

/**
 * @author luocy
 * @description 封装响应数据的对象
 * @program customer-service
 * @create 2021-04-04
 * @since 1.0
 */
@Data
public class ResponseObject {
    private String code; //结果编码，0-固定代表处理成功
    private String message;//响应消息
    private Object data;//响应附加数据（可选）

    public ResponseObject(String code, String message) {
        this.code = code;
        this.message = message;
    }
}