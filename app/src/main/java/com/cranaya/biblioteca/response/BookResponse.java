package com.cranaya.biblioteca.response;

import com.cranaya.biblioteca.model.BookModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// This class is for single movie request
public class BookResponse {

    @SerializedName("books")
    @Expose
    private BookModel books;

    public BookModel getBook() {
        return books;
    }

    @Override
    public String toString() {
        return "BookResponse{" +
                "book=" + books +
                '}';
    }
}
