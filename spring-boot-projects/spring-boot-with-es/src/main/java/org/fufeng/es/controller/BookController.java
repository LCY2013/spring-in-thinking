/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-10
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.es.controller;

import org.fufeng.es.entity.Book;
import org.fufeng.es.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @program: spring-in-thinking
 * @description: 图书表现层 {@link BookController}
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-10
 */
@RestController
public class BookController {

    @Autowired
    @Qualifier("bookRepository")
    private BookRepository bookRepository;

    @Autowired
    @Qualifier("bookElasticsearchRepository")
    private PagingAndSortingRepository<Book,String> bookStringBookElasticsearchRepository;

    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable("id")String id){
        return bookRepository.findById(id).orElse(null);
    }

    @GetMapping("/books/{name}")
    public List<Book> getBooks(@PathVariable("name")String name){
        return bookRepository.findByName(name);
    }

    @PostMapping("/book/{id}")
    public Book publishBook(@PathVariable String id, @RequestBody Book book){
        book.setId(id);
        book.setPublishedDate(new Date());
        return bookStringBookElasticsearchRepository.save(book);
    }

}
