package com.mytomcat.core;

import java.io.IOException;
import java.io.OutputStream;

public class MyHttpServletResponse {
    private OutputStream outputStream;

    public MyHttpServletResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void write(String content) throws IOException {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("HTTP/1.1 ").append(getCode()).append(" ").append(getReasonPhrase()).append("\n");
        messageBuilder.append("Content-Type: ").append(getContentType()).append("\n");
        messageBuilder.append("Content-Length: ").append(content.length() + 26).append("\n");
        messageBuilder.append("\n");
        messageBuilder.append("<html><body>");
        messageBuilder.append(content);
        messageBuilder.append("</body></html>");
        String message = messageBuilder.toString();
        this.outputStream.write(message.getBytes());
        this.close();
    }

    private String getContentType() {
        return "text/html;charset=UTF-8";
    }

    private String getReasonPhrase() {
        return "OK";
    }

    private int getCode() {
        return 200;
    }

    public void flush() throws IOException {
        this.outputStream.flush();
    }

    public void close() throws IOException {
        this.outputStream.close();
    }
}
