package net.xpece.android.content;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.util.Log;

import net.xpece.android.AndroidUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by pechanecjr on 17. 9. 2014.
 */
public class IntentQueue {

    private static final String TAG = IntentQueue.class.getSimpleName();

    private static final String FILENAME = "intent.q";

    private static final IntentQueue sInstance = new IntentQueue();

    private ConcurrentLinkedQueue<Intent> mQueue = null;

    private boolean mLoaded = false;

    private Check mCheck = null;

    public static IntentQueue getInstance() {
        return sInstance;
    }

    private IntentQueue() {}

    private void addInternal(Intent intent) {
        mQueue.add(intent);
    }

    private void removeInternal(Intent intent, RemoveHandler handler) {
        for (Intent i : mQueue) {
            if (handler.shouldBeRemoved(intent, i)) {
                mQueue.remove(i);
            }
        }
    }

    private void clearInternal() {
        mQueue.clear();
    }

    @SuppressWarnings("deprecation")
    public void loadAsync(final Context context) {
        new Thread() {
            @Override
            public void run() {
                load(context);
            }
        }.start();
    }

    @Deprecated
    public synchronized void load(Context context) {
        ensureLoaded(context);

        if (mLoaded) {
            Log.d(TAG, "Loaded " + mQueue.size() + " intents.");
            if (AndroidUtils.isAnyNetworkConnected(context)) {
                doWork(context);
            } else {
                IntentQueue.ConnectivityReceiver.setEnabled(context, true);
            }
        } else {
            Log.e(TAG, "Loading intent queue failed.");
        }
    }

    public synchronized void add(Context context, Intent intent) {
        add(context, intent, RemoveHandler.EQUALS);
    }

    public synchronized void add(Context context, Intent intent, RemoveHandler handler) {
        ensureLoaded(context);
        Log.d(TAG, "Added intent " + intent + " to queue.");
        removeInternal(intent, handler);
        addInternal(intent);
        persist(context);

        IntentQueue.ConnectivityReceiver.setEnabled(context, true);
    }

    public synchronized Intent[] pollAll(Context context) {
        ensureLoaded(context);
        int size = mQueue.size();
        Intent[] intents = mQueue.toArray(new Intent[size]);
        clearInternal();
        if (size > 0) persist(context);
//    ConnectivityReceiver.setEnabled(sContext.get(), false);

        return intents;
    }

    public synchronized Intent poll(Context context) {
        ensureLoaded(context);

        Intent i = null;
        try {
            i = mQueue.poll();
            return i;
        } finally {
            if (i != null) {
                Log.d(TAG, "Polled intent " + i + " from queue.");
                persist(context);
            }
        }
    }

    public synchronized void clear(Context context) {
        ensureLoaded(context);

        int size = mQueue.size();
        if (size == 0) return;

        clearInternal();

        persist(context);
    }

    private void persist(Context context) {
        try {
            File f = new File(context.getFilesDir(), FILENAME);
            FileWriter fw = new FileWriter(f, false);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Intent i : mQueue) {
                String uri = i.toUri(0);
                bw.write(uri);
                bw.newLine();
            }
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean loadFromStorage(Context context) {
        mQueue = new ConcurrentLinkedQueue<>();

        try {
            File f = new File(context.getFilesDir(), FILENAME);
            if (!f.exists()) return true;

            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String l;
            while (true) {
                l = br.readLine();
                if (l == null || l.length() == 0) break;
                Intent i = Intent.parseUri(l, 0);
                mQueue.add(i);
            }
            br.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void ensureLoaded(Context context) {
        if (mLoaded) {
            return;
        } else {
            mLoaded = loadFromStorage(context);
        }
    }

    public synchronized int size() {
        return mQueue == null ? 0 : mQueue.size();
    }

    public synchronized void setCheck(Check check) {
        mCheck = check;
    }

    private static void doWork(Context context) {
        if (!AndroidUtils.isAnyNetworkConnected(context)) {
            return;
        }

        IntentQueue instance = getInstance();
        if (instance.mCheck != null && !instance.mCheck.shouldProcessQueue()) {
            return;
        }
        ConnectivityReceiver.setEnabled(context, false);

        int size = instance.size();
        if (size > 0) {
            Log.d(TAG, "Starting " + size + " services...");
            Intent[] intents = instance.pollAll(context);
            for (Intent i : intents) {
                context.startService(i);
            }
        }
    }

    public interface RemoveHandler {
        RemoveHandler EQUALS = new RemoveHandler() {
            @Override
            public boolean shouldBeRemoved(Intent intent, Intent subjectToRemoval) {
                return intent != null && intent.equals(subjectToRemoval);
            }
        };

        boolean shouldBeRemoved(Intent intent, Intent subjectToRemoval);
    }

    public static class ConnectivityReceiver extends BroadcastReceiver {

        public static void setEnabled(Context context, boolean enabled) {
            int flag = (enabled ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
            ComponentName component = new ComponentName(context, ConnectivityReceiver.class);

            context.getPackageManager().setComponentEnabledSetting(component, flag, PackageManager.DONT_KILL_APP);
        }

        public static boolean isEnabled(Context context) {
            ComponentName component = new ComponentName(context, ConnectivityReceiver.class);
            return context.getPackageManager().getComponentEnabledSetting(component) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) return;

            ConnectivityReceiver.setEnabled(context, false);

            doWork(context);
        }
    }

    public interface Check {
        boolean shouldProcessQueue();
    }
}
