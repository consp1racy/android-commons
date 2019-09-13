package net.xpece.commons.sample;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedListAdapterCallback;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.xpece.android.util.SortedListEx;
import net.xpece.android.widget.RecyclerViewAdapterEx;

import org.jetbrains.annotations.NotNull;


public class RecyclerViewAdapter extends RecyclerViewAdapterEx<RecyclerViewAdapter.SomeViewHolder> {

    final SortedListEx<SomeEntity> data = SortedListEx.newInstance(SomeEntity.class, new SortedListAdapterCallback<SomeEntity>(this) {
        @Override
        public int compare(SomeEntity o1, SomeEntity o2) {
            return o1.compareTo(o2);
        }

        @Override
        public boolean areContentsTheSame(SomeEntity oldItem, SomeEntity newItem) {
            return oldItem.isContentSame(newItem);
        }

        @Override
        public boolean areItemsTheSame(SomeEntity item1, SomeEntity item2) {
            return item1.isSame(item2);
        }
    });

    @Override
    public int getContentItemCount() {
        return data.size();
    }

    @NotNull
    @Override
    protected SomeViewHolder onCreateContentItemViewHolder(@NotNull ViewGroup parent, int contentViewType) {
        final Context context = parent.getContext();
        final TextView text = new AppCompatTextView(context);
        return new SomeViewHolder(text);
    }

    @Override
    protected void onBindContentItemViewHolder(@NotNull SomeViewHolder contentViewHolder, int position) {
        final SomeEntity se = data.get(position);
        final TextView text = contentViewHolder.getTextView();
        text.setText(se.name);
    }

    public static class SomeViewHolder extends RecyclerView.ViewHolder {
        public SomeViewHolder(View itemView) {
            super(itemView);
        }

        public TextView getTextView() {
            return (TextView) itemView;
        }
    }
}
