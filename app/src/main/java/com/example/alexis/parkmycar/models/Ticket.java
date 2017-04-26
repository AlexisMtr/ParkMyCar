package com.example.alexis.parkmycar.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ticket
{
    private static Ticket current = null;
    private static List<Ticket> tickets = new ArrayList<Ticket>();

    private Vehicule vehicule;
    private int duration;
    private Date date;
    private String emplacement;
    private int rappel;
    private boolean clos;

    public Ticket(Vehicule v, int duree, Date date, String emplacement, int rappel, boolean clos)
    {
        this.vehicule = v;
        this.duration = duree;
        this.date = date;
        this.emplacement = emplacement;
        this.rappel = rappel;
        this.clos = clos;
    }

    public Vehicule getVehicule()
    {
        return this.vehicule;
    }

    public Date getDate()
    {
        return this.date;
    }

    public String getEmplacement()
    {
        return this.emplacement;
    }

    public int getDuree()
    {
        return this.duration;
    }

    public int getRappel()
    {
        return this.rappel;
    }



    public static Ticket createNew(Vehicule v, int duree, Date date, String emplacement, int rappel)
    {
        return Ticket.current = new Ticket(v, duree, date, emplacement, rappel, false);
    }

    public static Ticket getCurrent()
    {
        return Ticket.current;
    }

    public static void closeCurrent()
    {
        if(Ticket.getCurrent() != null)
        {
            Ticket.getCurrent().clos = true;
            Ticket.add(Ticket.getCurrent());
        }
        Ticket.current = null;
    }

    public static int addTime(int timeToAdd)
    {
        Ticket.getCurrent().duration += timeToAdd;
        return Ticket.getCurrent().getDuree();
    }

    public static List<Ticket> getTickets()
    {
        return Ticket.tickets;
    }

    public static boolean add(Ticket t)
    {
        return Ticket.tickets.add(t);
    }
}
