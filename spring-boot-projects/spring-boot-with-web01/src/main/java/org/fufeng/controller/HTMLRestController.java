/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-06
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * @program: spring-in-thinking
 * @description: html rest 使用
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-06
 */
//@Controller
@RestController
public class HTMLRestController {

    // 返回html字符串
    /*@RequestMapping(path = {"/html/list","/html/lists"},
            method = {RequestMethod.GET,RequestMethod.POST})*/
    @GetMapping("/html/list")
    @PostMapping("/html/lists")
    //@ResponseBody
    public String html(){
        return "<html><head></head><body>hello,fufeng!</body></html>";
    }

    @GetMapping("/html/list/{message}")
    public String htmlPathVariable(@PathVariable String message){
        return "<html><head></head><body>hello,"+message+"</body></html>";
    }


    @GetMapping("/html/param")
    public String htmlParam(@RequestParam(value = "pa",required = false,defaultValue = "Empty") String param,
                            HttpServletRequest request,
                            @RequestParam(value = "ag",required = false,defaultValue = "17") Integer age){
        String pa = request.getParameter("param");
        return "<html><head></head><body>"+
                "param -> " + pa + "</br>" +
                "pa -> " + param + "</br>" +
                "age -> " + age + "</br>" +
                "</body></html>";
    }

    @GetMapping("/html/head")
    public String htmlHead(@RequestHeader("Accept") String acceptHeader,
                           HttpServletRequest request){
        return "<html><head></head><body>"+
                "Request Header 'Accept' : " + acceptHeader +
                "</body></html>";
    }

    @GetMapping("/html/response/entity")
    public ResponseEntity<String> htmlResponseEntity(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("headers", Collections.singletonList("headersValue"));
        return new ResponseEntity<>(
                "<html><head></head><body>HTML ResponseEntity</body></html>",httpHeaders, HttpStatus.OK);
    }
}
