package com.example.alexis.parkmycar.utils.persistance;

import android.content.Context;
import java.util.List;

public interface StorageService {

    public List<String> store(Context c, List<String> art);
    public List<String> restore(Context c);
    public List<String> clear(Context c);
    public void add(Context c, String article);
}
