package com.example.alexis.parkmycar;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexis.parkmycar.models.controlleur.CtrlTicket;
import com.example.alexis.parkmycar.utils.timer.CountDown;
import com.example.alexis.parkmycar.utils.timer.CustomTimer;
import com.example.alexis.parkmycar.utils.timer.IStepListener;
import com.example.alexis.parkmycar.utils.timer.IStopListener;

import java.text.DecimalFormat;
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

    private long duree = 0;

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
                if(stepName.equals("rappel"))
                {
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                    NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                            .setContentText("Votre stationnement expire dans " + ((CountDown) timer).remainingTime() / 60 + " minute(s)");
                    notificationManager.notify(001, builder.build());
                }
            }
        });

        countDown.setOnStopListener(new IStopListener() {
            @Override
            public void onStop(CustomTimer timer) {
                onFragmentInteraction("notifyEnd");
                onFragmentInteraction("endTicket");
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
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentText("Votre stationnement est terminé");
                notificationManager.notify(002, builder.build());
                break;
            case "endTicket":
                //TODO affichage payemet + retour nouveau ticket
                mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.getMenu().getItem(0));

                showEndTicket();

                break;
            default:
                break;
        }
    }

    private void showEndTicket()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                duree = (CtrlTicket.getCurrent().getDuree() * 60) - countDown.remainingTime();


                DecimalFormat df = new DecimalFormat("#.##");
                final Dialog dialog = new Dialog(HubActivity.this);
                dialog.setTitle("Payez votre parking");
                dialog.setContentView(HubActivity.this.getLayoutInflater().inflate(R.layout.dial_recap, null));
                TextView montant = (TextView) dialog.findViewById(R.id.montant);
                Button payer = (Button) dialog.findViewById(R.id.payerBtn);
                payer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        CtrlTicket.finishTicket((int)duree);
                        mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.getMenu().getItem(0));
                    }
                });
                montant.setText(df.format(CtrlTicket.calculPrix(duree)) + " €");
                dialog.show();

                /*AlertDialog.Builder dial = new AlertDialog.Builder(HubActivity.this.getApplicationContext());
                dial.setTitle("Payez votre du");
                dial.setView(HubActivity.this.getLayoutInflater().inflate(R.layout.dial_recap, null));

                AlertDialog alert = dial.create();
                TextView montant = (TextView) alert.findViewById(R.id.montant);
                montant.setText(String.valueOf(CtrlTicket.calculPrix(duree)));
                alert.show();*/


            }
        });
    }
}
