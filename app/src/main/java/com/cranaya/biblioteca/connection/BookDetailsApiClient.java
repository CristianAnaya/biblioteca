package com.cranaya.biblioteca.connection;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cranaya.biblioteca.AppExecutors;
import com.cranaya.biblioteca.model.BookModel;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class BookDetailsApiClient {
    private static final String TAG = BookDetailsApiClient.class.getSimpleName();

    //LiveData
    private MutableLiveData<BookModel> mBooks;


    private static BookDetailsApiClient instance;

    //making Global RUNNABLE
    private RetrieveBooksByISBNRunnable retrieveBooksByISBNRunnable;

    public static BookDetailsApiClient getInstance(){
        if (instance == null){
            instance = new BookDetailsApiClient();
        }
        return instance;
    }

    private BookDetailsApiClient(){
        mBooks = new MutableLiveData<>();
    }

    public LiveData<BookModel> getBooks(long isbn){
        if (retrieveBooksByISBNRunnable != null){
            retrieveBooksByISBNRunnable = null;
        }

        retrieveBooksByISBNRunnable = new RetrieveBooksByISBNRunnable(isbn);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveBooksByISBNRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
        return mBooks;
    }


    //1- this method that we are going to call through the classes
    public void searchBookDetailsApi(long isbn){

        if (retrieveBooksByISBNRunnable != null){
            retrieveBooksByISBNRunnable = null;
        }

        retrieveBooksByISBNRunnable = new RetrieveBooksByISBNRunnable(isbn);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveBooksByISBNRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);


    }



    // Retreiving data from RestAPI by runnable class
    // We have 2 type of Queries: the ISBN & search Queries
    private class RetrieveBooksByISBNRunnable implements Runnable{

        private long isbn;
        boolean cancelRequest;

        public RetrieveBooksByISBNRunnable(long isbn) {
            this.isbn = isbn;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response<BookModel> response = getBooks(isbn).execute();
                if (cancelRequest){
                    return;
                }
                if (response.code() == 200){
                    BookModel bookModel = response.body();
                    //Sending data to live data
                    //PostValue: used for background thread
                    //setValue: not for background thread
                    mBooks.postValue(bookModel);
                }
                else{
                    String error = response.errorBody().string();
                    Log.v(TAG, "Error "+error);
                    mBooks.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mBooks.postValue(null);
            }

        }

        // Search Method/ Query
        private Call<BookModel> getBooks(long isbn){
            return Service.getBookApi().detailsBook(isbn);
        }

        private void cancelRequest(){
            Log.d(TAG, "Cancelling search Request: ");
            cancelRequest = true;
        }
    }

}
