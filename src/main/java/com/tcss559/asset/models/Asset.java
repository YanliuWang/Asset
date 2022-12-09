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
    private String city;

    @Getter
    @Setter
    private String state;

    @Getter
    @Setter
    private String ip;

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private String category;

    @Override
    public String toString() {
        return String.format("asset id: %6s | asset name: %6s | asset RFID id: %10s | category: %10s | " +
                "value: $%6.2f | location: %s, %s, %s", assetId, assetName, rfidId, category, value, city,
                state, country);
    }
}
