package com.microservice.microserviceshoppingcart.domain.pagination;

import java.util.List;

public class PageCustom <T> {
    private List<T> content;
    private int totalElements;
    private int totalPages;
    private int currentPage;
    private boolean ascending;
    private boolean empty;
    private double totalPrice;

    public PageCustom(List<T> content, int totalElements, int totalPages, int currentPage, boolean ascending, double totalPrice) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.ascending = ascending;
        this.empty = content.isEmpty();
        this.totalPrice = totalPrice;
    }
    public PageCustom(){

    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
