package com.hopop.hopop.payment.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.facebook.stetho.json.annotation.JsonProperty;
import com.hopop.hopop.database.Wallet;

import java.util.ArrayList;
import java.util.List;

public class WalletInfo implements Parcelable {

    @JsonProperty
    private List<Wallet> wallet = new ArrayList<Wallet>();

    public List<Wallet> getWallet() {
        return wallet;
    }

    public void setWallet(List<Wallet> wallet) {
        this.wallet = wallet;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.wallet);
    }

    public WalletInfo() {
    }

    protected WalletInfo(Parcel in) {
        this.wallet = in.createTypedArrayList(Wallet.CREATOR);
    }

    public static final Creator<WalletInfo> CREATOR = new Creator<WalletInfo>() {
        @Override
        public WalletInfo createFromParcel(Parcel source) {
            return new WalletInfo(source);
        }

        @Override
        public WalletInfo[] newArray(int size) {
            return new WalletInfo[size];
        }
    };
}
