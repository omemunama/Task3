package nursulaeman.task3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    TextView tv_shared_preference, tv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tv_shared_preference = (TextView) findViewById(R.id.tv_shared_preference);
        tv_logout = (TextView) findViewById(R.id.tv_logout);

        //this for retrieve value from shared preferences
        SharedPreferences get_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
        tv_shared_preference.setText("Welcome "+ get_shared_preference.getString("name", ""));

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSharedPreference();
                Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void clearSharedPreference() {
        SharedPreferences set_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = set_shared_preference.edit();
        sp_editor.clear();
        sp_editor.commit();
    }

}
