package nursulaeman.task3;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class MainActivity extends AppCompatActivity {

    AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);
    Button btn_login;
    boolean doubleBackToExitPressedOnce = false, login;
    TextView tv_regres;
    EditText email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_reg = (TextView) findViewById(R.id.tv_reg);
        tv_reg.setPaintFlags(tv_reg.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tv_regres = (TextView) findViewById(R.id.tv_regres);
        email = (EditText) findViewById(R.id.et_log_1);
        pass = (EditText) findViewById(R.id.et_log_2);

        mAwesomeValidation.addValidation(MainActivity.this, R.id.et_log_1, Patterns.EMAIL_ADDRESS, R.string.err_email);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAwesomeValidation.validate()) {
                   email.requestFocus();
                } else {
                    getApi();
                }
            }
        });

    }

    public void register (View v)
    {
        Intent i=new Intent();
        i.setClass(this,RegisterActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
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
                tv_regres.setText(String.valueOf(status));
                //this extract data from retrofit with for() loop
                for (Users.UserItem user : response.body().getUsers()) {
                    /*tv_result_api.append(
                            "Id = " + String.valueOf(user.getId()) +
                                    System.getProperty("line.separator") +
                                    "Email = " + user.getEmail() +
                                    System.getProperty("line.separator") +
                                    "Name = " + user.getName() +
                                    System.getProperty("line.separator") +
                                    "Password = " + user.getPassword() +
                                    System.getProperty("line.separator")
                    );*/
                    login = false;
                    if (user.getEmail().toString().equals(email.getText().toString()) &&
                            user.getPassword().toString().equals(pass.getText().toString())) {
                        login = true;
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                tv_regres.setText(String.valueOf(t));
            }
        });
    }
}
