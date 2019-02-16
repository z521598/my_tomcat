package com.mytomcat.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyHttpServletRequest {
    private InputStream inputStream;
    private HttpRequestMethod method;
    private String uri;

    public MyHttpServletRequest(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String requestLine = bufferedReader.readLine();
        String[] methodAndUriAndProtocol = requestLine.split(" ");
        method = HttpRequestMethod.valueOf(methodAndUriAndProtocol[0]);
        uri = methodAndUriAndProtocol[1];
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public HttpRequestMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }
}
