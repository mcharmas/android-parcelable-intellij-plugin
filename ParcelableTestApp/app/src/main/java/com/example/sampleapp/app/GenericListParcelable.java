package com.example.sampleapp.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericListParcelable implements Parcelable {
    private final List<String> stringList;
    private final List<Integer> intList;

    private GenericListParcelable(List<String> stringList, List<Integer> intList) {
        this.stringList = stringList;
        this.intList = intList;
    }

    public static GenericListParcelable create() {
        return new GenericListParcelable(
                Arrays.asList("a", "b", "c"),
                Arrays.asList(1,2,3,4)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericListParcelable that = (GenericListParcelable) o;

        if (stringList != null ? !stringList.equals(that.stringList) : that.stringList != null) return false;
        return intList != null ? intList.equals(that.intList) : that.intList == null;

    }

    @Override
    public int hashCode() {
        int result = stringList != null ? stringList.hashCode() : 0;
        result = 31 * result + (intList != null ? intList.hashCode() : 0);
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.stringList);
        dest.writeList(this.intList);
    }

    protected GenericListParcelable(Parcel in) {
        this.stringList = in.createStringArrayList();
        this.intList = new ArrayList<Integer>();
        in.readList(this.intList, Integer.class.getClassLoader());
    }

    public static final Creator<GenericListParcelable> CREATOR = new Creator<GenericListParcelable>() {
        @Override
        public GenericListParcelable createFromParcel(Parcel source) {
            return new GenericListParcelable(source);
        }

        @Override
        public GenericListParcelable[] newArray(int size) {
            return new GenericListParcelable[size];
        }
    };
}
