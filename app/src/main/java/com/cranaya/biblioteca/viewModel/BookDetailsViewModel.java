package com.cranaya.biblioteca.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.cranaya.biblioteca.model.BookModel;
import com.cranaya.biblioteca.repositories.BookDetailsRepository;

public class BookDetailsViewModel extends ViewModel {
    private static final String TAG = BookDetailsViewModel.class.getSimpleName();

    private BookDetailsRepository bookDetailsRepository;

    //Constructor
    public BookDetailsViewModel() {
        bookDetailsRepository = BookDetailsRepository.getInstance();
    }

    public LiveData<BookModel> getBook(long isbn){
        return bookDetailsRepository.getBooks(isbn);
    }

    //3- Calling method in view-model
   // public void searchBookApi(long isbn){
     //   bookDetailsRepository.searchBooksApi(isbn);
    //}

}
