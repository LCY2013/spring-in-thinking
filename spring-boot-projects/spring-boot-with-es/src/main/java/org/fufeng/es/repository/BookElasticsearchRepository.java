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
package org.fufeng.es.repository;

import org.fufeng.es.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.repository.support.AbstractElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.MappingElasticsearchEntityInformation;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.stereotype.Repository;

/**
 * @program: spring-in-thinking
 * @description: 图书es仓储 {@link BookElasticsearchRepository}
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-10
 */
@Repository("bookElasticsearchRepository")
public class BookElasticsearchRepository extends AbstractElasticsearchRepository<Book,String> {

    // 定义图书仓储
    private BookRepository bookRepository;

    @Autowired
    public BookElasticsearchRepository(ElasticsearchOperations operations) {
        super(createElasticsearchEntityInformation(),operations);
    }

    private static ElasticsearchEntityInformation<Book, String> createElasticsearchEntityInformation() {
        final TypeInformation<Book> typeInformation = ClassTypeInformation.from(Book.class);
        final ElasticsearchPersistentEntity<Book> elasticsearchPersistentEntity = new SimpleElasticsearchPersistentEntity<Book>(typeInformation);
        final ElasticsearchEntityInformation<Book, String> information = new MappingElasticsearchEntityInformation<Book, String>(elasticsearchPersistentEntity);
        return information;
    }


    /**
     *  通过该方法实现自定义的ID
     * @param str ID信息
     * @return 转换后的ID
     */
    @Override
    protected String stringIdRepresentation(String str) {
        return str;
    }

    /**
     *  添加图书信息
     * @param book 图书
     * @return 返回添加后的图书信息
     */
    public Book add(Book book){
        return this.bookRepository.save(book);
    }
}
