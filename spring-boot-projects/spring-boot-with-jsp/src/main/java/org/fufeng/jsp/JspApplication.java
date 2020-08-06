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
package org.fufeng.jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: spring-in-thinking
 * @description: 使用JSP
 *   jar tvf target/*.jar
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-06
 */
@SpringBootApplication
public class JspApplication {

    public static void main(String[] args) {
        SpringApplication.run(JspApplication.class,args);
    }
    /*
    Data Access
    The data stored at each znode in a namespace is read and written atomically.
    Reads get all the data bytes associated with a znode and a write replaces all the data.
    Each node has an Access Control List (ACL) that restricts who can do what.

    ZooKeeper was not designed to be a general database or large object store.
    Instead, it manages coordination data. This data can come in the form of configuration,
    status information, rendezvous, etc. A common property of the various forms of
    coordination data is that they are relatively small: measured in kilobytes.
    The ZooKeeper client and the server implementations have sanity checks to ensure
    that znodes have less than 1M of data, but the data should be much less than that on average.
    Operating on relatively large data sizes will cause some operations to take much more time
    than others and will affect the latencies of some operations because of the extra time
    needed to move more data over the network and onto storage media.
    If large data storage is needed, the usually pattern of dealing with
    such data is to store it on a bulk storage system, such as NFS or HDFS,
    and store pointers to the storage locations in ZooKeeper.
     */

}
