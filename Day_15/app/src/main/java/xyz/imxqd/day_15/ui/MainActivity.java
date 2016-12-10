package xyz.imxqd.day_15.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

import xyz.imxqd.day_15.dao.NoteDB;
import xyz.imxqd.day_15.dao.model.Note;
import xyz.imxqd.day_15.utils.Constants;
import xyz.imxqd.day_15.R;

public class MainActivity extends Activity implements NoteListAdapter.OnItemEventListener {

    private RecyclerView mRecyclerView;
    private NoteListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new NoteListAdapter(this);
        mAdapter.setItemListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.note_list);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.refresh();
        initEmptyView();
    }

    private void initEmptyView() {
        if (mAdapter.getItemCount() == 0) {
            findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.empty_view).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(this, AddAndViewActivity.class);
            intent.setAction(Constants.ACTION_ADD);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onItemClick(Note note, NoteListAdapter.NoteItemViewHolder holder) {
        Intent intent = new Intent(this, AddAndViewActivity.class);
        intent.setAction(Constants.ACTION_VIEW);
        intent.putExtra(Constants.ARG_NOTE, note);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(Note note, NoteListAdapter.NoteItemViewHolder holder) {
        note.setSelected(true);
        mAdapter.notifyItemChanged(holder.getAdapterPosition());
        startActionMode(new ActionMode.Callback() {
            boolean isSelectedAll = false;
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.setTitle(R.string.action_del);
                getMenuInflater().inflate(R.menu.menu_action_mode, menu);
                mAdapter.enableActionMode(true);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.action_del) {
                    ArrayList<Integer> list = mAdapter.getSelectedId();
                    for (int i : list) {
                        NoteDB.getInstance(MainActivity.this)
                                .delNote(i);
                    }
                    mode.finish();
                } else if (item.getItemId() == R.id.action_all) {
                    if (isSelectedAll) {
                        isSelectedAll = false;
                    } else {
                        isSelectedAll = true;
                    }
                    mAdapter.selectAll(isSelectedAll);
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mAdapter.enableActionMode(false);
                initEmptyView();
            }
        });
        return true;
    }
}
