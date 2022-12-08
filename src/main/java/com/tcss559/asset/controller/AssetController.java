package com.tcss559.asset.controller;

import com.tcss559.asset.models.Asset;
import com.tcss559.asset.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@CrossOrigin
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @ResponseBody
    @RequestMapping(value = "/{RFIDid}", method = GET)
    public Asset lookUpAsset(HttpServletRequest request, HttpServletResponse response, @PathVariable String RFIDid) {
        return assetService.lookUpAsset(request, response, RFIDid);
    }

    @ResponseBody
    @RequestMapping(value = "/category/{value}", method = GET)
    public List<Asset> lookUpAssetByCategory(HttpServletRequest request, HttpServletResponse response, @PathVariable String value) {
        return assetService.lookUpAssetByCategory(request, response, value);
    }

}
