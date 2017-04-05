package com.example.alexis.parkmycar;

import android.inputmethodservice.Keyboard;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewAccountActivity extends AppCompatActivity
{

    EditText nom;
    EditText prenom;
    EditText mail;
    EditText tel;
    EditText password;
    EditText confPassword;
    Button valider;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        nom = (EditText) findViewById(R.id.newNom);
        prenom = (EditText) findViewById(R.id.newPrenom);
        mail = (EditText) findViewById(R.id.newEmail);
        tel = (EditText) findViewById(R.id.newTel);
        password = (EditText) findViewById(R.id.newPassword);
        confPassword = (EditText) findViewById(R.id.confPassword);
        valider = (Button) findViewById(R.id.newOK);

        valider.setEnabled(false);

        nom.setOnKeyListener(onTextChange());
        prenom.setOnKeyListener(onTextChange());
        mail.setOnKeyListener(onTextChange());
        tel.setOnKeyListener(onTextChange());
        mail.setOnKeyListener(onTextChange());
        password.setOnKeyListener(onTextChange());
        confPassword.setOnKeyListener(onTextChange());


        valider.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
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
                    if(nom.getText().toString().equals(""))
                    {
                        valider.setEnabled(false);
                        return false;
                    }
                    if(prenom.getText().toString().equals(""))
                    {
                        valider.setEnabled(false);
                        return false;
                    }
                    if(mail.getText().toString().equals(""))
                    {
                        valider.setEnabled(false);
                        return false;
                    }
                    if(tel.getText().toString().equals(""))
                    {
                        valider.setEnabled(false);
                        return false;
                    }
                    if(password.getText().toString().equals(""))
                    {
                        valider.setEnabled(false);
                        return false;
                    }
                    if(confPassword.getText().toString().equals(""))
                    {
                        valider.setEnabled(false);
                        return false;
                    }

                    if(!confPassword.getText().toString().equals(password.getText().toString()))
                    {
                        valider.setEnabled(false);
                        return false;
                    }

                    valider.setEnabled(true);
                    return false;
                }

                return false;
            }
        };
    }
}
