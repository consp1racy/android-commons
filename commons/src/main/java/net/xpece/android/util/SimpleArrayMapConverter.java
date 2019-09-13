package net.xpece.android.util;

import android.os.Bundle;
import androidx.collection.SimpleArrayMap;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

import java.lang.ClassLoader;import java.lang.Override;import java.lang.String;import java.util.ArrayList;

public class SimpleArrayMapConverter<K, V> implements ParcelConverter<SimpleArrayMap<K, V>> {

    public static <K, V> void toBundle(Bundle bundle, String tag, SimpleArrayMap<K, V> map) {
        int size = map.size();
        ArrayList<K> keys = new ArrayList<>(size);
        ArrayList<V> values = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            K key = map.keyAt(i);
            V value = map.valueAt(i);
            keys.add(key);
            values.add(value);
        }

        bundle.putParcelable(tag + ".KEYS", Parcels.wrap(keys));
        bundle.putParcelable(tag + ".VALUES", Parcels.wrap(values));
    }

    public static <K, V> SimpleArrayMap<K, V> fromBundle(Bundle bundle, String tag, boolean nullIfNotFound) {
        ArrayList<K> keys = Parcels.unwrap(bundle.getParcelable(tag + ".KEYS"));
        ArrayList<V> values = Parcels.unwrap(bundle.getParcelable(tag + ".VALUES"));
        if (keys == null) {
            return nullIfNotFound ? null : new SimpleArrayMap<K, V>();
        } else {
            SimpleArrayMap<K, V> map = new SimpleArrayMap<>();
            for (int i = 0, size = keys.size(); i < size; i++) {
                K key = keys.get(i);
                V value = values.get(i);
                map.put(key, value);
            }
            return map;
        }
    }

    @Override
    public void toParcel(SimpleArrayMap<K, V> input, android.os.Parcel parcel) {
        int size = input.size();
        parcel.writeInt(size);
        for (int i = 0; i < size; i++) {
            parcel.writeParcelable(Parcels.wrap(input.keyAt(i)), 0);
            parcel.writeParcelable(Parcels.wrap(input.valueAt(i)), 0);
        }
    }

    @Override
    public SimpleArrayMap<K, V> fromParcel(android.os.Parcel parcel) {
        ClassLoader cl = getClass().getClassLoader();

        SimpleArrayMap<K, V> items = new SimpleArrayMap<>();
        int size = parcel.readInt();
        for (int i = 0; i < size; i++) {
            K key = Parcels.unwrap(parcel.readParcelable(cl));
            V value = Parcels.unwrap(parcel.readParcelable(cl));
            items.put(key, value);
        }
        return items;
    }
}
