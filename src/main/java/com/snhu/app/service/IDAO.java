package com.snhu.app.service;

import com.mongodb.*;
import com.snhu.app.model.Tuple;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * IDAO
 */
public interface IDAO {
    public static final DBObject SUCCESS = new BasicDBObject("success", true);

    public DBCollection getCollection();

    /**
     * Generic create method for all classes implementing IDAO
     */
    public default boolean create(DBObject item) throws MongoException, NullPointerException {
        Objects.requireNonNull(item, "Item to create must not be null");
        getCollection().insert(item);
        return true;
    }

    /**
     * Generic find method for all classes implementing IDAO
     */
    public default Stream<DBObject> find(DBObject find) throws NullPointerException {
        Objects.requireNonNull(find, "Item to find must not be null");
        return StreamSupport.stream(getCollection().find(find).spliterator(), false).peek(map -> map.removeField("_id"));
    }

    /**
     * Generic find and project method for all classes implementing IDAO
     */
    public default Stream<DBObject> find(DBObject find, DBObject project) throws NullPointerException {
        Objects.requireNonNull(find, "Item to find must not be null");
        return StreamSupport.stream(getCollection().find(find, project).spliterator(), false).peek(map -> map.removeField("_id"));
    }

    /**
     * Generic count method for all classes implementing IDAO
     */
    public default int count(DBObject find) throws NullPointerException {
        Objects.requireNonNull(find, "Item to find must not be null");
        return getCollection().find(find).count();
    }

    /**
     * Generic update method for all classes implementing IDAO
     */
    public default DBObject update(DBObject query, DBObject update) throws NullPointerException {
        Objects.requireNonNull(query, "Item to update must not be null");
        WriteResult result = getCollection().update(query, update, false, true);
        return build("msg", result.getError())
                .add("changed", result.getN()).get();
    }

    /**
     * Generic delete method for all classes implementing IDAO
     */
    public default DBObject delete(DBObject item) throws NullPointerException {
        Objects.requireNonNull(item, "Item to delete must not be null");
        WriteResult result = getCollection().remove(item);
        return build("msg", result.getError())
                .add("changed", result.getN()).get();
    }

    /**
     * Generic aggregate method for all classes implementing IDAO
     */
    public default Stream<DBObject> aggregate(List<DBObject> pipeline) {
        return StreamSupport.stream(getCollection().aggregate(pipeline).results().spliterator(), false);
    }

    /**
     * Helper method for creating BasicDBObjectBuilders
     */
    public default BasicDBObjectBuilder build() {
        return BasicDBObjectBuilder.start();
    }

    /**
     * Helper method for creating BasicDBObjectBuilders
     */
    public default BasicDBObjectBuilder build(Map map) {
        return BasicDBObjectBuilder.start(map);
    }

    /**
     * Helper method for creating BasicDBObjectBuilders
     */
    public default BasicDBObjectBuilder build(String key, Object value) {
        return BasicDBObjectBuilder.start(key, value);
    }

    /**
     * Helper method for creating BasicDBObjects
     */
    public default DBObject object(String key, Object value) {
        return build(key, value).get();
    }

    /**
     * Helper method for creating BasicDBObjects
     */
    public default DBObject object(Map map) {
        return build(map).get();
    }

    public default <FIRST, SECOND> Tuple<FIRST, SECOND> pair(FIRST first, SECOND second) {
        return Tuple.of(first, second);
    }

    /**
     * Helper method for creating QueryBuilders
     */
    public default QueryBuilder query() {
        return QueryBuilder.start();
    }

    /**
     * Helper method for creating QueryBuilders
     */
    public default QueryBuilder queryWhere(String key) {
        return QueryBuilder.start(key);
    }

    /**
     * Helper method for creating Aggregation pipelines
     */
    public default List<DBObject> pipeline(DBObject... stages) {
        return Arrays.asList(stages);
    }

    /**
     * Helper method for the aggregate function $match
     *
     * @param toMatch
     * @return
     */
    public default DBObject $match(DBObject toMatch) {
        return queryWhere("$match").is(toMatch).get();
    }

    /**
     * Helper method for the aggregate function $sum
     *
     * @param toSum field to sum
     * @return
     */
    public default DBObject $sum(String toSum) {
        return object("$sum", toSum);
    }

    /**
     * Helper method for the aggregate function $sum
     *
     * @param toSum object to sum
     * @return
     */
    public default DBObject $sum(DBObject toSum) {
        return object("$sum", toSum);
    }

    /**
     * Helper method for the aggregate function $group
     *
     * @param toGroup object key-value pairs to group
     * @return
     */
    public default DBObject $group(Tuple<String, Object>... toGroup) {
        return queryWhere("$group").is(
                build(
                        Arrays.stream(toGroup).parallel().collect(
                                HashMap<String, Object>::new,
                                (a, b) -> a.put(b.getFirst(), b.getSecond()),
                                Map::putAll)
                ).get()
        ).get();
    }

    /**
     * Helper method for the aggregate function $sort
     *
     * @param orders sort order key value pairs
     * @return
     */
    public default DBObject $sort(Tuple<String, DBUtil.SortOrder>... orders) {
        return queryWhere("$sort").is(
                build(
                        Arrays.stream(orders).parallel().collect(
                                HashMap<String, Object>::new,
                                (a, b) -> a.put(b.getFirst(), b.getSecond().value),
                                Map::putAll)
                ).get()
        ).get();
    }

    /**
     * Helper method for the aggregate function $limit
     *
     * @param limit
     * @return
     */
    public default DBObject $limit(int limit) {
        return queryWhere("$limit").is(limit).get();
    }
}