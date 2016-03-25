package com.example.sampleapp.app;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;

public class NestedParcelable implements Parcelable {
    private final Bitmap bitmap;
    private final List<Bitmap> bitmapList;

    private NestedParcelable(Bitmap bitmap, List<Bitmap> bitmapList) {
        this.bitmap = bitmap;
        this.bitmapList = bitmapList;
    }

    public static NestedParcelable create() {
        return new NestedParcelable(
                Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888),
                Arrays.asList(
                        Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888),
                        Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888)
                )
        );
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.bitmap, flags);
        dest.writeTypedList(this.bitmapList);
    }

    protected NestedParcelable(Parcel in) {
        this.bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        this.bitmapList = in.createTypedArrayList(Bitmap.CREATOR);
    }

    public static final Creator<NestedParcelable> CREATOR = new Creator<NestedParcelable>() {
        @Override
        public NestedParcelable createFromParcel(Parcel source) {
            return new NestedParcelable(source);
        }

        @Override
        public NestedParcelable[] newArray(int size) {
            return new NestedParcelable[size];
        }
    };
}
