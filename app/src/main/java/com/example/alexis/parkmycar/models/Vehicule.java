package com.example.alexis.parkmycar.models;

import java.util.ArrayList;
import java.util.List;

public class Vehicule
{
    private static List<Vehicule> vehicules = new ArrayList<Vehicule>();
    public static List<Vehicule> getVehicules()
    {
        return Vehicule.vehicules;
    }
    public static boolean add(Vehicule v)
    {
        return Vehicule.vehicules.add(v);
    }
    public static boolean remove(Vehicule v)
    {
        if(!Vehicule.vehicules.contains(v))
            return false;

        return Vehicule.vehicules.remove(v);
    }
    public static boolean remove(int poisition)
    {
        Vehicule v = Vehicule.vehicules.get(poisition);
        return Vehicule.remove(v);
    }
    public static Vehicule get(int position)
    {
        return Vehicule.vehicules.get(position);
    }

    private String immat;
    private String model;
    private String marque;

    public Vehicule(String immat, String model, String marque)
    {
        this.immat = immat;
        this.model = model;
        this.marque = marque;
    }

    public String getImmat()
    {
        return this.immat;
    }

    public String getModel()
    {
        return this.model;
    }

    public String getMarque()
    {
        return this.marque;
    }

    public void setImmat(String immat)
    {
        this.immat = immat;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public void setMarque(String marque)
    {
        this.marque = marque;
    }
}
