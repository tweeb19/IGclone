package com.example.PostCRUD.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostModel {
    private int id;
    private int userId;
    private String img;
    private String description;
    private LocalDate createdOn;
}
