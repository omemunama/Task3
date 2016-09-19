package nursulaeman.task3;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_reg = (TextView) findViewById(R.id.tv_reg);
        tv_reg.setPaintFlags(tv_reg.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }

    public void register (View v)
    {
        Intent i=new Intent();
        i.setClass(this,RegisterActivity.class);
        startActivity(i);
    }
}
