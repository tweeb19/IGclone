package com.example.FEMS.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostFrontEnd {
    private int id;
    private User user;
    private String img;
    private String description;
    private LocalDate createdOn;
    private PageOfItems<Comment> comment;

}
