package xyz.imxqd.day_15.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import xyz.imxqd.day_15.dao.NoteDB;
import xyz.imxqd.day_15.dao.model.Note;
import xyz.imxqd.day_15.utils.Constants;
import xyz.imxqd.day_15.R;

import static android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY;

public class AddAndViewActivity extends Activity {
    private EditText etContent;
    private boolean isAddAction = false;
    private boolean isUpdating = false;
    private Note note;

    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etContent = (EditText) findViewById(R.id.add_and_view_content);

        if (Constants.ACTION_ADD.equals(getIntent().getAction())) {
            isAddAction = true;
            etContent.requestFocus();
            etContent.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showIME();
                }
            }, 200);
        } else if (Constants.ACTION_VIEW.equals(getIntent().getAction())) {
            isAddAction = false;
            etContent.clearFocus();
            note = getIntent().getParcelableExtra(Constants.ARG_NOTE);
            etContent.setText(note.getContent());
        }
        etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isUpdating = true;
                    initMenu();
                } else {
                    isUpdating = false;
                    initMenu();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        initMenu();
        return true;
    }

    private void initMenu() {
        mMenu.clear();
        if (isAddAction) {
            getMenuInflater().inflate(R.menu.add, mMenu);
        } else if (isUpdating){
            getMenuInflater().inflate(R.menu.add, mMenu);
        } else {
            getMenuInflater().inflate(R.menu.view, mMenu);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showIME() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etContent, 0);
    }

    private void hideIME() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                if (isAddAction) {
                    String content = etContent.getText().toString();
                    if (TextUtils.isEmpty(content.trim())) {
                        finish();
                        return true;
                    }
                    Note note = new Note();
                    note.setTime(System.currentTimeMillis());
                    note.setContent(content);
                    NoteDB.getInstance(this).addNote(note);
                    finish();
                } else {
                    note.setContent(etContent.getText().toString());
                    NoteDB.getInstance(this).updateNote(note);
                    etContent.clearFocus();
                    hideIME();
                }

                break;
            case R.id.action_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, etContent.getText().toString());
                startActivity(intent);
                break;
            case R.id.action_del:
                NoteDB.getInstance(this).delNote(note.getId());
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
