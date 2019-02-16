package com.mytomcat.demo;

import java.io.IOException;

import com.mytomcat.core.MyHttpServlet;
import com.mytomcat.core.MyHttpServletRequest;
import com.mytomcat.core.MyHttpServletResponse;

public class InfoServlet extends MyHttpServlet {

    protected void doPost(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException {
        response.write("info");
    }

    protected void doGet(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException {
        response.write("info");
    }

}
