package com.example.sampleapp.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class PrimitiveArrayParcelable implements Parcelable {
    private final int[] a;
    private final double[] b;
    private final String[] c;
    private final float[] e;
    private final boolean[] f;
    private final byte[] g;

    private PrimitiveArrayParcelable(int[] a, double[] b, String[] c, float[] e, boolean[] f, byte[] g) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.e = e;
        this.f = f;
        this.g = g;
    }

    public static PrimitiveArrayParcelable create() {
        return new PrimitiveArrayParcelable(
                new int[]{1, 2, 3, 4, 5},
                new double[]{1.0, 2.0},
                new String[]{"a", "b"},
                new float[]{1f, 2f},
                new boolean[]{true, false, true},
                new byte[]{(byte) 1, (byte) 2}
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrimitiveArrayParcelable that = (PrimitiveArrayParcelable) o;

        if (!Arrays.equals(a, that.a)) return false;
        if (!Arrays.equals(b, that.b)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(c, that.c)) return false;
        if (!Arrays.equals(e, that.e)) return false;
        if (!Arrays.equals(f, that.f)) return false;
        return Arrays.equals(g, that.g);

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(a);
        result = 31 * result + Arrays.hashCode(b);
        result = 31 * result + Arrays.hashCode(c);
        result = 31 * result + Arrays.hashCode(e);
        result = 31 * result + Arrays.hashCode(f);
        result = 31 * result + Arrays.hashCode(g);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.a);
        dest.writeDoubleArray(this.b);
        dest.writeStringArray(this.c);
        dest.writeFloatArray(this.e);
        dest.writeBooleanArray(this.f);
        dest.writeByteArray(this.g);
    }

    protected PrimitiveArrayParcelable(Parcel in) {
        this.a = in.createIntArray();
        this.b = in.createDoubleArray();
        this.c = in.createStringArray();
        this.e = in.createFloatArray();
        this.f = in.createBooleanArray();
        this.g = in.createByteArray();
    }

    public static final Creator<PrimitiveArrayParcelable> CREATOR = new Creator<PrimitiveArrayParcelable>() {
        @Override
        public PrimitiveArrayParcelable createFromParcel(Parcel source) {
            return new PrimitiveArrayParcelable(source);
        }

        @Override
        public PrimitiveArrayParcelable[] newArray(int size) {
            return new PrimitiveArrayParcelable[size];
        }
    };
}
