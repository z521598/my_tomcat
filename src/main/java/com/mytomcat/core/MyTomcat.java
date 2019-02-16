package com.mytomcat.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.mytomcat.demo.HelloWorldServlet;
import com.mytomcat.demo.InfoServlet;

public class MyTomcat {
    private int port;
    private String configYamlClassPath;
    private Map<String, Class> urlServletMap;

    public MyTomcat(int port) {
        this.port = port;
        this.configYamlClassPath = "web.yml";
    }

    public MyTomcat(int port, String configYamlClassPath) {
        this.port = port;
        this.configYamlClassPath = configYamlClassPath;
    }

    public void start() throws IOException {
        initServletMapping();
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            MyHttpServletRequest request = new MyHttpServletRequest(inputStream);
            MyHttpServletResponse response = new MyHttpServletResponse(outputStream);

            dispatch(request, response);
            socket.close();
        }
    }

    private void initServletMapping() {
        urlServletMap = new HashMap<String, Class>();
        urlServletMap.put("/", HelloWorldServlet.class);
        urlServletMap.put("/info", InfoServlet.class);
    }

    private void dispatch(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException {
        Class servletClz = urlServletMap.get(request.getUri());
        if (servletClz == null) {
            notify404(request, response);
            return;
        }
        MyHttpServlet httpServlet = null;
        try {
            httpServlet = (MyHttpServlet) servletClz.newInstance();
        } catch (Exception e) {
            notify500(request, response);
            return;
        }

        httpServlet.service(request, response);
    }

    private void notify500(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException {
        response.write("500");
    }

    private void notify404(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException {
        response.write("404");
    }

}
