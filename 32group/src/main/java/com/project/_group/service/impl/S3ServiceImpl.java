package com.project._group.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.project._group.config.YandexCloudConfig;
import com.project._group.service.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@AllArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 s3;

    private final YandexCloudConfig cloudConfig;

    @Override
    public String uploadFileToS3(String url) {
        File file = new File(url);
        s3.putObject(cloudConfig.getBucketName(), file.getName(), file);
        return s3.getUrl(cloudConfig.getBucketName(), url).toString();
    }
}
