package com.tcss559.asset.dao;

import com.tcss559.asset.models.Asset;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetDAO {
    public Asset selectByRFIDid(String RFIDid);

    public List<Asset> selectByCategory(String category);

    public List<Asset> selectByName(String name);

    public List<Asset> selectByCity(String city);

    public List<Asset> selectByCountry(String country);

    public int insert(Asset asset);

    public int update(Asset asset);

    public int delete(String rfidId);
}
