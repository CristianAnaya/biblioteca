package com.cranaya.biblioteca.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cranaya.biblioteca.R;
import com.cranaya.biblioteca.adapters.BookAdapter;
import com.cranaya.biblioteca.databinding.ActivityBookListBinding;
import com.cranaya.biblioteca.listener.ActionListener;
import com.cranaya.biblioteca.listener.BookListener;
import com.cranaya.biblioteca.model.BookModel;
import com.cranaya.biblioteca.viewModel.BookListViewModel;
import com.haerul.bottomfluxdialog.BottomFluxDialog;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity implements BookListener, ActionListener {
    private static final String TAG = BookListActivity.class.getSimpleName();

    //ViewModel
    private BookListViewModel bookListViewModel;
    private ActivityBookListBinding activityBookListBinding;
    private List<BookModel> books = new ArrayList<>();
    private BookAdapter bookAdapter;
    private String technology;
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBookListBinding = DataBindingUtil.setContentView(this,R.layout.activity_book_list);

        technology = getIntent().getStringExtra("technology");

        doInitialization();
    }

    private void doInitialization(){

        bookListViewModel = new ViewModelProvider(BookListActivity.this).get(BookListViewModel.class);
        activityBookListBinding.rvBooks.setHasFixedSize(true);
        bookAdapter = new BookAdapter(books,this);
        activityBookListBinding.rvBooks.setAdapter(bookAdapter);

        activityBookListBinding.swipeRefresh.setOnRefreshListener(() -> observeAnyChange("java"));
        
        activityBookListBinding.search.setOnClickListener(v -> {
            showDialogSearch();
        });

    }



    private void showDialogSearch() {
        BottomFluxDialog.inputDialog(this)
                .setTextTitle("Search")
                .setTextMessage("What book are you looking for?")
                .setRightButtonText("SUBMIT")
                .setInputListener(new BottomFluxDialog.OnInputListener() {
                    @Override
                    public void onSubmitInput(String text) {
                        if (text.isEmpty()){
                            Toast.makeText(BookListActivity.this, "You have not selected any book.", Toast.LENGTH_SHORT).show();
                        }else{
                            books.clear();
                            observeAnyChange(text);
                        }

                    }

                    @Override
                    public void onCancelInput() {
                        Toast.makeText(BookListActivity.this, "Button Cancel Clicked!", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    // Observing anny data change
    private void observeAnyChange(String search){
        showProgress();

        bookListViewModel.getBooks(search,String.valueOf(currentPage),this).observe(this, bookModels -> {
            hideProgress();
            // Observing for any data change
            if (bookModels != null){
                activityBookListBinding.collapsing.setTitle(search);
                books.addAll(bookModels);
                bookModels.clear();
                bookAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(BookListActivity.this, "No se encontro ningun libro para la categoria "+technology, Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void hideProgress() {
        activityBookListBinding.swipeRefresh.setRefreshing(false);
        activityBookListBinding.shimmer.stopShimmer();
        activityBookListBinding.shimmer.setVisibility(View.GONE);
        activityBookListBinding.rvBooks.setVisibility(View.VISIBLE);
    }

    private void showProgress() {
        activityBookListBinding.swipeRefresh.setRefreshing(true);
        activityBookListBinding.shimmer.startShimmer();
        activityBookListBinding.emptyView.setVisibility(View.GONE);
        activityBookListBinding.shimmer.setVisibility(View.VISIBLE);
        activityBookListBinding.rvBooks.setVisibility(View.GONE);
    }


    @Override
    public void onBookClicked(BookModel bookModel) {
        Intent intent = new Intent(getApplicationContext(), BookDetailsActivity.class);
        intent.putExtra(BookModel.TAG, bookModel);
        startActivity(intent);

    }

    @Override
    public void getResult(boolean status, String meesage) {
        if (!status){
            Toast.makeText(this, ""+meesage, Toast.LENGTH_SHORT).show();
            activityBookListBinding.emptyView.setVisibility(View.VISIBLE);
            activityBookListBinding.txtEmptyErr.setText(meesage);
        }else{
            activityBookListBinding.emptyView.setVisibility(View.GONE);
        }

    }
}