package com.example.alexis.parkmycar;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.ButtonBarLayout;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alexis.parkmycar.models.controlleur.CtrlTicket;
import com.example.alexis.parkmycar.models.controlleur.CtrlUsager;
import com.example.alexis.parkmycar.models.controlleur.CtrlVoiture;
import com.example.alexis.parkmycar.models.controlleur.CtrlZone;
import com.example.alexis.parkmycar.models.metier.Usager;
import com.example.alexis.parkmycar.utils.permissions.PermissionUtil;
import com.example.alexis.parkmycar.utils.persistance.SharedPreferenceStorageImpl;
import com.example.alexis.parkmycar.utils.persistance.StorageService;
import com.example.alexis.parkmycar.utils.timer.CustomTimer;
import com.example.alexis.parkmycar.utils.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

public class ConnectionActivity extends AppCompatActivity
{

    StorageService data;
    TextView newAccount;
    Button logInBtn;
    EditText login;
    EditText password;

    AlertDialog alert;
    AlertDialog.Builder dial;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        data = new SharedPreferenceStorageImpl(this.getApplicationContext());
        PermissionUtil.checkPermissions(ConnectionActivity.this, 5, Manifest.permission.INTERNET);

        CtrlUsager.requestUsagers();

        dial = new AlertDialog.Builder(this);
        dial.setTitle("En attente de connexion");
        dial.setView(R.layout.dial_waiting);

        alert = dial.create();
        alert.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                List<String> emails = data.restore(getApplicationContext());
                if(emails.size() > 0)
                {
                    for(Usager u : CtrlUsager.getUsagers())
                    {
                        if(u.getMail().equals(emails.get(0)))
                        {
                            utils.setUsager(u);

                            Intent addIntent = new Intent(ConnectionActivity.this, HubActivity.class);
                            finish();
                            startActivity(addIntent);
                            return;
                        }
                    }
                }
                data.clear(getApplicationContext());
                alert.dismiss();
                alert = null;
                dial = null;
            }
        }, 10000);

        logInBtn = (Button) findViewById(R.id.connexion);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        newAccount = (TextView) findViewById(R.id.newAccount);

        logInBtn.setEnabled(false);

        login.setOnKeyListener(onTextChange());
        password.setOnKeyListener(onTextChange());

        newAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intentNewAccunt = new Intent(ConnectionActivity.this, NewAccountActivity.class);
                startActivityForResult(intentNewAccunt, 1);
            }
        });

        logInBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                for(Usager u : CtrlUsager.getUsagers())
                {
                    if(u.getMail().equals(login.getText().toString()))
                    {
                        if(password.getText().toString().equals(u.getMdp()))
                        {
                            utils.setUsager(u);
                            List<String> mail = new ArrayList<String>();
                            mail.add(u.getMail());
                            data.store(getApplicationContext(), mail);

                            Intent addIntent = new Intent(ConnectionActivity.this, HubActivity.class);
                            finish();
                            startActivity(addIntent);
                        }
                    }
                }
            }
        });
    }

    private View.OnKeyListener onTextChange()
    {
        return new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent)
            {
                if(keyEvent.getAction() == KeyEvent.ACTION_UP)
                {
                    if(login.getText().toString().equals("") || password.getText().toString().equals(""))
                    {
                        logInBtn.setEnabled(false);
                        return false;
                    }

                    logInBtn.setEnabled(true);
                }

                return false;
            }
        };
    }
}
