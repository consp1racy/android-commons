package net.xpece.commons.sample;

import androidx.annotation.NonNull;

import net.xpece.android.util.ComparableContent;


public class SomeEntity implements Comparable<SomeEntity>, ComparableContent<SomeEntity> {
    public int id = 0;
    @NonNull public String name = "";

    @Override
    public int compareTo(@NonNull SomeEntity someEntity) {
        return name.compareTo(someEntity.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SomeEntity)) return false;

        SomeEntity that = (SomeEntity) o;

        if (id != that.id) return false;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SomeEntity{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }

    @Override
    public boolean isSame(@NonNull SomeEntity other) {
        return id == other.id;
    }

    @Override
    public boolean isContentSame(SomeEntity other) {
        return false;
    }
}
