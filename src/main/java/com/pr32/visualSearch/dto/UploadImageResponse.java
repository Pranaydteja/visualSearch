package com.pr32.visualSearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UploadImageResponse {

    @JsonProperty("url")
    private String url;

    @JsonProperty("labels")
    private String[] labels;

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
