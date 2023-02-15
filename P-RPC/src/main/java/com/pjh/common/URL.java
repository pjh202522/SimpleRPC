package com.pjh.common;

import java.io.Serializable;

/**
 * @author yueyinghaibao
 */
public class URL implements Serializable {

    private String hostname;
    private Integer port;

    public URL(String hostname, Integer port) {
        this.hostname = hostname;
        this.port = port;
    }

    public URL(String path) {
        String[] split = path.split(":");
        this.hostname = split[0];
        this.port = Integer.valueOf(split[1]);
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}

