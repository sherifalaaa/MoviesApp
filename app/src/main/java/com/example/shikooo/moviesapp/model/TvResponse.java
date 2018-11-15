package com.example.shikooo.moviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shikooo on 11/7/2018.
 */

public class TvResponse {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Tv> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Tv> getResults() {
        return results;
    }

    public void setResults(List<Tv> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
