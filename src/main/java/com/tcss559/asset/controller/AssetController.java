package com.tcss559.asset.controller;

import com.tcss559.asset.models.Asset;
import com.tcss559.asset.models.dto.ResponseDto;
import com.tcss559.asset.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@CrossOrigin
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @ResponseBody
    @RequestMapping(value = "/{RFIDid}", method = GET)
    public Asset lookUpAsset(@PathVariable String RFIDid) {
        return assetService.lookUpAsset(RFIDid);
    }

    @ResponseBody
    @RequestMapping(value = "/category/{value}", method = GET)
    public List<Asset> lookUpAssetByCategory(@PathVariable String value) {
        return assetService.lookUpAssetByCategory(value);
    }

    @ResponseBody
    @RequestMapping(value = "/name/{value}", method = GET)
    public List<Asset> lookUpAssetByName(@PathVariable String value) {
        return assetService.lookUpAssetByName(value);
    }

    @ResponseBody
    @RequestMapping(value = "/city/{value}", method = GET)
    public List<Asset> lookUpAssetByCity(@PathVariable String value) {
        return assetService.lookUpAssetByCity(value);
    }

    @ResponseBody
    @RequestMapping(value = "/country/{value}", method = GET)
    public List<Asset> lookUpAssetByCountry(@PathVariable String value) {
        return assetService.lookUpAssetByCountry(value);
    }

    @ResponseBody
    @RequestMapping(value = "/", method = POST)
    public ResponseDto createAsset(@Valid @RequestBody Asset asset) {
        return assetService.createAsset(asset);
    }

    @ResponseBody
    @RequestMapping(value = "/", method = PUT)
    public ResponseDto updateAsset(@Valid @RequestBody Asset asset) {
        return assetService.updateAsset(asset);
    }

    @ResponseBody
    @RequestMapping(value = "/{rfidId}", method = DELETE)
    public ResponseDto deleteAsset(@PathVariable String rfidId) {
        return assetService.deleteAsset(rfidId);
    }

    @RequestMapping(path = "/report", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> generateReport(@RequestParam String fieldChoice, @RequestParam String choiceValue) throws IOException {
        return assetService.generateReport(fieldChoice, choiceValue);
    }
}
