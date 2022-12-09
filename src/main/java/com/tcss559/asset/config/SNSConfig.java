package com.tcss559.asset.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.SnsClientBuilder;
//import software.amazon.awssdk.regions.Region;

@Configuration
public class SNSConfig {

    @Bean
    @Primary
    public AmazonSNSClient getAmazonSNSClient() {
        return(AmazonSNSClient) AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_2)
                .withCredentials(
                        new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIAUIY3GMURU2QTA2TW", "C7I2foQWchKVbN76wsXAEuRAGb6l+QfNnHe33/f7"))
                ).build();
    }
//
//    @Bean
//    @Primary
//    public SnsClient getSnsClient() {
//
//        AmazonSNSClient amazonSNSClient = getAmazonSNSClient();
//        amazonSNSClient.createTopic()
//
//        SnsClient.builder()
//                .region(Region.US_EAST_1)
//                .credentialsProvider(ProfileCredentialsProvider.create())
//                .build();
//
//        return SnsClient.builder().region(Region.US_East_2)
//                .credentialsProvider(
//                        new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIAUIY3GMURU2QTA2TW", "C7I2foQWchKVbN76wsXAEuRAGb6l+QfNnHe33/f7"))
//                ).build();
//    }
}
