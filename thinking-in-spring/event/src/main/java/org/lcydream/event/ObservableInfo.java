/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-07-13
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.lcydream.event;

import java.util.EventListener;
import java.util.EventObject;
import java.util.Observable;
import java.util.Observer;

/**
 * @program: spring-in-thinking
 * @description: #{description}
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-07-13
 */
public class ObservableInfo {

    public static void main(String[] args) {
        //创建一个监视器
        EventObservable eo = new EventObservable();
        //监视器添加监视对象
        eo.addObserver(new EventObserver());

        //发布信息
        eo.notifyObservers("hello");
    }

    //监视器发布
    static class EventObservable extends Observable {

        @Override
        protected synchronized void setChanged() {
            super.setChanged();
        }

        @Override
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(new EventObject(arg));
            clearChanged();
        }
    }

    //监视者
    static class EventObserver implements Observer, EventListener {
        @Override
        public void update(Observable o, Object arg) {
            EventObject eo = (EventObject)arg;
            System.out.println(o+":"+eo);
        }
    }

}
