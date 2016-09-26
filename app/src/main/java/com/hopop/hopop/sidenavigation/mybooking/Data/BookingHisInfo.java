package com.hopop.hopop.sidenavigation.mybooking.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hopop.hopop.database.BookingHistory;

import java.util.ArrayList;
import java.util.List;

public class BookingHisInfo implements Parcelable {

    @SerializedName("booking_history")
    private List<BookingHistory> bookingHistory = new ArrayList<BookingHistory>();

    public List<BookingHistory> getBookingHistory() {
        return bookingHistory;
    }

    public void setBookingHistory(List<BookingHistory> bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.bookingHistory);
    }

    public BookingHisInfo() {
    }

    protected BookingHisInfo(Parcel in) {
        this.bookingHistory = in.createTypedArrayList(BookingHistory.CREATOR);
    }

    public static final Creator<BookingHisInfo> CREATOR = new Creator<BookingHisInfo>() {
        @Override
        public BookingHisInfo createFromParcel(Parcel source) {
            return new BookingHisInfo(source);
        }

        @Override
        public BookingHisInfo[] newArray(int size) {
            return new BookingHisInfo[size];
        }
    };
}
