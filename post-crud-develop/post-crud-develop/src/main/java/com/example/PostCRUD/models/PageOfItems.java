package com.example.PostCRUD.models;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PageOfItems<T> {
    private List<T> items;
    private boolean hasNext;
    private int totalElements;

    public PageOfItems(List<Post> list, HttpHeaders httpHeaders, HttpStatus ok) {
        this.items = new ArrayList<>();
    }

    public PageOfItems(List<T> items, boolean hasNext, int totalElements) {
        this.items = items;
        this.hasNext = hasNext;
        this.totalElements = totalElements;
    }

    public PageOfItems(Page<T> page) {
        this.items = page.toList();
        this.hasNext = page.hasNext();
        this.totalElements = (int)page.getTotalElements();
    }

    public PageOfItems() {

    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PageOfItems that = (PageOfItems) obj;
        return this.hasNext == that.hasNext && this.totalElements == that.totalElements && Objects.equals(this.items, that.items);
    }

    @Override
    public String toString() {
        return "PageOfItems{" +
                "items=" + items +
                ", hasNext=" + hasNext +
                ", totalElements=" + totalElements +
                "}";
    }
}
