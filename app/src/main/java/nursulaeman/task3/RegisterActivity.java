package nursulaeman.task3;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    AwesomeValidation mAwesomeValidation2 = new AwesomeValidation(BASIC);
    Button btn_register;
    TextView tv_respond;
    EditText email, name, pass1, pass2;
    String sEmail, sName, sPass, sToken;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAwesomeValidation.addValidation(RegisterActivity.this, R.id.et_reg_1, Patterns.EMAIL_ADDRESS, R.string.err_email);
        mAwesomeValidation2.addValidation(RegisterActivity.this, R.id.et_reg_4, "[a-zA-Z\\s]+", R.string.err_name);
        email = (EditText) findViewById(R.id.et_reg_1);
        name = (EditText) findViewById(R.id.et_reg_4);
        pass1 = (EditText) findViewById(R.id.et_reg_2);
        pass2 = (EditText) findViewById(R.id.et_reg_3);
        btn_register = (Button) findViewById(R.id.btn_reg);
        tv_respond = (TextView) findViewById(R.id.tv_respond);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setTitle("Register on Process");
                progressDialog.setMessage("Loading ...");
                progressDialog.setProgress(0);

                sEmail = email.getText().toString();
                sName = name.getText().toString();
                sPass = pass1.getText().toString();
                sToken = "gsadug90sajvdfgaf";
                if (!mAwesomeValidation.validate()) {
                    email.requestFocus();
                } else if (!mAwesomeValidation2.validate()) {
                    name.requestFocus();
                } else if (!validatePass1(pass1.getText().toString())) {
                    pass1.setError("Invalid Password");
                    pass1.requestFocus();
                } else if (!pass2.getText().toString().equals(pass1.getText().toString())) {
                    pass2.setError("Invalid Password Confirmation");
                    pass2.requestFocus();
                } else {
                    Toast.makeText(RegisterActivity.this, "Input Success", Toast.LENGTH_LONG).show();
                    progressDialog.show();
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
                .baseUrl("https://private-6d28c-task32.apiary-mock.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        final UserApi user_api = retrofit.create(UserApi.class);

        // implement interface for get all user
        Call<Users> call = user_api.getUsers();
        call.enqueue(new Callback<Users>() {

            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                int status = response.code();
                tv_respond.setText("");
                tv_respond.setText(String.valueOf(status));
                //this extract data from retrofit with for() loop
                for (Users.UserItem user : response.body().getUsers()) {
                    if (user.getEmail().toString().equals(email.getText().toString())) {
                        Toast.makeText(RegisterActivity.this, "email already in use", Toast.LENGTH_LONG).show();
                        break;
                    } else {
                        postApi();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                progressDialog.dismiss();
                tv_respond.setText(String.valueOf(t));
            }

        });
    }

    private void postApi() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-6d28c-task32.apiary-mock.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        final UserApi user_api = retrofit.create(UserApi.class);

        // implement interface for add user

        User user_save = new User(sEmail, sName, sPass, sToken);
        Call<User> call2 = user_api.saveUser(user_save);

        call2.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int status2 = response.code();
                progressDialog.dismiss();
                tv_respond.setText(String.valueOf(status2));
                Toast.makeText(RegisterActivity.this, "registration is successful", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<User> call2, Throwable t) {
                progressDialog.dismiss();
                tv_respond.setText(String.valueOf(t));
            }
        });
    }

}


