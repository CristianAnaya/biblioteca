package com.cranaya.biblioteca.connection;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cranaya.biblioteca.AppExecutors;
import com.cranaya.biblioteca.listener.ActionListener;
import com.cranaya.biblioteca.model.BookModel;
import com.cranaya.biblioteca.response.BookSearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class BookApiClient {
    private static final String TAG = BookApiClient.class.getSimpleName();

    //LiveData
    private MutableLiveData<List<BookModel>> mBooks;

    private static BookApiClient instance;

    //making Global RUNNABLE
    private RetrieveBooksRunnable retrieveBooksRunnable;

    public static BookApiClient getInstance(){
        if (instance == null){
            instance = new BookApiClient();
        }
        return instance;
    }

    private BookApiClient(){
        mBooks = new MutableLiveData<>();
    }

    public LiveData<List<BookModel>> getBooks(String name, String page, ActionListener listener){
        if (retrieveBooksRunnable != null){
            retrieveBooksRunnable = null;
        }

        retrieveBooksRunnable = new RetrieveBooksRunnable(name, page,listener);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveBooksRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);

        return mBooks;
    }





    // Retreiving data from RestAPI by runnable class
    // We have 2 type of Queries: the ISBN & search Queries
    private class RetrieveBooksRunnable implements Runnable{

        private String query;
        private String page;
        boolean cancelRequest;
        ActionListener listener;
        public RetrieveBooksRunnable(String query, String page, ActionListener listener) {
            this.query = query;
            this.cancelRequest = false;
            this.page = page;
            this.listener = listener;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getBooksByPage(query, page).execute();
                if (cancelRequest){
                    return;
                }
                if (response.code() == 200){
                    List<BookModel> list = new ArrayList<>(((BookSearchResponse)response.body()).getBooks());

                    //Sending data to live data
                    //PostValue: used for background thread
                    //setValue: not for background thread
                    if (list.size() != 0){
                        mBooks.postValue(list);
                        listener.getResult(true,"success");
                    }else{
                        mBooks.postValue(list);
                        listener.getResult(false,"no se encontro ningun libro.");
                    }

                }
                else{
                    listener.getResult(false,response.message());
                    String error = response.errorBody().string();
                    Log.v(TAG, "Error "+error);
                    mBooks.postValue(null);
                }
            } catch (IOException e) {
                listener.getResult(false,e.getMessage());
                e.printStackTrace();
                mBooks.postValue(null);
            }

        }

            // Search Method/ Query
            private Call<BookSearchResponse> getBooksByPage(String tecnology, String page){
                return Service.getBookApi().searchBookByPage(tecnology,page);
            }

            private void cancelRequest(){
                Log.d(TAG, "Cancelling search Request: ");
                cancelRequest = true;
            }
        }



}
