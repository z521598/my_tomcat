package com.mytomcat.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyTomcat {
    private int port;
    private String configYamlClassPath;
    private Map<String, String> urlServletMap = new HashMap<>();
    private WebConfigReader webConfigReader;

    public MyTomcat(int port) {
        this.port = port;
        this.configYamlClassPath = "web.yml";
        this.webConfigReader = new WebConfigReader(this.configYamlClassPath);
    }

    public MyTomcat(int port, String configYamlClassPath) {
        this.port = port;
        this.configYamlClassPath = configYamlClassPath;
        this.webConfigReader = new WebConfigReader(this.configYamlClassPath);
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
        try {
            WebConfig webConfig = this.webConfigReader.parse();
            List<ServletMapping> servletMappingList = webConfig.getServletMappings();
            servletMappingList.forEach(sm -> urlServletMap.put(sm.getUrl(), sm.getClzName()));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void dispatch(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException {
        String servletClzName = urlServletMap.get(request.getUri());
        if (servletClzName == null) {
            notify404(request, response);
            return;
        }
        Class servletClz = null;
        try {
            servletClz = Class.forName(servletClzName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
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
