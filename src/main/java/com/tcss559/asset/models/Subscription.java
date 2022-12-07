package com.tcss559.asset.models;

import lombok.Getter;
import lombok.Setter;

public class Subscription {
    @Getter
    @Setter
    private int userId;

    @Getter
    @Setter
    private int assetId;
}
