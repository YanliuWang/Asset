package com.tcss559.asset.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.AmazonSNSException;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.tcss559.asset.dao.SubscriptionDAO;
import com.tcss559.asset.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

import javax.annotation.Resource;

import java.sql.SQLException;
import java.util.Map;

/**
 * @Description: subscription service
 * @Author: Xiaojie Li
 * @Date: 2022/12/9
 */
@Service("SubscriptionService")
public class SubscriptionService {

    @Resource
    private SubscriptionDAO subscriptionDAO;

    @Autowired
    private AmazonSNSClient awsclient;

    /**
     * subscribe
     *
     * @param headers
     * @param assetId
     * @return Response
     */
    public Response subscribe(Map<String, String> headers, int assetId) {
        String token = headers.getOrDefault("token", null);
        if (token == null) return Response.error("Fail to get token");
        String result[] = token.split("\\s*-\\s*");
        String userId = result[result.length - 1];

        if (userId == null) return Response.error("Fail to get userId");
        try{
            int isAdded = subscriptionDAO.addNewSubscription(Integer.parseInt(userId), assetId);
            if (isAdded != 1) {
                return Response.error("subscription added failure");
            }

            String email = subscriptionDAO.getEmail(Integer.parseInt(userId));
            if (email == null) {
                return Response.error("user email is empty. Cannot subscribe.");
            }
            String ARN = subscriptionDAO.getARN(assetId);
            awsclient.subscribe(new SubscribeRequest(ARN, "email", email));
            return Response.success();
        } catch(Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }

    /**
     * publish
     *
     * @param userId,
     * @param assetId
     * @param message
     * @return Response
     */
    public Response publish(int userId, long assetId, String message) {
        try{
            String email = subscriptionDAO.getEmail(userId);
            if (email == null) {
                return Response.error("user email is empty. Cannot notify the user.");
            }
            String ARN = subscriptionDAO.getARN(assetId);
            awsclient.publish(new PublishRequest(ARN, message, message));
            return Response.success();
        } catch(Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }

    /**
     * create Topic
     *
     * @param assetId
     * @return void
     */
    public void createTopic(long assetId) {
        String arn = awsclient.createTopic(String.format("asset-%s", assetId)).getTopicArn();
        subscriptionDAO.storeArn(assetId, arn);
    }
}