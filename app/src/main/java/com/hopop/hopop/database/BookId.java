package com.hopop.hopop.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.facebook.stetho.json.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class BookId extends SugarRecord implements Parcelable {


    @SerializedName("book_id")
    private String bookId;
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bookId);
    }

    public BookId() {
    }

    protected BookId(Parcel in) {
        this.bookId = in.readString();
    }

    public static final Creator<BookId> CREATOR = new Creator<BookId>() {
        @Override
        public BookId createFromParcel(Parcel source) {
            return new BookId(source);
        }

        @Override
        public BookId[] newArray(int size) {
            return new BookId[size];
        }
    };
}
