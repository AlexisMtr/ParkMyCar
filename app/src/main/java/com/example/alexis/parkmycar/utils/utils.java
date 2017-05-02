package com.example.alexis.parkmycar.utils;

import com.example.alexis.parkmycar.models.controlleur.CtrlUsager;
import com.example.alexis.parkmycar.models.metier.Usager;


public class utils {

    private static Usager usager = CtrlUsager.getUsagers().get(0);
    public static Usager getUsager() { return usager; }
}
