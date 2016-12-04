package xyz.imxqd.day_11;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class WidgetActivity extends Activity implements SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener {

    private TextView tvProgress;
    private TextView tvState;
    private SeekBar seekBar;
    private ProgressBar progressBar;
    private ProgressBar progressBar2;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);

        tvProgress = (TextView) findViewById(R.id.seek_bar_progress);
        tvState = (TextView) findViewById(R.id.seek_bar_state);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar2 = (ProgressBar) findViewById(R.id.progress_bar2);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        seekBar.setOnSeekBarChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);

        int checkedId = radioGroup.getCheckedRadioButtonId();
        showRadio(checkedId);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        tvProgress.setText(String.valueOf(i));
        progressBar.setProgress(i);
        progressBar2.setProgress(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        tvState.setText("开始");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        tvState.setText("结束");
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        showRadio(checkedId);
    }

    private void showRadio(int checkedId) {
        if (checkedId == R.id.radio_btn1) {
            Toast.makeText(this, "hfutcx", Toast.LENGTH_SHORT).show();
        } else if (checkedId == R.id.radio_btn2) {
            Toast.makeText(this, "hfutxqd", Toast.LENGTH_SHORT).show();
        }
    }
}
