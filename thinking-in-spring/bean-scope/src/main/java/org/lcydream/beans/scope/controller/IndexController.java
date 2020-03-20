package org.lcydream.beans.scope.controller;

import org.lcydream.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页控制器{@link org.springframework.stereotype.Controller}
 */
@Controller
public class IndexController {

    //scopedTarget.createUser 这里生成的代理对象名称叫scopedTarget.createUser，
    // 其内部是每次HTTP请求都会产生一个新的User对象
    @Autowired
    private User user; // CGLIB 代理后对象（不变的）

    @GetMapping("/index.html")
    public String index(Model model) {
        // JSP EL 变量搜索路径 page -> request -> session -> application(ServletContext)
        // userObject -> 渲染上下文
        // user 对象存在 ServletContext，上下文名称：scopedTarget.createUser == 新生成 Bean 名称
        model.addAttribute("user", user);
        return "index";
    }
}
