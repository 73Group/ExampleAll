package xyz.imxqd.day_15.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

import xyz.imxqd.day_15.R;
import xyz.imxqd.day_15.dao.NoteDB;
import xyz.imxqd.day_15.dao.model.Note;
import xyz.imxqd.day_15.utils.TimeFormat;

/**
 * Created by imxqd on 2016/12/10.
 * 主界面记事列表的适配器
 */

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteItemViewHolder> {

    private ArrayList<Note> mList;
    private Context mContext;
    private OnItemEventListener mListener;
    private boolean isActionMode = false;

    public NoteListAdapter(Context context) {
        mList = NoteDB.getInstance(context).getAllNotes();
        mContext = context;
    }

    public void setItemListener (OnItemEventListener listener) {
        mListener = listener;
    }

    public void refresh() {
        mList.clear();
        mList.addAll(NoteDB.getInstance(mContext).getAllNotes());
        notifyDataSetChanged();
    }

    public void enableActionMode(boolean enable) {
        isActionMode = enable;
        if (!isActionMode) {
            refresh();
        }
    }

    public void selectAll(boolean isSelected) {
        for (Note note : mList) {
            note.setSelected(isSelected);
        }
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getSelectedId() {
        ArrayList<Integer> list = new ArrayList<>(2);
        for (Note note : mList) {
            if (note.isSelected()) {
                list.add(note.getId());
            }
        }
        return list;
    }

    @Override
    public NoteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NoteItemViewHolder holder, int position) {
        holder.setNote(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class NoteItemViewHolder extends RecyclerView.ViewHolder {

        TextView time;
        TextView content;
        public NoteItemViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.item_time);
            content = (TextView) itemView.findViewById(R.id.item_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note note = mList.get(getAdapterPosition());
                    if (isActionMode) {
                        if (note.isSelected()) {
                            note.setSelected(false);
                        } else {
                            note.setSelected(true);
                        }
                        notifyItemChanged(getAdapterPosition());
                        return;
                    }
                    if (mListener != null) {
                        mListener.onItemClick(note, NoteItemViewHolder.this);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mListener != null) {
                        return mListener.onItemLongClick(mList.get(getAdapterPosition())
                                , NoteItemViewHolder.this);
                    } else {
                        return false;
                    }
                }
            });
        }

        public void setNote(Note note) {
            time.setText(TimeFormat.format(note.getTime()));
            content.setText(note.getContent());
            if (isActionMode) {
                itemView.setSelected(note.isSelected());
            } else {
                itemView.setSelected(false);
            }
        }
    }

    public interface OnItemEventListener {
        void onItemClick(Note note, NoteItemViewHolder holder);
        boolean onItemLongClick(Note note, NoteItemViewHolder holder);
    }
}
