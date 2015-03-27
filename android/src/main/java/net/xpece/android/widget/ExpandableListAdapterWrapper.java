package net.xpece.android.widget;

import android.annotation.TargetApi;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

/**
 * Created by Eugen on 24. 3. 2015.
 */
@TargetApi(8)
public class ExpandableListAdapterWrapper extends BaseExpandableListAdapter {

    private final BaseExpandableListAdapter mAdapter;

    public ExpandableListAdapterWrapper(BaseExpandableListAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mAdapter.unregisterDataSetObserver(observer);
    }

    @Override
    public int getGroupCount() {
        return mAdapter.getGroupCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mAdapter.getChildrenCount(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mAdapter.getGroup(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mAdapter.getChild(groupPosition, childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mAdapter.getGroupId(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mAdapter.getChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return mAdapter.hasStableIds();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return mAdapter.getGroupView(groupPosition, isExpanded, convertView, parent);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return mAdapter.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return mAdapter.isChildSelectable(groupPosition, childPosition);
    }

    @Override
    public void notifyDataSetInvalidated() {
        mAdapter.notifyDataSetInvalidated();
    }

    @Override
    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return mAdapter.areAllItemsEnabled();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        mAdapter.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        mAdapter.onGroupExpanded(groupPosition);
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return mAdapter.getCombinedChildId(groupId, childId);
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return mAdapter.getCombinedGroupId(groupId);
    }

    @Override
    public boolean isEmpty() {
        return mAdapter.isEmpty();
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return mAdapter.getChildType(groupPosition, childPosition);
    }

    @Override
    public int getChildTypeCount() {
        return mAdapter.getChildTypeCount();
    }

    @Override
    public int getGroupType(int groupPosition) {
        return mAdapter.getGroupType(groupPosition);
    }

    @Override
    public int getGroupTypeCount() {
        return mAdapter.getGroupTypeCount();
    }
}
