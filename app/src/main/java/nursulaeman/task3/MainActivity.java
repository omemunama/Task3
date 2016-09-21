package nursulaeman.task3;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class MainActivity extends AppCompatActivity {

    AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_reg = (TextView) findViewById(R.id.tv_reg);
        tv_reg.setPaintFlags(tv_reg.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        mAwesomeValidation.addValidation(MainActivity.this, R.id.et_log_1, Patterns.EMAIL_ADDRESS, R.string.err_email);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeValidation.validate();
            }
        });

    }

    public void register (View v)
    {
        Intent i=new Intent();
        i.setClass(this,RegisterActivity.class);
        startActivity(i);
    }
}
