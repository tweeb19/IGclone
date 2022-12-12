package com.example.FEMS.services;


import com.example.FEMS.models.PageOfItems;
import com.example.FEMS.models.PostFrontEnd;



public interface PostFrontEndService {
    PageOfItems<PostFrontEnd> getAll(int pageNumber, int pageSize);
}
