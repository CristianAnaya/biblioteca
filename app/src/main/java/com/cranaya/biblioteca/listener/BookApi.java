package com.cranaya.biblioteca.listener;

import com.cranaya.biblioteca.model.BookModel;
import com.cranaya.biblioteca.response.BookSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookApi {

    //Search for books
    @GET("search/{query}")
    Call<BookSearchResponse> searchBook(
            @Path("query") String query
    );



    //Search for books by page
    @GET("search/{query}/{page}")
    Call<BookSearchResponse> searchBookByPage(
            @Path("query") String query,
            @Path("page") String page
    );

    //Search for books by page
    @GET("books/{isbn13}")
    Call<BookModel> detailsBook(
            @Path("isbn13") long isbn13
    );
}
