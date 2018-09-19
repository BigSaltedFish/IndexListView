package io.ztc.indexlistview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 点击响应管理Holder
 */
public class IndexBaseRecHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
    protected Context mContext;
    protected IndexItemClickListener indexItemClickListener;
    protected IndexItemLongClickListener indexItemLongClickListener;
    protected IndexBaseHolder indexBaseHolder;
    protected RecyclerView mRecyclerView;
    protected IndexBaseAdapter mRecyclerViewAdapter;

    public IndexBaseRecHolder(IndexBaseAdapter recyclerViewAdapter, RecyclerView recyclerView, View itemView, final IndexItemClickListener indexItemClickListener, IndexItemLongClickListener indexItemLongClickListener) {
        super(itemView);
        mRecyclerViewAdapter = recyclerViewAdapter;
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        this.indexItemClickListener = indexItemClickListener;
        this.indexItemLongClickListener = indexItemLongClickListener;
        itemView.setOnClickListener(new IndexNoDoubleListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (v.getId() == IndexBaseRecHolder.this.itemView.getId() && null != indexItemClickListener) {
                    indexItemClickListener.onItemClick(mRecyclerView, v, getAdapterPositionWrapper());
                }
            }
        });
        itemView.setOnLongClickListener(this);

        indexBaseHolder = new IndexBaseHolder(mRecyclerView, this);
    }

    public IndexBaseHolder getViewHolderHelper() {
        return indexBaseHolder;
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == this.itemView.getId() && null != indexItemLongClickListener) {
            return indexItemLongClickListener.onItemLongClick(mRecyclerView, v, getAdapterPositionWrapper());
        }
        return false;
    }

    public int getAdapterPositionWrapper() {
        if (mRecyclerViewAdapter.getHeadersCount() > 0) {
            return getAdapterPosition() - mRecyclerViewAdapter.getHeadersCount();
        } else {
            return getAdapterPosition();
        }
    }

    public interface IndexItemClickListener {
        void onItemClick(ViewGroup parent, View itemView, int position);
    }

    public interface IndexItemLongClickListener {
        boolean onItemLongClick(ViewGroup parent, View itemView, int position);
    }
}
