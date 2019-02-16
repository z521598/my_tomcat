package com.mytomcat.demo;

import java.io.IOException;

import com.mytomcat.core.MyHttpServlet;
import com.mytomcat.core.MyHttpServletRequest;
import com.mytomcat.core.MyHttpServletResponse;

public class HelloWorldServlet extends MyHttpServlet {

    protected void doPost(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException {
        response.write("hello,world");
    }

    protected void doGet(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException {
        response.write("hello,world");
    }

}
