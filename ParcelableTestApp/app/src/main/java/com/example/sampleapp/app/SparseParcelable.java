package com.example.sampleapp.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

public class SparseParcelable implements Parcelable {
    private final SparseArray<String> sampleSparseArray;
    private final SparseBooleanArray sparseBooleanArray;

    private SparseParcelable(SparseArray<String> sampleSparseArray, SparseBooleanArray sparseBooleanArray) {
        this.sampleSparseArray = sampleSparseArray;
        this.sparseBooleanArray = sparseBooleanArray;
    }

    public static SparseParcelable create() {
        SparseArray<String> sampleSparseArray = new SparseArray<String>(10);
        sampleSparseArray.put(10, "abcd");

        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        sparseBooleanArray.put(1, false);

        return new SparseParcelable(
                sampleSparseArray,
                sparseBooleanArray
        );
    }

    public SparseArray getSampleSparseArray() {
        return sampleSparseArray;
    }

    public SparseBooleanArray getSparseBooleanArray() {
        return sparseBooleanArray;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSparseArray((SparseArray) this.sampleSparseArray);
        dest.writeSparseBooleanArray(this.sparseBooleanArray);
    }

    protected SparseParcelable(Parcel in) {
        this.sampleSparseArray = in.readSparseArray(String.class.getClassLoader());
        this.sparseBooleanArray = in.readSparseBooleanArray();
    }

    public static final Creator<SparseParcelable> CREATOR = new Creator<SparseParcelable>() {
        @Override
        public SparseParcelable createFromParcel(Parcel source) {
            return new SparseParcelable(source);
        }

        @Override
        public SparseParcelable[] newArray(int size) {
            return new SparseParcelable[size];
        }
    };
}

