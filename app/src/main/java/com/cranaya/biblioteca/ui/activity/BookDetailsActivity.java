package com.cranaya.biblioteca.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.cranaya.biblioteca.R;
import com.cranaya.biblioteca.databinding.ActivityBookDetailsBinding;
import com.cranaya.biblioteca.model.BookModel;
import com.cranaya.biblioteca.viewModel.BookDetailsViewModel;

import java.util.Locale;

public class BookDetailsActivity extends AppCompatActivity {
    private static final String TAG = BookDetailsActivity.class.getSimpleName();

    private ActivityBookDetailsBinding activityBookDetailsBinding;
    private BookDetailsViewModel bookDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBookDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_book_details);

        doInitialization();

    }

    private void doInitialization(){
        bookDetailsViewModel = new ViewModelProvider(this).get(BookDetailsViewModel.class);
        Bundle extra = getIntent().getExtras();

        if (extra != null){
            BookModel bookModel = extra.getParcelable(BookModel.TAG);
            if (bookModel != null){
                long isbn = Long.parseLong(bookModel.getIsbn13());
                observerBookDetails(isbn);

            }
        }

        activityBookDetailsBinding.imgBack.setOnClickListener(v -> onBackPressed());
    }

    private void observerBookDetails(long isbn){

        activityBookDetailsBinding.setIsLoading(true);
        bookDetailsViewModel.getBook(isbn).observe(this, bookModel -> {

            if (bookModel != null){
                activityBookDetailsBinding.setIsLoading(false);
                loadBasicBookDetails(bookModel);

            }else{
                Toast.makeText(this, "Ocurrio un error, vuelvelo a intentar mas tarde.", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void loadBasicBookDetails(BookModel bookModel){

        activityBookDetailsBinding.setYear(bookModel.getYear());
        activityBookDetailsBinding.setImageURL(bookModel.getImage());
        activityBookDetailsBinding.setAuthors(bookModel.getAuthors());
        activityBookDetailsBinding.setPublisher(bookModel.getPublisher());
        activityBookDetailsBinding.setTitle(bookModel.getTitle());
        activityBookDetailsBinding.setSubTitle(bookModel.getSubtitle());
        activityBookDetailsBinding.setDesc(String.valueOf(
                HtmlCompat.fromHtml(bookModel.getDesc(),HtmlCompat.FROM_HTML_MODE_LEGACY)
        ));
        activityBookDetailsBinding.setRating(String.format(
                Locale.getDefault(),
                "%.2f",
                Double.parseDouble(bookModel.getRating())
                ));
        activityBookDetailsBinding.setPrice(bookModel.getPrice());



        activityBookDetailsBinding.txtReadMore.setOnClickListener(v -> {
            if (activityBookDetailsBinding.txtReadMore.getText().toString().equals("Read More")){
                activityBookDetailsBinding.txtDesc.setMaxLines(Integer.MAX_VALUE);
                activityBookDetailsBinding.txtDesc.setEllipsize(null);
                activityBookDetailsBinding.txtReadMore.setText(R.string.str_read_less);
            }else{
                activityBookDetailsBinding.txtDesc.setMaxLines(4);
                activityBookDetailsBinding.txtDesc.setEllipsize(TextUtils.TruncateAt.END);
                activityBookDetailsBinding.txtReadMore.setText(R.string.str_read_more);

            }
        });

        activityBookDetailsBinding.btnWebSite.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(bookModel.getUrl()));
            startActivity(intent);
        });

        activityBookDetailsBinding.imgBook.setVisibility(View.VISIBLE);
        activityBookDetailsBinding.txtYear.setVisibility(View.VISIBLE);
        activityBookDetailsBinding.txtAuthors.setVisibility(View.VISIBLE);
        activityBookDetailsBinding.txtPublisher.setVisibility(View.VISIBLE);
        activityBookDetailsBinding.txtTitle.setVisibility(View.VISIBLE);
        activityBookDetailsBinding.txtSubtitle.setVisibility(View.VISIBLE);
        activityBookDetailsBinding.txtDesc.setVisibility(View.VISIBLE);
        activityBookDetailsBinding.txtReadMore.setVisibility(View.VISIBLE);
        activityBookDetailsBinding.viewDivider1.setVisibility(View.VISIBLE);
        activityBookDetailsBinding.viewDivider2.setVisibility(View.VISIBLE);
        activityBookDetailsBinding.layoutMisc.setVisibility(View.VISIBLE);
        activityBookDetailsBinding.btnWebSite.setVisibility(View.VISIBLE);
        activityBookDetailsBinding.btnPdf.setVisibility(View.VISIBLE);
    }


}