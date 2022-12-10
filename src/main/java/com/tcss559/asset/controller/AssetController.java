package com.tcss559.asset.controller;

import com.tcss559.asset.models.Asset;
import com.tcss559.asset.models.Response;
import com.tcss559.asset.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @Description: asset controller
 * @Author: Xiaojie Li
 * @Date: 2022/12/9
 */
@Controller
@CrossOrigin
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    /**
     * look up asset
     *
     * @param RFIDid
     * @return Asset
     */
    @ResponseBody
    @RequestMapping(value = "/{RFIDid}", method = GET)
    public Asset lookUpAsset(@PathVariable String RFIDid) {
        return assetService.lookUpAsset(RFIDid);
    }

    /**
     * look up asset by category
     *
     * @param value
     * @return List<Asset>
     */
    @ResponseBody
    @RequestMapping(value = "/category/{value}", method = GET)
    public List<Asset> lookUpAssetByCategory(@PathVariable String value) {
        return assetService.lookUpAssetByCategory(value);
    }

    /**
     * look up asset by name
     *
     * @param value
     * @return List<Asset>
     */
    @ResponseBody
    @RequestMapping(value = "/name/{value}", method = GET)
    public List<Asset> lookUpAssetByName(@PathVariable String value) {
        return assetService.lookUpAssetByName(value);
    }

    /**
     * look up asset by city
     *
     * @param value
     * @return List<Asset>
     */
    @ResponseBody
    @RequestMapping(value = "/city/{value}", method = GET)
    public List<Asset> lookUpAssetByCity(@PathVariable String value) {
        return assetService.lookUpAssetByCity(value);
    }

    /**
     * look up asset by country
     *
     * @param value
     * @return List<Asset>
     */
    @ResponseBody
    @RequestMapping(value = "/country/{value}", method = GET)
    public List<Asset> lookUpAssetByCountry(@PathVariable String value) {
        return assetService.lookUpAssetByCountry(value);
    }

    /**
     * create asset
     *
     * @param  asset
     * @return Response
     */
    @ResponseBody
    @RequestMapping(value = "/", method = POST)
    public Response createAsset(@Valid @RequestBody Asset asset) {
        return assetService.createAsset(asset);
    }

    /**
     * update asset
     *
     * @param  asset
     * @return Response
     */
    @ResponseBody
    @RequestMapping(value = "/", method = PUT)
    public Response updateAsset(@Valid @RequestBody Asset asset) {
        return assetService.updateAsset(asset);
    }

    /**
     * delete asset
     *
     * @param  rfidId
     * @return Response
     */
    @ResponseBody
    @RequestMapping(value = "/{rfidId}", method = DELETE)
    public Response deleteAsset(@PathVariable String rfidId) {
        return assetService.deleteAsset(rfidId);
    }

    /**
     * generate report
     *
     * @param  fieldChoice
     * @param choiceValue
     * @return ResponseEntity
     */
    @RequestMapping(path = "/report", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> generateReport(@RequestParam String fieldChoice, @RequestParam String choiceValue) throws IOException {
        return assetService.generateReport(fieldChoice, choiceValue);
    }
}
