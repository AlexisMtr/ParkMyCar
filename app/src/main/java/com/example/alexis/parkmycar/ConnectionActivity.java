package com.example.alexis.parkmycar;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.alexis.parkmycar.utils.permissions.PermissionUtil;

public class ConnectionActivity extends AppCompatActivity
{

    TextView newAccount;
    Button logInBtn;
    EditText login;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        PermissionUtil.checkPermissions(ConnectionActivity.this, 5, Manifest.permission.INTERNET);

        CtrlUsager.requestUsagers();

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
                if(!login.getText().toString().equals("1") && !password.getText().toString().equals("1"))
                    return;

                Intent addIntent = new Intent(ConnectionActivity.this, HubActivity.class);
                finish();
                startActivity(addIntent);
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
