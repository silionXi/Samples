package com.silion.samples.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silion.samples.R;

import java.util.List;

/**
 * Created by silion on 2015/11/4.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<RecyclerData> mRecyclerDataList;
    private OnItemClickListener mOnItemClickListener;

    public RecyclerAdapter(Context context, List<RecyclerData> list) {
        mContext = context;
        mRecyclerDataList = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.recycleritem_recyclerview, viewGroup, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder viewHolder, final int i) {
        RecyclerData recyclerData = mRecyclerDataList.get(i);
        viewHolder.mIconImageView.setImageDrawable(recyclerData.getmIcon());
        viewHolder.mTitleTextView.setText(recyclerData.getmTitle());
        viewHolder.mIconBackgroundImageView.setColorFilter(mContext.getResources().getColor(R.color.i));

        ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
        layoutParams.height = 200 + recyclerData.getmOffsetHeight();
        viewHolder.itemView.setLayoutParams(layoutParams);

        if (mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(viewHolder.itemView, viewHolder.getLayoutPosition());
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(viewHolder.itemView, viewHolder.getLayoutPosition());
                    return true;
                }
            });
        }
    }

    /**
     * @return the count of the item,
     * also the size of the data list
     */
    @Override
    public int getItemCount() {
        return mRecyclerDataList.size();
    }

    interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int postition);
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        protected ImageView mIconBackgroundImageView;
        protected ImageView mIconImageView;
        protected TextView mTitleTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mIconBackgroundImageView = (ImageView) itemView.findViewById(R.id.iconBackgroundImageView);
            mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
        }
    }
}
