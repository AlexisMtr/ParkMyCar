package com.example.alexis.parkmycar.utils.persistance;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArraySet;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Created by Alexis on 15/02/2017.
 */

public class SharedPreferenceStorageImpl implements StorageService {

    public static final String PREFS_NAME = "TOKENS";
    SharedPreferences pref;

    public SharedPreferenceStorageImpl(Context c)
    {
        pref = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public List<String> store(Context c, List<String> art) {
        SharedPreferences.Editor edit = pref.edit();
        List<String> list = restore(c);
        list.addAll(art);
        Collections.sort(list, Collator.getInstance());

        Set<String> set = new ArraySet<String>();
        set.addAll(list);

        edit.putStringSet("user", set);
        edit.commit();

        return list;
    }

    @Override
    public List<String> restore(Context c) {
        Set<String> set = pref.getStringSet("user", new ArraySet<String>());
        return new ArrayList<String>(set);
    }

    @Override
    public List<String> clear(Context c) {
        SharedPreferences.Editor edit = pref.edit();
        edit.putStringSet("user", null);
        edit.commit();
        return restore(c);
    }

    @Override
    public void add(Context c, String article) {
        ArrayList<String> s = new ArrayList<>();
        s.add(article);
        store(c, s);
    }
}
