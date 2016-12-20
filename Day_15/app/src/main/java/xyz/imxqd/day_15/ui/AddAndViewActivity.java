package xyz.imxqd.day_15.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import xyz.imxqd.day_15.dao.NoteDB;
import xyz.imxqd.day_15.dao.model.Note;
import xyz.imxqd.day_15.utils.Constants;
import xyz.imxqd.day_15.R;

/**
 * 这个Activity用于显示或编辑或新增一条便签
 */
public class AddAndViewActivity extends Activity {
    private EditText etContent;

    // 是否是添加动作
    private boolean isAddAction = false;

    //是否正在编辑
    private boolean isEditing = false;

    // 当前这条便签
    private Note note;

    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        if (getActionBar() != null) {
            // setDisplayHomeAsUpEnabled的作用是在ActionBar的左边添加一个返回按钮
            // 这个按钮的id为android.R.id.home
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etContent = (EditText) findViewById(R.id.add_and_view_content);

        // 在这里，我们使用Intent中的action参数来判断意图
        if (Constants.ACTION_ADD.equals(getIntent().getAction())) {
            isAddAction = true;
            // 如果是添加动作，则编辑框请求焦点并自动打开输入法键盘
            etContent.requestFocus();
            // 打开键盘需要等到界面全部绘制完成后才能进行，所以我们延迟200ms（等待界面绘制完成）
            // PS：View拥有post和postDelayed方法，里面的Runnable是在UI线程（或称作主线程）执行的
            // 线程相关的内容我们下一次推送就会学习
            etContent.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showIME();
                }
            }, 200);
        } else if (Constants.ACTION_VIEW.equals(getIntent().getAction())) {
            isAddAction = false;
            etContent.clearFocus();
            // 如果是查看动作，则清除编辑框焦点，获取这条便签的内容并显示
            note = getIntent().getParcelableExtra(Constants.ARG_NOTE);
            etContent.setText(note.getContent());
        }
        etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isEditing = true;
                    initMenu();
                } else {
                    isEditing = false;
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

    /**
     * 根据不同的状态去改变ActionBar上的菜单项
     */
    private void initMenu() {
        mMenu.clear();
        if (isAddAction) {
            getMenuInflater().inflate(R.menu.add, mMenu);
        } else if (isEditing){
            getMenuInflater().inflate(R.menu.add, mMenu);
        } else {
            getMenuInflater().inflate(R.menu.view, mMenu);
        }
    }

    /**
     * 用来打开输入法键盘
     */
    private void showIME() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etContent, 0);
    }

    /**
     * 用来隐藏输入法键盘
     */
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
                // 这里是使用隐式Intent方式进行纯文本内容的分享
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
                // ActionBar上的返回按键
                finish();
                break;
        }
        return true;
    }
}
