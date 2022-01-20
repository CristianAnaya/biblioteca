package com.cranaya.biblioteca.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cranaya.biblioteca.R;
import com.cranaya.biblioteca.databinding.ItemBookBinding;
import com.cranaya.biblioteca.listener.BookListener;
import com.cranaya.biblioteca.model.BookModel;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<BookModel> bookModels;
    private LayoutInflater layoutInflater;
    private BookListener bookListener;
    public BookAdapter(List<BookModel> bookModels, BookListener bookListener) {
        this.bookModels = bookModels;
        this.bookListener = bookListener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemBookBinding bookBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_book, parent, false
        );
        return new BookViewHolder(bookBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bindBook(bookModels.get(position));
    }

    @Override
    public int getItemCount() {
        return bookModels.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder{

        private ItemBookBinding itemBookBinding;

        public BookViewHolder(ItemBookBinding itemBookBinding){
            super(itemBookBinding.getRoot());
            this.itemBookBinding = itemBookBinding;
        }

        public void bindBook(BookModel bookModel){

            Glide.with(itemBookBinding.imgBook.getContext())
                    .load(bookModel.getImage())
                    .error(itemBookBinding.imgBook.getContext().getDrawable(R.drawable.ic_round_business_center_24))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            itemBookBinding.progress.stopShimmer();
                            itemBookBinding.progress.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            itemBookBinding.progress.stopShimmer();
                            itemBookBinding.progress.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(itemBookBinding.imgBook);

            itemBookBinding.setBook(bookModel);
            itemBookBinding.executePendingBindings();
            itemBookBinding.getRoot().setOnClickListener(v -> bookListener.onBookClicked(bookModel));
        }
    }
}
