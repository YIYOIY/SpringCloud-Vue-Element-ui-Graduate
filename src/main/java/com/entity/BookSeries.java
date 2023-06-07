package com.entity;

import lombok.ToString;

@ToString
public class BookSeries {
    private int seriesId;
    private String seriesName;
    public BookSeries() {
    }

    public BookSeries(String seriesName) {
        this.seriesName = seriesName;
    }

    public BookSeries(int seriesId, String seriesName) {
        this.seriesId = seriesId;
        this.seriesName = seriesName;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
}
