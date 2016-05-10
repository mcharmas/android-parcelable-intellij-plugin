package com.example.sampleapp.app;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class MapParcelable implements Parcelable {
    private final Map<String, String> sampleMap;
    private final Map<Long, Bitmap> mapWithParcelableValues;

    private MapParcelable(Map<String, String> sampleMap, Map<Long, Bitmap> mapWithParcelableValues) {
        this.sampleMap = sampleMap;
        this.mapWithParcelableValues = mapWithParcelableValues;
    }

    public static MapParcelable create() {
        HashMap<String, String> stringMap = new HashMap<String, String>();
        stringMap.put("a", "b");

        HashMap<Long, Bitmap> bitmapMap = new HashMap<Long, Bitmap>();
        bitmapMap.put(1L, Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888));

        return new MapParcelable(stringMap, bitmapMap);
    }

    public Map<String, String> getSampleMap() {
        return sampleMap;
    }

    public Map<Long, Bitmap> getMapWithParcelableValues() {
        return mapWithParcelableValues;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.sampleMap.size());
        for (Map.Entry<String, String> entry : this.sampleMap.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.mapWithParcelableValues.size());
        for (Map.Entry<Long, Bitmap> entry : this.mapWithParcelableValues.entrySet()) {
            dest.writeValue(entry.getKey());
            dest.writeParcelable(entry.getValue(), flags);
        }
    }

    protected MapParcelable(Parcel in) {
        int sampleMapSize = in.readInt();
        this.sampleMap = new HashMap<String, String>(sampleMapSize);
        for (int i = 0; i < sampleMapSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.sampleMap.put(key, value);
        }
        int mapWithParcelableValuesSize = in.readInt();
        this.mapWithParcelableValues = new HashMap<Long, Bitmap>(mapWithParcelableValuesSize);
        for (int i = 0; i < mapWithParcelableValuesSize; i++) {
            Long key = (Long) in.readValue(Long.class.getClassLoader());
            Bitmap value = in.readParcelable(Bitmap.class.getClassLoader());
            this.mapWithParcelableValues.put(key, value);
        }
    }

    public static final Creator<MapParcelable> CREATOR = new Creator<MapParcelable>() {
        @Override
        public MapParcelable createFromParcel(Parcel source) {
            return new MapParcelable(source);
        }

        @Override
        public MapParcelable[] newArray(int size) {
            return new MapParcelable[size];
        }
    };
}
