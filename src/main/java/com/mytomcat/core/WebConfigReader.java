package com.mytomcat.core;

import java.io.IOException;
import java.io.InputStream;

import com.mytomcat.util.YamlUtils;

public class WebConfigReader {
    private String path;

    public WebConfigReader(String path) {
        this.path = path;
    }

    public WebConfig parse() throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        WebConfig webConfig = YamlUtils.toObject(inputStream, WebConfig.class);
        System.out.println(webConfig.getServletMappings());
        return webConfig;
    }

}
