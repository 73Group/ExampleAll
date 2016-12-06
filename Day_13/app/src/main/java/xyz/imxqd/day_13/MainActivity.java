package xyz.imxqd.day_13;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * SharedPreferences的使用示例
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private static final String PREF_NAME = "login_config";
    private EditText etUsername;
    private EditText etPassword;
    private CheckBox cbAutoLogin;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        cbAutoLogin = (CheckBox) findViewById(R.id.cb_auto_login);
        btnLogin = (Button) findViewById(R.id.btn_login);

        SharedPreferences pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        if (pref.contains("username")) {
            cbAutoLogin.setChecked(true);
            etUsername.setText(pref.getString("username", ""));
            etPassword.setText(pref.getString("password", ""));
        } else {
            cbAutoLogin.setChecked(false);
        }

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        boolean isChecked = cbAutoLogin.isChecked();
        SharedPreferences pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        if (isChecked) {
            pref.edit()
                    .putString("username", etUsername.getText().toString())
                    .putString("password", etPassword.getText().toString())
                    .apply();
        } else {
            pref.edit()
                    .clear()
                    .apply();
        }
        startActivity(new Intent(this, FileActivity.class));
    }
}
