package nursulaeman.task3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class RegisterActivity extends AppCompatActivity {

    AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAwesomeValidation.addValidation(RegisterActivity.this, R.id.et_reg_1, Patterns.EMAIL_ADDRESS, R.string.err_email);
        btn_register = (Button) findViewById(R.id.btn_reg);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeValidation.validate();
            }
        });

    }
}
