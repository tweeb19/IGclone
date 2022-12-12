package com.example.FEMS.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageOfItems <T>{
    private List<T> items;
    private boolean hasNext;
    private int totalElements;
}
