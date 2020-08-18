/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-18
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: spring-in-thinking
 * @description: {@link }
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-18
 */
@WebServlet(asyncSupported = true, urlPatterns = "/async")
public class AsyncServlet extends HttpServlet {

    private static final long serialVersionUID = -8443732733783724198L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AsyncContext asyncContext = request.startAsync();

        response.setContentType("text/html;charset=UTF-8");

        final ServletContext servletContext = request.getServletContext();

        PrintWriter writer = response.getWriter();

        writer.write(threadInfo(request));

        asyncContext.addListener(new AsyncListener() {

            @Override
            public void onComplete(AsyncEvent event) throws IOException {

                HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();

                PrintWriter writer = response.getWriter();

                writer.write("onComplete : " + threadInfo((HttpServletRequest) event.getSuppliedRequest()));

            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {

                servletContext.log("onTimeout : " + threadInfo((HttpServletRequest) event.getSuppliedRequest()));

            }

            @Override
            public void onError(AsyncEvent event) throws IOException {

                servletContext.log("onError : " + threadInfo((HttpServletRequest) event.getSuppliedRequest()));

            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {

                servletContext.log("onStartAsync : " + threadInfo((HttpServletRequest) event.getSuppliedRequest()));

            }
        });

        asyncContext.start(() -> {
            HttpServletResponse response1 = (HttpServletResponse) asyncContext.getResponse();
            try {
                response1.getWriter().write(threadInfo(request));
                asyncContext.complete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private static String threadInfo(HttpServletRequest request) {
        return "Current Thread [" + Thread.currentThread().getName() + "] on request URI[" + request.getRequestURI() + "] was executed! <br />";
    }

}