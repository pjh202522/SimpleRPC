package com.pjh.loadbalance;

import com.pjh.common.URL;

import java.util.List;
import java.util.Random;

/**
 * @author yueyinghaibao
 */
public class LoadBalancer {

    public static URL random(List<URL> urls) {
        Random random = new Random();
        int i = random.nextInt(urls.size());
        return urls.get(i);
    }
}
