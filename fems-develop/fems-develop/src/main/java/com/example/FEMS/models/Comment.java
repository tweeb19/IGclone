package com.example.FEMS.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int id;
    private int postId;
    private String username;
    private String body;
    private LocalDate createdOn;
}
