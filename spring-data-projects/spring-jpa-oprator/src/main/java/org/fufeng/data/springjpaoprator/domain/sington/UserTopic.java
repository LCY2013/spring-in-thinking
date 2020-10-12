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
package org.fufeng.data.springjpaoprator.domain.sington;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring-boot
 * @description 用户主题实体领域
 * @create 2020-10-12
 */
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "user_topic")
@Access(AccessType.FIELD)
@Data
public class UserTopic {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title", nullable = true, length = 200)
    private String title;
    @Basic
    @Column(name = "create_user_id", nullable = true)
    private Integer createUserId;
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content", nullable = true, length = -1)
    @Lob
    private String content;
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image", nullable = true)
    @Lob
    private byte[] image;
    @Basic
    @Column(name = "create_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Basic
    @Column(name = "create_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "topic_type")
    private Type type;
    @Transient
    private String transientSimple;
    //非数据库映射字段，业务类型的字段
    public String getTransientSimple() {
        return title + "auto:fufeng" + type;
    }
    //有一个枚举类，主题的类型
    public enum Type {
        EN("英文"), CN("中文");
        private final String des;
        Type(String des) {
            this.des = des;
        }
    }
}
