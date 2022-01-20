package com.cranaya.biblioteca.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cranaya.biblioteca.listener.ActionListener;
import com.cranaya.biblioteca.model.BookModel;
import com.cranaya.biblioteca.repositories.BookRepository;

import java.util.List;

public class BookListViewModel extends ViewModel {

    //this class is used for VIEWMODEL

    private BookRepository bookRepository;

    //Constructor
    public BookListViewModel() {
        bookRepository = BookRepository.getInstance();
    }

    public LiveData<List<BookModel>> getBooks(String name, String page, ActionListener listener){
        return bookRepository.getBooks(name, page, listener);
    }

}
