package com.tcss559.asset.service;

import com.tcss559.asset.dao.AssetDAO;
import com.tcss559.asset.models.Asset;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service("AssetService")
public class AssetService {
    @Resource
    private AssetDAO assetDao;

    public Asset lookUpAsset(HttpServletRequest request, HttpServletResponse response, String RFIDid) {
        Asset asset = assetDao.selectByRFIDid(RFIDid);
        return asset;
    }

    public List<Asset> lookUpAssetByCategory(HttpServletRequest request, HttpServletResponse response, String value) {
        List<Asset> assets = assetDao.selectByCategory(value);
        return assets;
    }
}