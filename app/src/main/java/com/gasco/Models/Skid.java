package com.gasco.Models;

public class Skid {
    private String href;
    private String id;
    private String skidNo;
    private String skidType;
    private String fromDate;
    private String thruDate;
    private float cylinders;
    private float capacity;
    private boolean active;
    private boolean functional;

    public Skid(){}

    public Skid(String skidId){
        setId(skidId);
    }

    // Getter Methods

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public String getSkidNo() {
        return skidNo;
    }

    public String getSkidType() {
        return skidType;
    }

    public float getCylinders() {
        return cylinders;
    }

    public float getCapacity() {
        return capacity;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getThruDate() {
        return thruDate;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isFunctional() {
        return functional;
    }

    // Setter Methods

    public void setHref(String href) {
        this.href = href;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSkidNo(String skidNo) {
        this.skidNo = skidNo;
    }

    public void setSkidType(String skidType) {
        this.skidType = skidType;
    }

    public void setCylinders(float cylinders) {
        this.cylinders = cylinders;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setThruDate(String thruDate) {
        this.thruDate = thruDate;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setFunctional(boolean functional) {
        this.functional = functional;
    }
}
