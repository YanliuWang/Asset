package com.tcss559.asset.controller;

import com.tcss559.asset.models.Asset;
import com.tcss559.asset.models.dto.ResponseDto;
import com.tcss559.asset.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@CrossOrigin
@RequestMapping("/tracking/location")
public class AssetTrackingController {

    @Autowired
    private AssetService assetService;

    @ResponseBody
    @RequestMapping(value = "/{rfidId}", produces = MediaType.APPLICATION_JSON_VALUE, method = GET)
    public String getLocation(@PathVariable String rfidId) {
        return assetService.getLocation(rfidId);
    }

    @ResponseBody
    @RequestMapping(value = "/", method = PUT)
    public ResponseDto updateLocation(@Valid @RequestBody Asset asset) {
        return assetService.updateLocation(asset);
    }
}
