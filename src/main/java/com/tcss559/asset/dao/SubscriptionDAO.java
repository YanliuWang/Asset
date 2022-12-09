package com.tcss559.asset.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SubscriptionDAO {
    public int addNewSubscription(@Param("userId") int userId, @Param("assetId") int assetId);

    public String getEmail(int userId);

    public String getARN(long assetId);

    public int storeArn(@Param("assetId") long assetId, @Param("arn") String arn);
}
