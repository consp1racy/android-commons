package net.xpece.android.widget;

import android.annotation.TargetApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import net.xpece.android.util.Lists;

import java.util.ArrayList;

/**
 * Created by Eugen on 24. 3. 2015.
 *
 * @since API 8
 */
@TargetApi(8)
@Deprecated
public class HeaderExpandableListAdapter extends ExpandableListAdapterWrapper {

    private ArrayList<FixedViewInfo> mHeaderViewInfos = Lists.newArrayList();
    private ArrayList<FixedViewInfo> mFooterViewInfos = Lists.newArrayList();

    public HeaderExpandableListAdapter(BaseExpandableListAdapter adapter) {
        super(adapter);
    }

    public void addHeaderView(View v, Object data, boolean isSelectable) {
        FixedViewInfo info = new FixedViewInfo();
        info.view = v;
        info.data = data;
        info.isSelectable = isSelectable;
        mHeaderViewInfos.add(info);

        notifyDataSetChanged();
    }

    public void addFooterView(View v, Object data, boolean isSelectable) {
        FixedViewInfo info = new FixedViewInfo();
        info.view = v;
        info.data = data;
        info.isSelectable = isSelectable;
        mFooterViewInfos.add(info);

        notifyDataSetChanged();
    }

    public void removeHeaderView(View v) {
        for (int i = 0; i < mHeaderViewInfos.size(); i++) {
            FixedViewInfo info = mHeaderViewInfos.get(i);
            if (info.view == v) {
                mHeaderViewInfos.remove(i);

                notifyDataSetChanged();
            }
        }
    }

    public void removeFooterView(View v) {
        for (int i = 0; i < mFooterViewInfos.size(); i++) {
            FixedViewInfo info = mFooterViewInfos.get(i);
            if (info.view == v) {
                mFooterViewInfos.remove(i);

                notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getGroupCount() {
        return super.getGroupCount() + mHeaderViewInfos.size() + mFooterViewInfos.size();
    }

    @Override
    public int getGroupTypeCount() {
        return super.getGroupTypeCount() + mHeaderViewInfos.size() + mFooterViewInfos.size();
    }

    @Override
    public int getGroupType(int groupPosition) {
        int headers = mHeaderViewInfos.size();
        int groups = super.getGroupCount();
        int groupTypes = super.getGroupTypeCount();

        if (groupPosition < headers) {
            return groupTypes + groupPosition;
        } else if (groupPosition < headers + groups) {
            return super.getGroupType(groupPosition - headers);
        } else {
            return groupTypes + headers + (groupPosition - groups - headers);
        }
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        int headers = mHeaderViewInfos.size();
        int groups = super.getGroupCount();

        if (groupPosition < headers) {
            return mHeaderViewInfos.get(groupPosition).view;
        } else if (groupPosition < headers + groups) {
            groupPosition -= headers;
            return super.getGroupView(groupPosition, isExpanded, convertView, parent);
        } else {
            groupPosition = groupPosition - headers - groups;
            return mFooterViewInfos.get(groupPosition).view;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        int headers = mHeaderViewInfos.size();
        int groups = super.getGroupCount();

        if (groupPosition < headers) {
            return mHeaderViewInfos.get(groupPosition).data;
        } else if (groupPosition < headers + groups) {
            groupPosition -= headers;
            return super.getGroup(groupPosition);
        } else {
            groupPosition = groupPosition - headers - groups;
            return mFooterViewInfos.get(groupPosition).data;
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        int headers = mHeaderViewInfos.size();
        int groups = super.getGroupCount();

        if (groupPosition < headers) {
            return 0;
        } else if (groupPosition < headers + groups) {
            groupPosition -= headers;
            return super.getGroupId(groupPosition);
        } else {
            groupPosition = groupPosition - headers - groups;
            return 0;
        }
    }

    /**
     * A class that represents a fixed view in a list, for example a header at the top
     * or a footer at the bottom.
     */
    public class FixedViewInfo {
        /** The view to add to the list */
        public View view;
        /** The data backing the view. This is returned from {@link android.widget.ListAdapter#getItem(int)}. */
        public Object data;
        /** <code>true</code> if the fixed view should be selectable in the list */
        public boolean isSelectable;
    }

}
