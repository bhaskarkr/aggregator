package com.catalog.aggregator.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class EventUtils {
    public static String getProductUserEventKey(final String productId, final String userId) {
        return productId + "_" + userId;
    }

    public static String getProductIdFromEventKey(final String eventKey) {
        return eventKey.substring(0, eventKey.indexOf('_'));
    }

    public static String getUserIdFromEventKey(final String eventKey) {
        return eventKey.substring(eventKey.indexOf('_') + 1);
    }
}
