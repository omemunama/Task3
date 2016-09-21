package nursulaeman.task3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        mAwesomeValidation.addValidation(RegisterActivity.this, R.id.et_reg_4, "[a-zA-Z\\s]+", R.string.err_name);
        final EditText pass1 = (EditText) findViewById(R.id.et_reg_2);
        final EditText pass2 = (EditText) findViewById(R.id.et_reg_3);
        btn_register = (Button) findViewById(R.id.btn_reg);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeValidation.validate();
                if (!validatePass1(pass1.getText().toString())) {
                    pass1.setError("Invalid Password");
                    pass1.requestFocus();
                } else if (!pass2.getText().toString().equals(pass1.getText().toString())) {
                    pass2.setError("Invalid Password Confirmation");
                    pass2.requestFocus();
                } else {
                    Toast.makeText(RegisterActivity.this, "Input Success", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected boolean validatePass1(String pass1) {
        if (pass1 != null && pass1.length() > 7) {
            return true;
        } else {
            return false;
        }
    }

}


