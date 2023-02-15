package com.pjh.service;

import com.pjh.TimeService;

import java.util.Date;

/**
 * @author yueyinghaibao
 */
public class TimeServiceImpl implements TimeService {
    @Override
    public String getTime() {
        return new Date().toString();
    }
}
