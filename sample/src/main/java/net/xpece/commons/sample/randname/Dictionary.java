package net.xpece.commons.sample.randname;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Dictionary of adjectives and nouns.
 *
 * @author Kohsuke Kawaguchi
 */
public class Dictionary {
    private static Dictionary sInstance = null;

    private static final Object INSTANCE_LOCK = new Object();

    public static Dictionary getInstance(Context context) {
        if (sInstance == null) {
            synchronized (INSTANCE_LOCK) {
                if (sInstance == null) {
                    sInstance = new Dictionary(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private final List<String> nouns = new ArrayList<>();
    private final List<String> adjectives = new ArrayList<>();

    private final int prime;

    private Dictionary(Context context) {
        try {
            load(context, "a", adjectives);
            load(context, "n", nouns);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        int combo = size();

        int primeCombo = 2;
        while (primeCombo <= combo) {
            int nextPrime = primeCombo + 1;
            primeCombo *= nextPrime;
        }
        prime = primeCombo + 1;
    }

    /**
     * Total size of the combined words.
     */
    public int size() {
        return nouns.size() * adjectives.size();
    }

    /**
     * Sufficiently big prime that's bigger than {@link #size()}
     */
    public int getPrime() {
        return prime;
    }

    public String word(int i) {
        int a = i % adjectives.size();
        int n = i / adjectives.size();

        return adjectives.get(a) + "_" + nouns.get(n);
    }

    private void load(Context context, String name, List<String> col) throws IOException {
        final int id = context.getResources().getIdentifier(name, "raw", context.getPackageName());
        final InputStream is = context.getResources().openRawResource(id);
        BufferedReader r = new BufferedReader(new InputStreamReader(is, "US-ASCII"));
        try {
            String line;
            while ((line = r.readLine()) != null)
                col.add(line);
        } finally {
            r.close();
        }
    }
}
