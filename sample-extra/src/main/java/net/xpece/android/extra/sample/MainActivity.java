package net.xpece.android.extra.sample;

import android.os.Bundle;
import android.support.design.widget.CardButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardButton cb1 = (CardButton) findViewById(R.id.cb1);
        CardButton cb1b = (CardButton) findViewById(R.id.cb1b);
        CardButton cb2 = (CardButton) findViewById(R.id.cb2);
        CardButton cb2b = (CardButton) findViewById(R.id.cb2b);
        CardButton cb3 = (CardButton) findViewById(R.id.cb3);
        CardButton cb3b = (CardButton) findViewById(R.id.cb3b);
        CardButton cb4 = (CardButton) findViewById(R.id.cb4);
        CardButton cb4b = (CardButton) findViewById(R.id.cb4b);
        CardButton cb5 = (CardButton) findViewById(R.id.cb5);
        CardButton cb5b = (CardButton) findViewById(R.id.cb5b);
        CardButton cb6 = (CardButton) findViewById(R.id.cb6);
        CardButton cb6b = (CardButton) findViewById(R.id.cb6b);

        setVisualMargin(cb1, cb1b, cb2, cb2b, cb3, cb3b, cb4, cb4b, cb5, cb5b, cb6, cb6b);

        ViewGroup main2 = (ViewGroup) findViewById(R.id.activity_main2);
        for (int i = 0, size = main2.getChildCount(); i < size; i++) {
            final View v = main2.getChildAt(i);
            v.setEnabled(false);
        }
    }

    private void setVisualMargin(CardButton... cbs) {
        for (CardButton cb : cbs) {
            CardButton.setVisualMarginOriginal(cb);
        }
    }
}
