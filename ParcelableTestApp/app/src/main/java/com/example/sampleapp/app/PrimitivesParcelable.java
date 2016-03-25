package com.example.sampleapp.app;

import android.os.Parcel;
import android.os.Parcelable;

public class PrimitivesParcelable implements Parcelable {
    private final int a;
    private final double b;
    private final String c;
    private final short d;
    private final float e;
    private final boolean f;
    private final byte g;

    private PrimitivesParcelable(int a, double b, String c, short d, float e, boolean f, byte g) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
    }

    public static PrimitivesParcelable create() {
        return new PrimitivesParcelable(0, 1.0, "2", (short) 3, 6f, true, (byte) 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrimitivesParcelable that = (PrimitivesParcelable) o;

        if (a != that.a) return false;
        if (Double.compare(that.b, b) != 0) return false;
        if (d != that.d) return false;
        if (Float.compare(that.e, e) != 0) return false;
        if (f != that.f) return false;
        if (g != that.g) return false;
        return c != null ? c.equals(that.c) : that.c == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = a;
        temp = Double.doubleToLongBits(b);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (c != null ? c.hashCode() : 0);
        result = 31 * result + (int) d;
        result = 31 * result + (e != +0.0f ? Float.floatToIntBits(e) : 0);
        result = 31 * result + (f ? 1 : 0);
        result = 31 * result + (int) g;
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.a);
        dest.writeDouble(this.b);
        dest.writeString(this.c);
        dest.writeInt(this.d);
        dest.writeFloat(this.e);
        dest.writeByte(this.f ? (byte) 1 : (byte) 0);
        dest.writeByte(this.g);
    }

    protected PrimitivesParcelable(Parcel in) {
        this.a = in.readInt();
        this.b = in.readDouble();
        this.c = in.readString();
        this.d = (short) in.readInt();
        this.e = in.readFloat();
        this.f = in.readByte() != 0;
        this.g = in.readByte();
    }

    public static final Creator<PrimitivesParcelable> CREATOR = new Creator<PrimitivesParcelable>() {
        @Override
        public PrimitivesParcelable createFromParcel(Parcel source) {
            return new PrimitivesParcelable(source);
        }

        @Override
        public PrimitivesParcelable[] newArray(int size) {
            return new PrimitivesParcelable[size];
        }
    };
}
