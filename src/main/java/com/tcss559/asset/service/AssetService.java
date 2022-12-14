package com.tcss559.asset.service;

import com.tcss559.asset.dao.AssetDAO;
import com.tcss559.asset.models.Asset;
import com.tcss559.asset.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: asset service
 * @Author: Xiaojie Li
 * @Date: 2022/12/9
 */
@Service("AssetService")
public class AssetService {
    @Resource
    private AssetDAO assetDao;

    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * look up asset
     *
     * @param RFIDid
     * @return Asset
     */
    public Asset lookUpAsset(String RFIDid) {
        Asset asset = assetDao.selectByRFIDid(RFIDid);
        return asset;
    }

    /**
     * look up asset by category
     *
     * @param value
     * @return List<Asset>
     */
    public List<Asset> lookUpAssetByCategory(String value) {
        List<Asset> assets = assetDao.selectByCategory(value);
        return assets;
    }

    /**
     * look up asset by name
     *
     * @param value
     * @return List<Asset>
     */
    public List<Asset> lookUpAssetByName(String value) {
        List<Asset> assets = assetDao.selectByName(value);
        return assets;
    }

    /**
     * look up asset by city
     *
     * @param value
     * @return List<Asset>
     */
    public List<Asset> lookUpAssetByCity(String value) {
        List<Asset> assets = assetDao.selectByCity(value);
        return assets;
    }

    /**
     * look up asset by country
     *
     * @param value
     * @return List<Asset>
     */
    public List<Asset> lookUpAssetByCountry(String value) {
        List<Asset> assets = assetDao.selectByCountry(value);
        return assets;
    }

    /**
     * create asset
     *
     * @param  asset
     * @return Response
     */
    public Response createAsset(Asset asset) {
        String ip = asset.getIp();
        String ipUri = String.format("https://ep.api.getfastah.com/whereis/v1/json/%s", ip);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Fastah-Key", "2932d97afcd64d27ad4e8fb2de390104");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> result = restTemplate.exchange(ipUri, HttpMethod.GET, entity, String.class);
        try {
            JSONObject resultJson = new JSONObject(result.getBody());
            JSONObject locationJson = resultJson.getJSONObject("locationData");
            String cityName = locationJson.getString("cityName");
            String[] cityRaw = cityName.split("\\s*,\\s*");
            asset.setCity(cityRaw[0]);
            asset.setState(cityRaw[1]);
            asset.setCountry(locationJson.getString("countryCode"));
            int isCreated = assetDao.insert(asset);
            if (isCreated == 1) {
                Asset newAsset = assetDao.selectByRFIDid(asset.getRfidId());
                subscriptionService.createTopic(newAsset.getAssetId());
                return Response.success();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Response.error("Asset creation failed. Check whether the fields are valid.");
    }

    /**
     * update asset
     *
     * @param  asset
     * @return Response
     */
    public Response updateAsset(Asset newAsset) {
        Asset oldAsset = assetDao.selectByRFIDid(newAsset.getRfidId());
        if (newAsset.getAssetName() == null) {
            newAsset.setAssetName(oldAsset.getAssetName());
        }
        if (newAsset.getValue() == 0) {
            newAsset.setValue(oldAsset.getValue());
        }
        if (newAsset.getCity() == null) {
            newAsset.setCity(oldAsset.getCity());
        }
        if (newAsset.getState() == null) {
            newAsset.setState(oldAsset.getState());
        }
        if (newAsset.getCountry() == null) {
            newAsset.setCountry(oldAsset.getCountry());
        }
        if (newAsset.getCategory() == null) {
            newAsset.setCategory(oldAsset.getCategory());
        }

        int isUpdated = assetDao.update(newAsset);
        if (isUpdated == 1) {
            return Response.success();
        }
        return Response.error("Asset update failed. Check whether the fields are valid.");
    }

    /**
     * delete asset
     *
     * @param  rfidId
     * @return Response
     */
    public Response deleteAsset(String rfidId) {
        int result = assetDao.delete(rfidId);
        if (result == 1) {
            return Response.success();
        } else {
            return Response.error("Asset deletion failed. Check whether rfidId exists.");
        }
    }

    /**
     * generate report
     *
     * @param  fieldChoice
     * @param choiceValue
     * @return ResponseEntity
     */
    public ResponseEntity<InputStreamResource> generateReport(String fieldChoice, String choiceValue) {
        try {
            File f = new File("report.txt");
            FileWriter fWriter = new FileWriter(f);
            fWriter.write("Asset Management System Report\n\n");
            fWriter.write(String.format("The assets satisfying %s = %s:\n", fieldChoice, choiceValue));

            List<Asset> assets = new ArrayList<>();
            if (fieldChoice.equals("category")) {
                assets = assetDao.selectByCategory(choiceValue);
            } else if (fieldChoice.equals("country")) {
                assets = assetDao.selectByCountry(choiceValue);
            } else if (fieldChoice.equals("city")) {
                assets = assetDao.selectByCity(choiceValue);
            } else if (fieldChoice.equals("name")) {
                assets = assetDao.selectByName(choiceValue);
            }

            double valueSum = 0d;
            for (Asset a: assets) {
                fWriter.write(a.toString() + "\n");
                valueSum += a.getValue();
            }

            fWriter.write(String.format("\nThe asset value sum is $%.2f.", valueSum));
            fWriter.close();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(f));

            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.txt");
            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(f.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * get location
     *
     * @param rfidId
     * @return String
     */
    public String getLocation(String rfidId) {
        Asset asset = assetDao.selectByRFIDid(rfidId);
        JSONObject jo = new JSONObject();
        try{
            jo.put("rfidId", rfidId);
            jo.put("city", asset.getCity());
            jo.put("state", asset.getState());
            jo.put("country", asset.getCountry());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo.toString();
    }

    /**
     * update location
     *
     * @param headers
     * @param newAsset
     * @return Response
     */
    public Response updateLocation(Map<String, String> headers, Asset newAsset) {
        Asset asset = assetDao.selectByRFIDid(newAsset.getRfidId());
        if (newAsset.getCity() != null) {
            asset.setCity(newAsset.getCity());
        }
        if (newAsset.getState() != null) {
            asset.setState(newAsset.getState());
        }
        if (newAsset.getCountry() != null) {
            asset.setCountry(newAsset.getCountry());
        }

        int isUpdated = assetDao.update(asset);
        if (isUpdated == 1) {
            String message = String.format("%s(RFID: %s) is now transported to %s, %s, %s.", asset.getAssetName(),
                    asset.getRfidId(), asset.getCity(), asset.getState(), asset.getCountry());
            subscriptionService.publish(getUserId(headers), asset.getAssetId(), message);
            return Response.success();
        } else {
            return Response.error("Asset location update failed. Check whether the fields are valid.");
        }
    }

    /**
     * get user id
     *
     * @param headers
     * @return int
     */
    private int getUserId(Map<String, String> headers) {
        String token = headers.getOrDefault("token", null);
        if (token == null) return -1;
        String result[] = token.split("\\s*-\\s*");
        String userId = result[result.length - 1];

        if (userId == null) return -1;
        return Integer.parseInt(userId);
    }


}