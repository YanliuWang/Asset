package com.tcss559.asset.models;

import lombok.Getter;
import lombok.Setter;

public class Asset {
    @Getter
    @Setter
    private long assetId;

    @Getter
    @Setter
    private String assetName;

    @Getter
    @Setter
    private String rfidId;

    @Getter
    @Setter
    private double value;

    @Getter
    @Setter
    private String location;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String state;

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private String category;
}
