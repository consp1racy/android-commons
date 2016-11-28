package net.xpece.android.widget;

import java.util.List;

/**
 * @author Eugen on 15. 9. 2015.
 */
public interface Expandable<T> {
    boolean canExpand();
    List<T> getChildren();
}
