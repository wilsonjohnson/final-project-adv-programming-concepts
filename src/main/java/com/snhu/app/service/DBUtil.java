package com.snhu.app.service;

import com.mongodb.BasicDBObjectBuilder;

public class DBUtil {
    public enum SortOrder {
        ASCENDING(1),
        DESCENDING(-1),
        TEXT_SCORE(BasicDBObjectBuilder.start("$meta", "textScore").get());

        public final Object value;

        SortOrder(Object value) {
            this.value = value;
        }
    }
}
