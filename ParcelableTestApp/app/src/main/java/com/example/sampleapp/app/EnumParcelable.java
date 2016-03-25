package com.example.sampleapp.app;

import android.os.Parcel;
import android.os.Parcelable;

public class EnumParcelable implements Parcelable {
    private final SampleEnum valueX;
    private final SampleEnum valueY;
    private final SampleEnum nullValue;

    public EnumParcelable(SampleEnum valueX, SampleEnum valueY, SampleEnum nullValue) {
        this.valueX = valueX;
        this.valueY = valueY;
        this.nullValue = nullValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnumParcelable that = (EnumParcelable) o;

        if (valueX != that.valueX) return false;
        if (valueY != that.valueY) return false;
        return nullValue == that.nullValue;

    }

    @Override
    public int hashCode() {
        int result = valueX != null ? valueX.hashCode() : 0;
        result = 31 * result + (valueY != null ? valueY.hashCode() : 0);
        result = 31 * result + (nullValue != null ? nullValue.hashCode() : 0);
        return result;
    }

    public static EnumParcelable create() {
        return new EnumParcelable(SampleEnum.ABC, SampleEnum.DEF, null);
    }

    public enum SampleEnum {
        ABC,
        DEF
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.valueX == null ? -1 : this.valueX.ordinal());
        dest.writeInt(this.valueY == null ? -1 : this.valueY.ordinal());
        dest.writeInt(this.nullValue == null ? -1 : this.nullValue.ordinal());
    }

    protected EnumParcelable(Parcel in) {
        int tmpValueX = in.readInt();
        this.valueX = tmpValueX == -1 ? null : SampleEnum.values()[tmpValueX];
        int tmpValueY = in.readInt();
        this.valueY = tmpValueY == -1 ? null : SampleEnum.values()[tmpValueY];
        int tmpNullValue = in.readInt();
        this.nullValue = tmpNullValue == -1 ? null : SampleEnum.values()[tmpNullValue];
    }

    public static final Creator<EnumParcelable> CREATOR = new Creator<EnumParcelable>() {
        @Override
        public EnumParcelable createFromParcel(Parcel source) {
            return new EnumParcelable(source);
        }

        @Override
        public EnumParcelable[] newArray(int size) {
            return new EnumParcelable[size];
        }
    };
}
