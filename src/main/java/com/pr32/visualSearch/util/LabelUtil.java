package com.pr32.visualSearch.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class LabelUtil {

    public String[] getLabels(Map<String, Float> extractedLabels) {
        List<String> labels = new ArrayList<>();
        for(String str: extractedLabels.keySet()){
            String temp = str;
            temp = temp.toLowerCase();
            String[] tags = temp.split(" ");
            labels.addAll(Arrays.asList(tags));
        }
        return labels.toArray(new String[0]);
    }

}
