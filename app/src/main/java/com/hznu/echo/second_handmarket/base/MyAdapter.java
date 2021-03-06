package com.hznu.echo.second_handmarket.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hznu.echo.second_handmarket.R;

import java.util.List;

/**
 * <pre>
 *     author : wengqian
 *     e-mail : wengqianjlu@qq.com
 *     time   : 2017/11/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<String> mData;

    public MyAdapter(List<String> data) {
        this.mData = data;
    }

    public void updateData(List<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_listitem, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        holder.mTv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}