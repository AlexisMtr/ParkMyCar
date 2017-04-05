package com.example.alexis.parkmycar;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.alexis.parkmycar.models.Ticket;
import com.example.alexis.parkmycar.models.Vehicule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HubActivity extends AppCompatActivity implements TicketFragment.OnFragmentInteractionListener,
        CarsFragment.OnFragmentInteractionListener, HistoriqueFragment.OnFragmentInteractionListener, CurrentTicketFragment.OnFragmentInteractionListener
{

    private TextView mTextMessage;
    private TicketFragment ticket = new TicketFragment();
    private CurrentTicketFragment currentTicket = new CurrentTicketFragment();
    private CarsFragment cars = new CarsFragment();
    private HistoriqueFragment histo = new HistoriqueFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_ticket:
                    if(Ticket.getCurrent() == null)
                        ft.replace(R.id.content, ticket).commit();
                    else
                        ft.replace(R.id.content, currentTicket).commit();
                    return true;
                case R.id.navigation_cars:
                    ft.replace(R.id.content, cars).commit();
                    return true;
                case R.id.navigation_histo:
                    ft.replace(R.id.content, histo).commit();
                    return true;
            }
            return false;
        }

    };

    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content, new TicketFragment()).commit();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        for(int i = 0; i < 5; i++)
        {
            String immat = "AB-0" + i + "-YZ";
            String marque = "Citroen";
            String model = "DS";

            Vehicule.add(new Vehicule(immat, model, marque));
        }

        for(int i = 0; i < 5; i++)
        {
            Vehicule v = Vehicule.getVehicules().get(i);
            int d = 20 + i;
            Date date = new Date();
            String emplacement = i + " Rue de la chimie, 38400 St Martin d'Heres";
            int rappel = 5;

            Ticket.add(new Ticket(v, d, date, emplacement, rappel, false));
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        String action = uri.getPath();
    }

    @Override
    public void onFragmentInteraction(String action) {
        switch (action)
        {
            case "moveToChrono":
                mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.getMenu().getItem(0));
                break;
            default:
                break;
        }
    }
}
