package xyz.imxqd.day_11;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imxqd on 2016/12/4.
 * RecyclerView的适配器
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<User> mList;
    private OnItemClickListener mListener;

    public MyAdapter(List<User> list) {
        mList = list;
    }

    public MyAdapter() {
        mList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void add(User user) {
        mList.add(user);
    }

    public void remove(int pos) {
        mList.remove(pos);
    }

    public void clear() {
        mList.clear();
    }

    public List<User> getCheckedUsers() {
        List<User> list = new ArrayList<>();
        for (User user : mList) {
            if (user.isChecked()) {
                list.add(user);
            }
        }
        return list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User user = mList.get(position);
        holder.name.setText(user.getName());
        holder.number.setText(user.getNumber());
        holder.check.setChecked(user.isChecked());
        // 注意：这里两个状态都需要去设置icon，
        // 不要以为默认的icon是male，然后就想当然的只在user为female时设置icon了
        // 你想一想为什么
        if (user.getSex() == User.Sex.Female) {
            holder.icon.setImageResource(R.drawable.ic_account_circle_female_36dp);
        } else {
            holder.icon.setImageResource(R.drawable.ic_account_circle_male_36dp);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        TextView number;
        CheckBox check;

        public MyViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.item_icon);
            name = (TextView) itemView.findViewById(R.id.item_name);
            number = (TextView) itemView.findViewById(R.id.item_number);
            check = (CheckBox) itemView.findViewById(R.id.item_checkbox);

            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        // getAdapterPosition()方法可以获取本ViewHolder对象在Adapter中的最新位置
                        // getLayoutPosition()方法可以获取本ViewHolder对象在Layout中的最新位置
                        // 这两个位置在一般情况下是一样的，但是在adapter数据已改变，
                        // layout还没来得及重新绘制的这段时间（时间小于16ms）里是不同的
                        // 你暂时可以不用仔细研究这个问题
                        int pos = getAdapterPosition();
                        User user = mList.get(pos);
                        mListener.onIconClick(user, pos);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int pos = getAdapterPosition();
                        User user = mList.get(pos);
                        mListener.onItemClick(user, pos);
                    }
                }
            });

            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    // 这里必须要把CheckBox的状态保存起来，
                    // 否则CheckBox在滑动后状态会错乱
                    // 你可以把这句话注释掉，让后添加item项多于屏幕，
                    // 然后滑动再滑回试一试
                    // 你自己想一想为什么会错乱
                    mList.get(getAdapterPosition()).setChecked(b);
                }
            });
        }

    }

    // 这里是一个自定义的回调接口，分别用于点击item和icon时的回调
    interface OnItemClickListener {
        void onItemClick(User user, int pos);
        void onIconClick(User user, int pos);
    }
}
