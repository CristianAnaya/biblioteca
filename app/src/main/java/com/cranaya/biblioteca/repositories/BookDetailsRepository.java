package com.cranaya.biblioteca.repositories;

import androidx.lifecycle.LiveData;

import com.cranaya.biblioteca.connection.BookDetailsApiClient;
import com.cranaya.biblioteca.model.BookModel;


public class BookDetailsRepository {

    //This class is acting as repositories

    private static BookDetailsRepository instance;

    private BookDetailsApiClient bookDetailsApiClient;

    public static BookDetailsRepository getInstance(){
        if (instance == null){
            instance = new BookDetailsRepository();
        }

        return instance;
    }

    private BookDetailsRepository(){
        bookDetailsApiClient = BookDetailsApiClient.getInstance();
    }

    public LiveData<BookModel> getBooks(long isbn){
        return bookDetailsApiClient.getBooks(isbn);
    }

    //2- Calling the method in repository
    //public void searchBooksApi(long isbn){
     //   bookDetailsApiClient.searchBookDetailsApi(isbn);
    //}
}
