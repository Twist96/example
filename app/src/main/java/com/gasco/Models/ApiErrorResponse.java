package com.gasco.Models;

public class ApiErrorResponse {
    private String status;
    private float code;
    private String message;
    private String developerMessage;
    private String moreInfoUrl;
    private String throwable = null;


    // Getter Methods

    public String getStatus() {
        return status;
    }

    public float getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public String getMoreInfoUrl() {
        return moreInfoUrl;
    }

    public String getThrowable() {
        return throwable;
    }

    // Setter Methods

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCode(float code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public void setMoreInfoUrl(String moreInfoUrl) {
        this.moreInfoUrl = moreInfoUrl;
    }

    public void setThrowable(String throwable) {
        this.throwable = throwable;
    }
}
