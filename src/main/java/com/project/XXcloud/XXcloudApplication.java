package com.project.XXcloud;

import com.project.XXcloud.HDFS.HDFSOperation;
import com.project.XXcloud.SparkSense.SparkSense;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XXcloudApplication {

    public static void main(String[] args) {
        SparkSense.initialSpark();
        SpringApplication.run(XXcloudApplication.class, args);
    }

}
