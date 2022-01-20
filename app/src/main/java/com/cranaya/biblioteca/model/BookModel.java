package com.cranaya.biblioteca.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BookModel implements Parcelable {
    public static final String TAG = BookModel.class.getSimpleName();

    private String title;
    private String subtitle;
    private String isbn13;
    private String price;
    private String image;
    private String url;
    private String pages;
    private String year;
    private String rating;
    private String desc;
    private String pdf;
    private String authors;
    private String publisher;

    public BookModel(String title, String subtitle, String isbn13, String price, String image, String url, String pages, String year, String rating, String desc, String pdf, String authors, String publisher) {
        this.title = title;
        this.subtitle = subtitle;
        this.isbn13 = isbn13;
        this.price = price;
        this.image = image;
        this.url = url;
        this.pages = pages;
        this.year = year;
        this.rating = rating;
        this.desc = desc;
        this.pdf = pdf;
        this.authors = authors;
        this.publisher = publisher;
    }

    protected BookModel(Parcel in) {
        title = in.readString();
        subtitle = in.readString();
        isbn13 = in.readString();
        price = in.readString();
        image = in.readString();
        url = in.readString();
        pages = in.readString();
        year = in.readString();
        rating = in.readString();
        desc = in.readString();
        pdf = in.readString();
        authors = in.readString();
        publisher = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeString(isbn13);
        dest.writeString(price);
        dest.writeString(image);
        dest.writeString(url);
        dest.writeString(pages);
        dest.writeString(year);
        dest.writeString(rating);
        dest.writeString(desc);
        dest.writeString(pdf);
        dest.writeString(authors);
        dest.writeString(publisher);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookModel> CREATOR = new Creator<BookModel>() {
        @Override
        public BookModel createFromParcel(Parcel in) {
            return new BookModel(in);
        }

        @Override
        public BookModel[] newArray(int size) {
            return new BookModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
