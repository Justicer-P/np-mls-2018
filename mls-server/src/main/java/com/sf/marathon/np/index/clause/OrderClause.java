package com.sf.marathon.np.index.clause;

/**
 * Created by 204062 on 2015/9/21.
 */
public class OrderClause {
    private String fieldName;
    private GeoPoint geoPoint;
    private Order order;

    public OrderClause(String fieldName, Order order) {
        this.fieldName = fieldName;
        this.order = order;
    }

    public OrderClause(String fieldName, GeoPoint geoPoint, Order order) {
        this.fieldName = fieldName;
        this.geoPoint = geoPoint;
        this.order = order;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}


