package xyz.imxqd.day_13;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 内部私有文件读写示例
 */
public class FileActivity extends Activity implements View.OnClickListener {

    private EditText et;
    private Button btnRead;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        et = (EditText) findViewById(R.id.file_et);
        btnRead = (Button) findViewById(R.id.btn_read);
        btnSave = (Button) findViewById(R.id.btn_save);

        btnRead.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        File file = new File(getFilesDir(), "test.txt");
        try {


            switch (v.getId()) {
                case R.id.btn_read:
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String tmp = null;
                    StringBuilder str = new StringBuilder();
                    while ((tmp = reader.readLine()) != null) {
                        str.append(tmp);
                    }
                    et.setText(str);
                    reader.close();
                    break;
                case R.id.btn_save:
                    PrintWriter writer = new PrintWriter(file);
                    writer.println(et.getText().toString());
                    writer.close();
                    break;
                default:
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
