package com.hopop.hopop.payment.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hopop.hopop.database.BookId;

import java.util.ArrayList;
import java.util.List;

public class BookIdInfo implements Parcelable {
    @SerializedName("book_id")
    private List<BookId> bookId = new ArrayList<BookId>();

    public List<BookId> getBookId() {
        return bookId;
    }

    public void setBookId(List<BookId> bookId) {
        this.bookId = bookId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.bookId);
    }

    public BookIdInfo() {
    }

    protected BookIdInfo(Parcel in) {
        this.bookId = in.createTypedArrayList(BookId.CREATOR);
    }

    public static final Creator<BookIdInfo> CREATOR = new Creator<BookIdInfo>() {
        @Override
        public BookIdInfo createFromParcel(Parcel source) {
            return new BookIdInfo(source);
        }
        @Override
        public BookIdInfo[] newArray(int size) {
            return new BookIdInfo[size];
        }
    };
}
