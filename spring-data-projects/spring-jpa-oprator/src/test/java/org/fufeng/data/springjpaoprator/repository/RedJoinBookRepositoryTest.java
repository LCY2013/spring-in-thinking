/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring-boot
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-10-12
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.data.springjpaoprator.repository;

import org.fufeng.data.springjpaoprator.domain.RedJoinBook;
import org.fufeng.data.springjpaoprator.domain.RedSubBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring-boot
 * @description TODO
 * @create 2020-10-12
 */
@DataJpaTest
public class RedJoinBookRepositoryTest {
    @Autowired
    private RedJoinBookRepository redJoinBookRepository;
    @Test
    public void testRedSubBook() {
        RedJoinBook redJoinBook = new RedJoinBook();
        redJoinBook.setTitle("redbook");
        redJoinBook.setRedMark("redmark");
        redJoinBook.setId(1L);
        redJoinBookRepository.saveAndFlush(redJoinBook);
        RedJoinBook r = redJoinBookRepository.findById(1L).get();
        System.out.println(r.getId()+":"+r.getTitle()+":"+r.getRedMark());
    }
}
