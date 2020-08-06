package com.gasco.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DispenseLog2 {
    private String href;
    private String id;
    private Skid skid;
    private String createdByUser;
    private String statusId;
    private String logDate;
    private String checks = null;
    private float residualPressure;
    private float initialDispenserReading;
    private float finalDispenserReading;
    private float finalPressure;
    private float temperature;
    private String startTime;
    private String stopTime;
    private float volKG;
    private float volSCM;
    private float totalTime;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Skid getSkid() {
        return skid;
    }

    public void setSkid(Skid skidObject) {
        skid = skidObject;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public Date getLogDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(logDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public String getChecks() {
        return checks;
    }

    public void setChecks(String checks) {
        this.checks = checks;
    }

    public float getResidualPressure() {
        return residualPressure;
    }

    public void setResidualPressure(float residualPressure) {
        this.residualPressure = residualPressure;
    }

    public float getInitialDispenserReading() {
        return initialDispenserReading;
    }

    public void setInitialDispenserReading(float initialDispenserReading) {
        this.initialDispenserReading = initialDispenserReading;
    }

    public float getFinalDispenserReading() {
        return finalDispenserReading;
    }

    public void setFinalDispenserReading(float finalDispenserReading) {
        this.finalDispenserReading = finalDispenserReading;
    }

    public float getFinalPressure() {
        return finalPressure;
    }

    public void setFinalPressure(float finalPressure) {
        this.finalPressure = finalPressure;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public float getVolKG() {
        return volKG;
    }

    public void setVolKG(float volKG) {
        this.volKG = volKG;
    }

    public float getVolSCM() {
        return volSCM;
    }

    public void setVolSCM(float volSCM) {
        this.volSCM = volSCM;
    }

    public float getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }
}
