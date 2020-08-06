package com.gasco.Models;

public class DispenseLogUpdate {
    private String statusIdTo;
    private String dataValue;
    private String dataName;

    public DispenseLogUpdate(String statusIdTo, String dataValue, String dataName) {
        this.statusIdTo = statusIdTo;
        this.dataValue = dataValue;
        this.dataName = dataName;
    }

    public String getStatusIdTo() {
        return statusIdTo;
    }

    public String getDataValue() {
        return dataValue;
    }
}
