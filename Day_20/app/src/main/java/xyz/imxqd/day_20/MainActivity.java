package xyz.imxqd.day_20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            String json = "";
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("people");
            for (int i = 0 ; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                System.out.println(o.getString("email"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
