package com.cranaya.biblioteca.repositories;

import androidx.lifecycle.LiveData;
import com.cranaya.biblioteca.connection.BookApiClient;
import com.cranaya.biblioteca.listener.ActionListener;
import com.cranaya.biblioteca.model.BookModel;

import java.util.List;

public class BookRepository {
    //This class is acting as repositories

    private static BookRepository instance;

    private BookApiClient bookApiClient;

    public static BookRepository getInstance(){
        if (instance == null){
            instance = new BookRepository();
        }

        return instance;
    }

    private BookRepository(){
        bookApiClient = BookApiClient.getInstance();
    }

    public LiveData<List<BookModel>> getBooks(String name, String page, ActionListener listener){
        return bookApiClient.getBooks(name, page,listener);
    }


}
