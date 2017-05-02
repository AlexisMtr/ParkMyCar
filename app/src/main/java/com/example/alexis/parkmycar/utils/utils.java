package com.example.alexis.parkmycar.utils;

import com.example.alexis.parkmycar.models.controlleur.CtrlUsager;
import com.example.alexis.parkmycar.models.metier.Usager;


public class utils {

    private static Usager usager = null;
    public static Usager getUsager() { return usager; }
    public static void setUsager(Usager u) { usager = u; }

    public static boolean finishImport = false;
}
