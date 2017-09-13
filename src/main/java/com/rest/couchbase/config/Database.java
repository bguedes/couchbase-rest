package com.orange.couchbase.config;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {

    @Value("${storage.host}")
    private String host;

    @Value("${storage.bucket}")
    private String bucket;

    @Value("${storage.username}")
    private String username;

    @Value("${storage.password}")
    private String password;

    public @Bean Cluster couchbaseCluster() {
        CouchbaseCluster cluster = CouchbaseCluster.create(host);
        //cluster.authenticate(username, password);
        return cluster;
    }

    public @Bean Bucket loginBucket() {
        return couchbaseCluster().openBucket(bucket);
    }
}
