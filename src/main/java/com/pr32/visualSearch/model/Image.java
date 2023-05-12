package com.pr32.visualSearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "images")
public class Image {

    @Id
    private String id;
    private String name;
    private String url;
    private String[] labels;

    public Image(){}

    public Image(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Image(String name, String url, String[] labels) {
        this.name = name;
        this.url = url;
        this.labels = labels;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public void setId(String id) {
        this.id = id;
    }


}
