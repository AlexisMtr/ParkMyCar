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

import com.example.alexis.parkmycar.models.Ticket;
import com.example.alexis.parkmycar.models.Vehicule;
import com.example.alexis.parkmycar.utils.services.NotifyService;
import com.example.alexis.parkmycar.utils.timer.CountDown;
import com.example.alexis.parkmycar.utils.timer.CustomTimer;
import com.example.alexis.parkmycar.utils.timer.IStepListener;
import com.example.alexis.parkmycar.utils.timer.IStopListener;
import com.example.alexis.parkmycar.utils.timer.TimeUtil;

import java.util.Date;

public class HubActivity extends AppCompatActivity implements TicketFragment.OnFragmentInteractionListener,
        CarsFragment.OnFragmentInteractionListener, HistoriqueFragment.OnFragmentInteractionListener, CurrentTicketFragment.OnFragmentInteractionListener
{

    private TextView mTextMessage;
    private static TicketFragment ticket = TicketFragment.getInstance();
    private static CurrentTicketFragment currentTicket = CurrentTicketFragment.getInstance();
    private static CarsFragment cars = CarsFragment.getInstance();
    private static HistoriqueFragment histo = HistoriqueFragment.getInstance();
    private static CountDown countDown = new CountDown();

    private NotifyService nService;

    public CountDown getCountDown() { return countDown; }

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


    @Override
    protected void onPause() {
        unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, new IntentFilter(NotifyService.REFRESH_TIME_INTENT));
        if(isServiceRunning(NotifyService.class)) {
            initButton(false);
        }
        else{
            initButton(true);
            setTimer(0, 0);
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(HubActivity.this, NotifyService.class));
        super.onDestroy();
    }

    public void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long timeInMs = intent.getLongExtra(NotifyService.KEY_EXTRA_LONG_TIME_IN_MS, 0);
            Integer[] minSec = TimeUtil.getMinSec(timeInMs);
            setTimer(minSec[TimeUtil.MINUTE], minSec[TimeUtil.SECOND]);
        }
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(NotifyService.REFRESH_TIME_INTENT)) {
                updateGUI(intent);
            }
        }
    };

    private long getStartedValue(){
        return TimeUtil.toMs(0,0);
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void setTimer(int hour, int minute) {
        //timePicker.setHour(hour);
        //timePicker.setMinute(minute);
    }

    private void initButton(boolean startEnable){
        //stopButton.setEnabled(!startEnable);
        //startButton.setEnabled(startEnable);
    }
}
