package com.cranaya.biblioteca.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cranaya.biblioteca.R;
import com.cranaya.biblioteca.adapters.BookAdapter;
import com.cranaya.biblioteca.databinding.ActivityListBinding;
import com.cranaya.biblioteca.listener.ActionListener;
import com.cranaya.biblioteca.listener.BookListener;
import com.cranaya.biblioteca.model.BookModel;
import com.cranaya.biblioteca.ui.activity.BookDetailsActivity;
import com.cranaya.biblioteca.viewModel.BookListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements BookListener, ActionListener {
    private static final String TAG = ListActivity.class.getSimpleName();

    //ViewModel
    private BookListViewModel bookListViewModel;
    private ActivityListBinding binding;
    private List<BookModel> books = new ArrayList<>();
    private BookAdapter bookAdapter;
    private String technology;
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_list);


       if (getIntent().getStringExtra("technology") != null){
           technology = getIntent().getStringExtra("technology");
           setupActionBar("Libros de (" + technology + ")");
       }
        doInitialization();
    }


    private void doInitialization(){
        bookListViewModel = new ViewModelProvider(this).get(BookListViewModel.class);
        binding.rvBooks.setHasFixedSize(true);
        bookAdapter = new BookAdapter(books,this);
        binding.rvBooks.setAdapter(bookAdapter);
        observeAnyChange();
        binding.swipeRefresh.setOnRefreshListener(this::observeAnyChange);
        //searchBookApi(technology);

        //Calling the observers

    }

    // Observing anny data change
    private void observeAnyChange(){
        showProgress();

        bookListViewModel.getBooks(technology,String.valueOf(currentPage),this).observe(this, bookModels -> {
            hideProgress();
            // Observing for any data change
            if (bookModels != null) {
                int oldCount = bookModels.size();
                books.addAll(bookModels);
                bookAdapter.notifyItemRangeInserted(oldCount, bookModels.size());
            }else{
                Toast.makeText(this, "No se encontro ningun libro para la categoria "+technology, Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void hideProgress() {
        binding.shimmer.stopShimmer();
        binding.shimmer.setVisibility(View.GONE);
        binding.rvBooks.setVisibility(View.VISIBLE);
    }

    private void showProgress() {
        binding.emptyView.setVisibility(View.GONE);
        binding.shimmer.startShimmer();
        binding.shimmer.setVisibility(View.VISIBLE);
        binding.rvBooks.setVisibility(View.GONE);
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
            binding.emptyView.setVisibility(View.VISIBLE);
            binding.txtEmptyErr.setText("Ocurrio un problema, comprueba tu conexión a internet.");
        }else{
            binding.emptyView.setVisibility(View.GONE);
        }

    }

    private void setupActionBar(String title){
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}