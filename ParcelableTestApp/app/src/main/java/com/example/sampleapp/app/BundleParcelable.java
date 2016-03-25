package com.example.sampleapp.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class BundleParcelable implements Parcelable {
    private final Bundle bundle;

    private BundleParcelable(Bundle bundle) {
        this.bundle = bundle;
    }

    public static BundleParcelable create() {
        Bundle bundle = new Bundle();
        bundle.putString("sample", "string");
        return new BundleParcelable(bundle);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BundleParcelable that = (BundleParcelable) o;

        return bundle != null && bundle.keySet().equals(((BundleParcelable) o).bundle.keySet());

    }

    @Override
    public int hashCode() {
        return bundle != null ? bundle.hashCode() : 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.bundle);
    }

    protected BundleParcelable(Parcel in) {
        this.bundle = in.readBundle();
    }

    public static final Creator<BundleParcelable> CREATOR = new Creator<BundleParcelable>() {
        @Override
        public BundleParcelable createFromParcel(Parcel source) {
            return new BundleParcelable(source);
        }

        @Override
        public BundleParcelable[] newArray(int size) {
            return new BundleParcelable[size];
        }
    };
}
