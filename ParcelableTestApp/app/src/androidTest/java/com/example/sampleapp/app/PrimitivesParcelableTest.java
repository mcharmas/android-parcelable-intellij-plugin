package com.example.sampleapp.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.test.AndroidTestCase;

public class PrimitivesParcelableTest extends AndroidTestCase {

    public void testParcelsPrimitives() throws Exception {
        assertParcels(PrimitivesParcelable.create(), PrimitivesParcelable.CREATOR);
    }

    public void testParcelsPrimitiveArrays() throws Exception {
        assertParcels(PrimitiveArrayParcelable.create(), PrimitiveArrayParcelable.CREATOR);
    }

    public void testParcelsBoxedPrimitives() throws Exception {
        assertParcels(BoxedPrimitivesParcelable.create(), BoxedPrimitivesParcelable.CREATOR);
    }

    public void testParcelsBundleParcelable() throws Exception {
        assertParcels(BundleParcelable.create(), BundleParcelable.CREATOR);
    }

    public void testParcelsDateParcelable() throws Exception {
        assertParcels(DateParcelable.create(), DateParcelable.CREATOR);
    }

    public void testParcelsEnumParcelable() throws Exception {
        assertParcels(EnumParcelable.create(), EnumParcelable.CREATOR);
    }

    public void testParcelsSerializableParcelable() throws Exception {
        assertParcels(SerializableParcelable.create(), SerializableParcelable.CREATOR);
    }

    public void testParcelsNestedParcelables() throws Exception {
        NestedParcelable toParcel = NestedParcelable.create();
        NestedParcelable readParcelable = parcelAndRead(toParcel, NestedParcelable.CREATOR);
        assertEquals(toParcel.getBitmap().getWidth(), readParcelable.getBitmap().getWidth());
        assertEquals(toParcel.getBitmapList().size(), readParcelable.getBitmapList().size());
    }

    public void testParcelsMapParcelables() throws Exception {
        MapParcelable toParcel = MapParcelable.create();
        MapParcelable readParcelalbe = parcelAndRead(toParcel, MapParcelable.CREATOR);
        assertEquals(toParcel.getSampleMap().size(), readParcelalbe.getSampleMap().size());
        assertEquals(toParcel.getMapWithParcelableValues().size(), readParcelalbe.getMapWithParcelableValues().size());
    }

    public void testParcelsGenericListParcelables() throws Exception {
        assertParcels(GenericListParcelable.create(), GenericListParcelable.CREATOR);
    }

    public void testParcelsSparseParcelable() throws Exception {
        SparseParcelable sparseParcelable = SparseParcelable.create();
        SparseParcelable readParcelable = parcelAndRead(sparseParcelable, SparseParcelable.CREATOR);
        assertEquals(sparseParcelable.getSampleSparseArray().size(), readParcelable.getSampleSparseArray().size());
        assertEquals(sparseParcelable.getSparseBooleanArray().size(), readParcelable.getSparseBooleanArray().size());
    }

    private <T extends Parcelable> void assertParcels(T parcelable, Parcelable.Creator<T> creator) {
        Parcelable fromParcel = parcelAndRead(parcelable, creator);
        assertEquals(parcelable, fromParcel);
    }

    private <T extends Parcelable> T parcelAndRead(T parcelable, Parcelable.Creator<T> creator) {
        Parcel parcel = Parcel.obtain();
        parcelable.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        return creator.createFromParcel(parcel);
    }
}