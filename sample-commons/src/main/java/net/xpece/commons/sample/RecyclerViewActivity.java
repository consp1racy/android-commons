package net.xpece.commons.sample;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import net.xpece.android.widget.RecyclerViewAdapterEx;
import net.xpece.commons.android.sample.R;
import net.xpece.commons.sample.randname.RandomNameGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugen on 23.10.2016.
 */

public class RecyclerViewActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView list;

    private RecyclerViewAdapter adapter;

    private RandomNameGenerator newGen;

    private int dataId = 0;

    private final List<SomeEntity> defData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final RandomNameGenerator defGen = new RandomNameGenerator(this, 0);
        newGen = new RandomNameGenerator(this);

        final List<String> defNames = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String val = defGen.next();
            defNames.add(val);
        }
        for (String name : defNames) {
            final SomeEntity se = new SomeEntity();
            se.id = dataId++;
            se.name = name;
            defData.add(se);
        }

        adapter = new RecyclerViewAdapter();

        list = new RecyclerView(this);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        setContentView(list, new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.rva, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.clear: {
                adapter.data.clear();
                adapter.showMessage(new RecyclerViewAdapterEx.MessageModel("Empty."));
                return true;
            }
            case R.id.progress: {
                adapter.showProgress();
                return true;
            }
            case R.id.error: {
                adapter.showError(new RecyclerViewAdapterEx.ErrorModel("Error.", this));
                return true;
            }
            case R.id.set: {
                adapter.hideAny();
                adapter.data.set(defData);
                return true;
            }
            case R.id.add: {
                onClick(item.getActionView());
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        adapter.showProgress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    return;
                }

                if (!isDestroyed()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.hideAny();
                            adapter.data.addAll(newData());
                        }
                    });
                }
            }
        }).start();
    }

    List<SomeEntity> newData() {
        final List<SomeEntity> newData = new ArrayList<>();
        final List<String> newNames = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String val = newGen.next();
            newNames.add(val);
        }
        for (String name : newNames) {
            final SomeEntity se = new SomeEntity();
            se.id = dataId++;
            se.name = name;
            newData.add(se);
        }
        return newData;
    }
}
