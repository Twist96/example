package com.gasco.Models;

import java.util.ArrayList;

public class ApiListResponse<T> {

    private String href;
    private float offset;
    private float size;
    private float limit;
    ArrayList< T > items = new ArrayList < T > ();


    // Getter Methods

    public String getHref() {
        return href;
    }

    public float getOffset() {
        return offset;
    }

    public float getSize() {
        return size;
    }

    public float getLimit() {
        return limit;
    }

    // Setter Methods

    public void setHref(String href) {
        this.href = href;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setLimit(float limit) {
        this.limit = limit;
    }

    public ArrayList<T> getItems() {
        return items;
    }
}
