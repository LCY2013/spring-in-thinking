/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring
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
package org.fufeng.data.springjpaoprator.domain.relationship.many;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description TODO
 * @create 2020-10-12
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoomRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createTime,udpateTime;
    @ManyToOne
    private Room room;
    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "UserRoomRelation{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", udpateTime=" + udpateTime +
                ", room=" + room +
                ", user=" + user +
                '}';
    }
}
