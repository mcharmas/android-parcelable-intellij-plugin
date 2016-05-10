package com.example.sampleapp.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class DateParcelable implements Parcelable {
    private final Date date;

    private DateParcelable(Date date) {
        this.date = date;
    }

    public static DateParcelable create() {
        return new DateParcelable(new Date());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateParcelable that = (DateParcelable) o;

        return date != null ? date.equals(that.date) : that.date == null;

    }

    @Override
    public int hashCode() {
        return date != null ? date.hashCode() : 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
    }

    protected DateParcelable(Parcel in) {
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Creator<DateParcelable> CREATOR = new Creator<DateParcelable>() {
        @Override
        public DateParcelable createFromParcel(Parcel source) {
            return new DateParcelable(source);
        }

        @Override
        public DateParcelable[] newArray(int size) {
            return new DateParcelable[size];
        }
    };
}
