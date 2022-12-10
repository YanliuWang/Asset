package com.tcss559.asset.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description: subscription DAO
 * @Author: Xiaojie Li
 * @Date: 2022/12/9
 */
@Repository
public interface SubscriptionDAO {
    /**
     * add new subscription
     *
     * @param userId
     * @param assetId
     * @return int
     */
    public int addNewSubscription(@Param("userId") int userId, @Param("assetId") int assetId);

    /**
     * get email
     *
     * @param userId
     * @return String
     */
    public String getEmail(int userId);

    /**
     * get arn
     *
     * @param assetId
     * @return String
     */
    public String getARN(long assetId);

    /**
     * store arn
     *
     * @param assetId
     * @param arn
     * @return int
     */
    public int storeArn(@Param("assetId") long assetId, @Param("arn") String arn);
}
