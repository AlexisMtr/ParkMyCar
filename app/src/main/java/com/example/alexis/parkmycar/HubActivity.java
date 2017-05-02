package com.example.alexis.parkmycar;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.alexis.parkmycar.models.controlleur.CtrlTicket;
import com.example.alexis.parkmycar.utils.timer.CountDown;
import com.example.alexis.parkmycar.utils.timer.CustomTimer;
import com.example.alexis.parkmycar.utils.timer.IStepListener;
import com.example.alexis.parkmycar.utils.timer.IStopListener;

import java.util.Date;

public class HubActivity extends AppCompatActivity implements TicketFragment.OnFragmentInteractionListener,
        CarsFragment.OnFragmentInteractionListener, HistoriqueFragment.OnFragmentInteractionListener, CurrentTicketFragment.OnFragmentInteractionListener
{
    private static TicketFragment ticket = TicketFragment.getInstance();
    private static CurrentTicketFragment currentTicket = CurrentTicketFragment.getInstance();
    private static CarsFragment cars = CarsFragment.getInstance();
    private static HistoriqueFragment histo = HistoriqueFragment.getInstance();
    private static CountDown countDown = new CountDown();

    public CountDown getCountDown() { return countDown; }
    public Context context() { return this.getApplicationContext(); }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_ticket:
                    if(CtrlTicket.getCurrent() == null)
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

        countDown.setOnStepListener(new IStepListener() {
            @Override
            public void onStep(CustomTimer timer, String stepName) {
                Log.i("STEP", stepName);
            }
        });

        countDown.setOnStopListener(new IStopListener() {
            @Override
            public void onStop(CustomTimer timer) {
            }
        });

        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content, new TicketFragment()).commit();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
            case "notifyEnd":
                break;
            default:
                break;
        }
    }
}
