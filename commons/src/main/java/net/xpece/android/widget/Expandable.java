package net.xpece.android.widget;

import java.util.List;

public interface Expandable<T> {
    boolean canExpand();
    List<T> getChildren();
}
