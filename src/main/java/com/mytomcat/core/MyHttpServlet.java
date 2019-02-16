package com.mytomcat.core;

import java.io.IOException;

public abstract class MyHttpServlet {

    public void service(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException {
        HttpRequestMethod method = request.getMethod();
        switch (method) {
            case GET:
                doGet(request, response);
                break;
            case POST:
                doPost(request, response);
                break;
            default:
                doNotSupport(request, response);
                break;
        }
    }

    private void doNotSupport(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException {
        response.write("NOT SUPPORT");
        response.flush();
        response.close();

    }

    protected abstract void doPost(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException;

    protected abstract void doGet(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException;
}
