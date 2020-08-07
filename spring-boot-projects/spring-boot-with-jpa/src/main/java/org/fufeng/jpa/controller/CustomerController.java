/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-07
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.jpa.controller;

import org.fufeng.jpa.entity.Customer;
import org.fufeng.jpa.repository.CustomerRepository;
import org.fufeng.jpa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: spring-in-thinking
 * @description: Customer 接口控制器
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-07
 * @see org.fufeng.jpa.entity.Customer
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/all")
    public List<Customer> all(){
        return customerRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public Customer getCustomer(@PathVariable("id") Long id){
        return customerService.getCustomerById(id);
    }

    /**
     *  {
     *     "name":"fufeng",
     *     "creditCard":{
     *         "number":"6352678683823",
     *         "registeredDate":"2020-08-07 16:00:00"
     *     },
     *     "store":{
     *         "name":"新华"
     *     },
     *     "books":[
     *         {
     *             "name":"k8s",
     *             "isbn":"否",
     *             "publishDate":"2020-03-07 16:00:00"
     *         },{
     *             "name":"docker",
     *             "isbn":"否",
     *             "publishDate":"2020-02-07 16:00:00"
     *         }
     *     ]
     * }
     *
     * @param customer 消费者示例
     * @return 消费者信息
     */
    @PostMapping("/add")
    public Customer addCustomer(@RequestBody Customer customer){
        customerService.addCustomer(customer);
        return customerService.getCustomerById(customer.getId());
    }

}
