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
package org.fufeng.data.springjpaoprator.domain.relationship.onetoone.twoway;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description TODO
 * @create 2020-10-12
 */
import lombok.*;

import javax.persistence.*;

@Entity(name = "one_to_one_user_info_two_way")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Integer ages;
    private String telephone;
    //@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE}) //维护user的外键关联关系，配置一对一
    @MapsId // 作用是把关联关系实体里面的 ID（默认）值 copy 到 @MapsId 标注的字段上面（这里指的是 user_id 字段）
    @OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true,fetch = FetchType.LAZY) //维护user的外键关联关系，配置一对一
    private User user;

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", ages=" + ages +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
