package net.xpece.commons.sample.randname;

import android.content.Context;

/**
 * Generates pseudo random unique names that combines one adjective and one noun,
 * like "friendly tiger" or "good apple".
 * <p>
 * There's about 1.5 million unique combinations, and if you keep requesting a new word
 * it will start to loop (but this code will generate all unique combinations before it starts
 * to loop.)
 *
 * @author Kohsuke Kawaguchi
 */
public class RandomNameGenerator {
    private int pos;

    private Context context;

    public RandomNameGenerator(Context context, int seed) {
        this.context = context.getApplicationContext();
        this.pos = seed;
    }

    public RandomNameGenerator(Context context) {
        this(context, (int) System.currentTimeMillis());
    }

    public synchronized String next() {
        Dictionary d = Dictionary.getInstance(context);
        pos = Math.abs(pos + d.getPrime()) % d.size();
        return d.word(pos);
    }
}
