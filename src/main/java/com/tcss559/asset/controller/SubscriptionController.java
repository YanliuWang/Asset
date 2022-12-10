package com.tcss559.asset.controller;

import com.tcss559.asset.models.Response;
import com.tcss559.asset.service.AssetService;
import com.tcss559.asset.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @Description: Subscription Controller
 * @Author: Xiaojie Li
 * @Date: 2022/12/9
 */
@Controller
@CrossOrigin
@RequestMapping("/notification")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * subscribe
     *
     * @param headers
     * @param assetId
     * @return Response
     */
    @ResponseBody
    @RequestMapping(value = "/{assetId}", method = POST)
    public Response subscribe(@RequestHeader Map<String, String> headers, @PathVariable int assetId) {
        return subscriptionService.subscribe(headers, assetId);
    }

}
