package net.xpece.android.extra.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textTest = (TextView) findViewById(R.id.textTest);
//        XpTextViewCompat.setCompoundDrawableTintList(textTest, null);

        findViewById(R.id.textCheckable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (v instanceof Checkable) {
                    ((Checkable) v).toggle();
                }
            }
        });
    }
}
