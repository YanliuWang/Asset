package com.tcss559.asset.dao;

import com.tcss559.asset.models.Asset;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: asset DAO
 * @Author: Xiaojie Li
 * @Date: 2022/12/9
 */
@Repository
public interface AssetDAO {

    /**
     * select asset By RFIDid
     *
     * @param RFIDid
     * @return Asset
     */
    public Asset selectByRFIDid(String RFIDid);

    /**
     * select asset By category
     *
     * @param category
     * @return List<Asset>
     */
    public List<Asset> selectByCategory(String category);

    /**
     * select asset By name
     *
     * @param name
     * @return List<Asset>
     */
    public List<Asset> selectByName(String name);

    /**
     * select asset By city
     *
     * @param city
     * @return List<Asset>
     */
    public List<Asset> selectByCity(String city);

    /**
     * select asset By country
     *
     * @param country
     * @return List<Asset>
     */
    public List<Asset> selectByCountry(String country);

    /**
     * insert asset
     *
     * @param asset
     * @return int
     */
    public int insert(Asset asset);

    /**
     * update asset
     *
     * @param asset
     * @return int
     */
    public int update(Asset asset);

    /**
     * delete asset
     *
     * @param rfidId
     * @return int
     */
    public int delete(String rfidId);
}
