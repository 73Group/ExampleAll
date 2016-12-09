package com.example.cx.recyclerview;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by cx on 16/12/2.
 */

class MyAdapter extends RecyclerView.Adapter {

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle,tvContent;
        private ImageView tvImage;

        public ViewHolder(View root) {
            super(root);
            tvTitle = (TextView) root.findViewById(R.id.tvTitle);
            tvContent = (TextView) root.findViewById(R.id.tvContent);
            tvImage = (ImageView) root.findViewById(R.id.tvImage);


        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public TextView getTvContent() {
            return tvContent;
        }

        public ImageView getTvImage() {
            return tvImage;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //通过资源文件创建，创建布局解释器，解析布局
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.cell,parent, false));

    }

    SparseArray<Drawable> mDrawableList = new SparseArray<>();
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        cellData cd = data[position];
        if (mDrawableList.get(position) == null) {
            mDrawableList.put(position, vh.itemView.getResources().getDrawable(cd.drawableId));
        }
        vh.getTvTitle().setText(cd.title);
        vh.getTvContent().setText(cd.content);
        vh.getTvImage().setImageDrawable(mDrawableList.get(position));

    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    //自定义数据对象
    private cellData[] data = new cellData[] {
            new cellData("wm1","smart", R.drawable.wm1),
            new cellData("wm2","beautiful",R.drawable.wm2),
            new cellData("wm3","vivider",R.drawable.wm3),
            new cellData("wm4","smart",R.drawable.wm4),
            new cellData("wm5","beautiful",R.drawable.wm5),
            new cellData("wm6","vivider",R.drawable.wm6),
            new cellData("wm7","smart",R.drawable.wm7),
            new cellData("wm8","beautiful",R.drawable.wm8),
            new cellData("wm9","vivider",R.drawable.wm9),
            new cellData("wm10","smart",R.drawable.wm10),
            new cellData("wm11","beautiful",R.drawable.wm11),
            new cellData("wm12","vivider",R.drawable.wm12)

    };


}
