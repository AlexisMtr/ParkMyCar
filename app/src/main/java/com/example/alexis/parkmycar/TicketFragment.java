package com.example.alexis.parkmycar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.ViewDragHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alexis.parkmycar.models.Ticket;
import com.example.alexis.parkmycar.models.Vehicule;
import com.example.alexis.parkmycar.models.adapter.VehiculeListAdapter;
import com.example.alexis.parkmycar.models.adapter.VehiculeSpinnerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TicketFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TicketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TicketFragment extends Fragment
{
    private OnFragmentInteractionListener mListener;

    public TicketFragment() {
        // Required empty public constructor
    }

    View view;
    EditText duree;
    TextView endTime;
    EditText zone;
    EditText rappel;
    Button start;
    ImageButton findZone;
    Spinner vehicules;
    Vehicule vehicule;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_ticket, container, false);

        duree = (EditText) view.findViewById(R.id.ticket_duration);
        endTime = (TextView) view.findViewById(R.id.ticket_end);
        rappel = (EditText) view.findViewById(R.id.ticket_rappel);
        zone = (EditText) view.findViewById(R.id.ticket_location);
        findZone = (ImageButton) view.findViewById(R.id.ticket_btn_location);
        start = (Button) view.findViewById(R.id.ticket_start);
        vehicules = (Spinner) view.findViewById(R.id.ticket_vehicule);

        vehicules.setAdapter(new VehiculeSpinnerAdapter(getContext(), R.layout.cars_spinner_item, Vehicule.getVehicules()));


        endTime.setText(this.getEndTime());
        setStartVisibility();

        vehicules.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                vehicule = Vehicule.get(i);
                setStartVisibility();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                vehicule = null;
                setStartVisibility();
            }
        });

        zone.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean focused)
            {
                if(!focused)
                {
                    //TODO : modifier Zone
                    setStartVisibility();
                }
            }
        });

        findZone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TODO : Get Geolocation
                zone.setText("Rue de la Chimie, 38400 Saint Martin d'Heres");
                setStartVisibility();
            }
        });

        duree.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean focused)
            {
                if (!focused)
                {
                    endTime.setText(getEndTime());
                    setStartVisibility();
                }
            }
        });

        rappel.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean focused)
            {
                if(!focused)
                {
                    //TODO : créer un rappel
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TODO : new Ticket and start Timer
                Ticket.createNew(vehicule, 0, Calendar.getInstance(Locale.FRANCE).getTime(), zone.getText().toString(), 0);
                callActivity("moveToChrono");
            }
        });

        return view;
    }

    private String getEndTime()
    {
        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        int min = duree.getText().toString().equals("") ? 0 : Integer.parseInt(duree.getText().toString());
        cal.add(Calendar.MINUTE, min);
        SimpleDateFormat df = new SimpleDateFormat("H:mm", Locale.FRANCE);
        return df.format(cal.getTime());
    }

    private void setStartVisibility()
    {
        if(vehicule == null || duree.getText().toString().equals("") || zone.getText().toString().equals(""))
            start.setEnabled(false);
        else
            start.setEnabled(true);
    }




















    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TicketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TicketFragment newInstance(String param1, String param2) {
        TicketFragment fragment = new TicketFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void callActivity(String action) {
        if (mListener != null) {
            mListener.onFragmentInteraction(action);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String action);
    }
}