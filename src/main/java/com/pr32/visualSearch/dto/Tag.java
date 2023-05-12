package com.pr32.visualSearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tag {

    @JsonProperty(value = "tag")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
