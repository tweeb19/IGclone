package com.example.PostCRUD.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    @Column(length = 2000000)
    private String img;
    private String description;
    private LocalDate createdOn;

}
