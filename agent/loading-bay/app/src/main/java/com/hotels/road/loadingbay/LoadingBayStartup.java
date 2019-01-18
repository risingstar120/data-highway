package com.hotels.road.loadingbay;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;

import com.hotels.road.s3.io.S3ConnectivityCheck;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoadingBayStartup {

  @Autowired
  private AmazonS3 s3;
  @Value("${s3.bucket}")
  private String bucket;

  /**
   * Check connections before to start the application.
   * In case of a problem, it stops the application.
   */
  @PostConstruct
  public void postConstruct() {
    try {
      new S3ConnectivityCheck().checkS3Put(s3, bucket, "key");
    } catch (Exception e) {
      log.error("Application is going to be stopped.");
      throw e;
    }
  }
}
