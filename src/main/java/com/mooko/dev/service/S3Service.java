package com.mooko.dev.service;

import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RequiredArgsConstructor
public class S3Service {

    private S3Template s3Template;
    public void uploadFile() throws FileNotFoundException {
        File file = new File("file");
        FileInputStream fileInputStream = new FileInputStream(file);

        s3Template.upload("bucket", "key", fileInputStream);

    }
}
