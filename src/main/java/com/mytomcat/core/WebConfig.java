package com.mytomcat.core;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebConfig {
    @JsonProperty("servlet")
    private List<ServletMapping> servletMappings;

    public List<ServletMapping> getServletMappings() {
        return servletMappings;
    }

    public void setServletMappings(List<ServletMapping> servletMappings) {
        this.servletMappings = servletMappings;
    }
}
