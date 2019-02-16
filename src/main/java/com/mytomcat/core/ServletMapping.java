package com.mytomcat.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServletMapping {
    private String name;
    private String url;
    @JsonProperty("class")
    private String clzName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClzName() {
        return clzName;
    }

    public void setClzName(String clzName) {
        this.clzName = clzName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ServletMapping{");
        sb.append("name='").append(name).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", clzName='").append(clzName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
