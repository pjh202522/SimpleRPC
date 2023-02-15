package com.pjh.register;

import com.pjh.common.URL;
import com.pjh.config.ConfigurationCenter;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.client.ZKClientConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yueyinghaibao
 */
public class RemoteRegister {

    private static CuratorFramework client = getZkClient();

    private static CuratorFramework getZkClient() {
        String connectString = (String) ConfigurationCenter.get("ZooKeeper.Host");
        // 解决连接慢的关键 手动设置主机名 避免使用getHostname()导致阻塞
        ZKClientConfig config = new ZKClientConfig();
        config.setProperty(ZKClientConfig.ZOOKEEPER_SERVER_PRINCIPAL,"zookeeper/"+connectString);

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,5);
        client = CuratorFrameworkFactory.builder().zkClientConfig(config).connectString(connectString).namespace("simpleRPC").retryPolicy(retryPolicy).build();
        client.start();
        return client;
    }

    private static void createZNode(String path) {
        try {
            client.create().creatingParentContainersIfNeeded().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<URL> getZNode(String path) {
        List<URL> urls;
        try {
            List<String> list = client.getChildren().forPath(path);
            urls = new ArrayList<>();
            for(String s : list) {
                urls.add(new URL(s));
            }
            return urls;
        } catch (Exception e) {
            return null;
        }
    }

    private static void setZNode(String path, URL data) {
        try {
            client.create().withMode(CreateMode.EPHEMERAL).forPath(path + "/" + data.getHostname() + ":" + data.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, List<URL>> cache = new HashMap<>();

    public static void register(String interfaceName, String version, URL url) {
        String path = "/" + interfaceName + "/" + version;
        List<URL> urls = getZNode(path);
        if(urls == null) {
            createZNode(path);
        }
        setZNode(path, url);
    }

    public static List<URL> get(String interfaceName, String version) {
        String path = "/" + interfaceName + "/" + version;
        List<URL> urls = cache.get(path);
        if(urls == null) {
            urls = getZNode(path);
            cache.put(path, urls);
            setWatcher(path);
        }
        return urls;
    }

    private static void setWatcher(String path) {
        CuratorCache curatorCache = CuratorCache.build(client, path);
        CuratorCacheListener listener = CuratorCacheListener.builder()
                .forCreates(childData -> cache.get(path).add(new URL(childData.getPath())))
                .forDeletes(childData -> cache.get(path).remove(new URL(childData.getPath())))
                .build();
        curatorCache.listenable().addListener(listener);
        curatorCache.start();
    }
}
