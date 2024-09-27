package com.microservice.microserviceshoppingcart.domain.pagination;

public class PageRequestCustom {
    private int page;
    private int size;
    private String sortField;
    private boolean ascending;

    public PageRequestCustom(int page, int size, String sortField, boolean ascending) {
        this.page = page;
        this.size = size;
        this.sortField = sortField;
        this.ascending = ascending;
    }

    public PageRequestCustom() {
    }
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }
}
