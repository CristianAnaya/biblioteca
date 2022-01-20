package com.cranaya.biblioteca.response;

import com.cranaya.biblioteca.model.BookModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

// this class is for getting multiple books (books list)
public class BookSearchResponse {

    @SerializedName("total")
    @Expose
    private String total;

    @SerializedName("page")
    @Expose
    private String page;

    @SerializedName("books")
    @Expose
    private ArrayList<BookModel> books;

    public String getTotal() {
        return total;
    }

    public String getPage() {
        return page;
    }

    public ArrayList<BookModel> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "BookSearchResponse{" +
                "total='" + total + '\'' +
                ", page='" + page + '\'' +
                ", books=" + books +
                '}';
    }
}
