package com.example.homedemnage;

import java.util.List;

public class RestCategoryResponse {

    private Integer count;
    private String next;
    private List<Category> results;

    public Integer getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public List getResults() {
        return results;
    }
}
