package nursulaeman.task3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class RegisterActivity extends AppCompatActivity {

    AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);
    Button btn_register;
    TextView tv_respond, tv_result_api;
    EditText email, pass1, pass2;
    int used;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAwesomeValidation.addValidation(RegisterActivity.this, R.id.et_reg_1, Patterns.EMAIL_ADDRESS, R.string.err_email);
        mAwesomeValidation.addValidation(RegisterActivity.this, R.id.et_reg_4, "[a-zA-Z\\s]+", R.string.err_name);
        email = (EditText) findViewById(R.id.et_reg_1);
        pass1 = (EditText) findViewById(R.id.et_reg_2);
        pass2 = (EditText) findViewById(R.id.et_reg_3);
        btn_register = (Button) findViewById(R.id.btn_reg);
        tv_respond = (TextView) findViewById(R.id.tv_respond);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAwesomeValidation.validate()) {
                    email.requestFocus();
                }
                else if (!validatePass1(pass1.getText().toString())) {
                    pass1.setError("Invalid Password");
                    pass1.requestFocus();
                } else if (!pass2.getText().toString().equals(pass1.getText().toString())) {
                    pass2.setError("Invalid Password Confirmation");
                    pass2.requestFocus();
                } else {
                    Toast.makeText(RegisterActivity.this, "Input Success", Toast.LENGTH_LONG).show();
                    getApi();
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

    private void getApi() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-6d28c-task32.apiary-mock.com/users/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UserApi user_api = retrofit.create(UserApi.class);

        // // implement interface for get all user
        Call<Users> call = user_api.getUsers();
        call.enqueue(new Callback<Users>() {

            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                int status = response.code();
                tv_respond.setText(String.valueOf(status));
                //this extract data from retrofit with for() loop
                /*for(Users.UserItem user : response.body().getUsers()) {
                    tv_result_api.append(
                            "Id = " + String.valueOf(user.getId()) +
                                    System.getProperty("line.separator") +
                                    "Email = " + user.getEmail() +
                                    System.getProperty("line.separator") +
                                    "Name = " + user.getName() +
                                    System.getProperty("line.separator") +
                                    "Password = " + user.getPassword() +
                                    System.getProperty("line.separator")
                    );
                    if (user.getEmail().toString().equals(email.getText().toString())) {
                        used = used + 1;
                    }
                }
                if (used > 0){
                    Toast.makeText(RegisterActivity.this, "email already in use", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "registration is successful", Toast.LENGTH_LONG).show();
                }*/
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                tv_respond.setText(String.valueOf(t));
            }
        });
    }

}


