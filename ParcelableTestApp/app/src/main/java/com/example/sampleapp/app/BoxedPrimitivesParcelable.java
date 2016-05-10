package com.example.sampleapp.app;

import android.os.Parcel;
import android.os.Parcelable;

public class BoxedPrimitivesParcelable implements Parcelable {
    private final Integer a;
    private final Double b;
    private final String c;
    private final Short d;
    private final Float e;
    private final Boolean f;
    private final Byte g;

    private BoxedPrimitivesParcelable(Integer a, Double b, String c, Short d, Float e, Boolean f, Byte g) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
    }

    public static BoxedPrimitivesParcelable create() {
        return new BoxedPrimitivesParcelable(0, 1.0, "2", (short) 3, 6f, true, (byte) 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoxedPrimitivesParcelable that = (BoxedPrimitivesParcelable) o;

        if (a != null ? !a.equals(that.a) : that.a != null) return false;
        if (b != null ? !b.equals(that.b) : that.b != null) return false;
        if (c != null ? !c.equals(that.c) : that.c != null) return false;
        if (d != null ? !d.equals(that.d) : that.d != null) return false;
        if (e != null ? !e.equals(that.e) : that.e != null) return false;
        if (f != null ? !f.equals(that.f) : that.f != null) return false;
        return g != null ? g.equals(that.g) : that.g == null;

    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        result = 31 * result + (c != null ? c.hashCode() : 0);
        result = 31 * result + (d != null ? d.hashCode() : 0);
        result = 31 * result + (e != null ? e.hashCode() : 0);
        result = 31 * result + (f != null ? f.hashCode() : 0);
        result = 31 * result + (g != null ? g.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.a);
        dest.writeValue(this.b);
        dest.writeString(this.c);
        dest.writeValue(this.d);
        dest.writeValue(this.e);
        dest.writeValue(this.f);
        dest.writeValue(this.g);
    }

    protected BoxedPrimitivesParcelable(Parcel in) {
        this.a = (Integer) in.readValue(Integer.class.getClassLoader());
        this.b = (Double) in.readValue(Double.class.getClassLoader());
        this.c = in.readString();
        this.d = (Short) in.readValue(Short.class.getClassLoader());
        this.e = (Float) in.readValue(Float.class.getClassLoader());
        this.f = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.g = (Byte) in.readValue(Byte.class.getClassLoader());
    }

    public static final Creator<BoxedPrimitivesParcelable> CREATOR = new Creator<BoxedPrimitivesParcelable>() {
        @Override
        public BoxedPrimitivesParcelable createFromParcel(Parcel source) {
            return new BoxedPrimitivesParcelable(source);
        }

        @Override
        public BoxedPrimitivesParcelable[] newArray(int size) {
            return new BoxedPrimitivesParcelable[size];
        }
    };
}
