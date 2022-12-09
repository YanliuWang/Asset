package com.tcss559.asset.service;

import com.tcss559.asset.AssetApplication;
import com.tcss559.asset.dao.AssetDAO;
import com.tcss559.asset.models.Asset;
import com.tcss559.asset.models.dto.ResponseDto;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@Service("AssetService")
public class AssetService {
    @Resource
    private AssetDAO assetDao;

    public Asset lookUpAsset(String RFIDid) {
        Asset asset = assetDao.selectByRFIDid(RFIDid);
        return asset;
    }

    public List<Asset> lookUpAssetByCategory(String value) {
        List<Asset> assets = assetDao.selectByCategory(value);
        return assets;
    }

    public List<Asset> lookUpAssetByName(String value) {
        List<Asset> assets = assetDao.selectByName(value);
        return assets;
    }

    public List<Asset> lookUpAssetByCity(String value) {
        List<Asset> assets = assetDao.selectByCity(value);
        return assets;
    }

    public List<Asset> lookUpAssetByCountry(String value) {
        List<Asset> assets = assetDao.selectByCountry(value);
        return assets;
    }

    public ResponseDto createAsset(Asset asset) {
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
                return ResponseDto.success();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ResponseDto.error("Asset creation failed. Check whether the fields are valid.");
    }

    public ResponseDto updateAsset(Asset newAsset) {
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
            return ResponseDto.success();
        }
        return ResponseDto.error("Asset update failed. Check whether the fields are valid.");
    }

    public ResponseDto deleteAsset(String rfidId) {
        int result = assetDao.delete(rfidId);
        if (result == 1) {
            return ResponseDto.success();
        } else {
            return ResponseDto.error("Asset deletion failed. Check whether rfidId exists.");
        }
    }

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

    public ResponseDto updateLocation(Asset newAsset) {
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
            return ResponseDto.success();
        }
        return ResponseDto.error("Asset location update failed. Check whether the fields are valid.");
    }
}