package com.example.sampleapp.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class SerializableParcelable implements Parcelable {
    private final SerializableData serializableData;
    private final SerializableData serializableDataNullable;

    private SerializableParcelable(SerializableData serializableData, SerializableData serializableDataNullable) {
        this.serializableData = serializableData;
        this.serializableDataNullable = serializableDataNullable;
    }

    public static SerializableParcelable create() {
        return new SerializableParcelable(new SerializableData(10), null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SerializableParcelable that = (SerializableParcelable) o;

        if (serializableData != null ? !serializableData.equals(that.serializableData) : that.serializableData != null)
            return false;
        return serializableDataNullable != null ? serializableDataNullable.equals(that.serializableDataNullable) : that.serializableDataNullable == null;

    }

    @Override
    public int hashCode() {
        int result = serializableData != null ? serializableData.hashCode() : 0;
        result = 31 * result + (serializableDataNullable != null ? serializableDataNullable.hashCode() : 0);
        return result;
    }

    public static class SerializableData implements Serializable {
        private final int data;

        public SerializableData(int data) {
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SerializableData that = (SerializableData) o;

            return data == that.data;

        }

        @Override
        public int hashCode() {
            return data;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.serializableData);
        dest.writeSerializable(this.serializableDataNullable);
    }

    protected SerializableParcelable(Parcel in) {
        this.serializableData = (SerializableData) in.readSerializable();
        this.serializableDataNullable = (SerializableData) in.readSerializable();
    }

    public static final Creator<SerializableParcelable> CREATOR = new Creator<SerializableParcelable>() {
        @Override
        public SerializableParcelable createFromParcel(Parcel source) {
            return new SerializableParcelable(source);
        }

        @Override
        public SerializableParcelable[] newArray(int size) {
            return new SerializableParcelable[size];
        }
    };
}
