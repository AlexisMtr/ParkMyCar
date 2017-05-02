package com.example.alexis.parkmycar;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.alexis.parkmycar.models.controlleur.CtrlTicket;
import com.example.alexis.parkmycar.models.controlleur.CtrlVoiture;
import com.example.alexis.parkmycar.models.metier.Voiture;
import com.example.alexis.parkmycar.utils.timer.CountDown;
import com.example.alexis.parkmycar.utils.timer.CustomTimer;
import com.example.alexis.parkmycar.utils.timer.IFinishListener;
import com.example.alexis.parkmycar.utils.timer.IStopListener;
import com.example.alexis.parkmycar.utils.timer.ITickListener;
import com.example.alexis.parkmycar.utils.timer.TimeUtil;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CurrentTicketFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CurrentTicketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentTicketFragment extends Fragment
{
    private OnFragmentInteractionListener mListener;

    private static CurrentTicketFragment instance;

    public static CurrentTicketFragment getInstance()
    {
        if(instance == null)
            instance = CurrentTicketFragment.newInstance("","");
        return instance;
    }

    public CurrentTicketFragment() {
        // Required empty public constructor
    }


    View view;
    NumberPicker minToAdd;
    ImageButton removeMin;
    ImageButton addMin;
    TextView chrono;
    //TextView timeTotal;
    TextView tarif;
    TextView tarifTot;
    TextView vehModel;
    TextView vehImmat;
    TextView zoneName;
    TextView zoneName2;
    ImageButton stopTicket;
    ImageButton cancelTicket;
    CountDown countDown;
    ITickListener tickListener;
    IStopListener stopListener;
    IFinishListener finishListener;

    double tarif_h = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_current_ticket, container, false);

        //minToAdd = (EditText) view.findViewById(R.id.timeToAdd);
        minToAdd = (NumberPicker) view.findViewById(R.id.timeToAdd);
        removeMin = (ImageButton) view.findViewById(R.id.removeTimeTicket);
        addMin = (ImageButton) view.findViewById(R.id.addTimeTicket);
        chrono = (TextView) view.findViewById(R.id.chronoTime);
        //timeTotal = (TextView) view.findViewById(R.id.time_tot);
        stopTicket = (ImageButton) view.findViewById(R.id.stopTicket);
        cancelTicket = (ImageButton) view.findViewById(R.id.cancelTicket);
        cancelTicket.setVisibility(View.GONE);
        tarif = (TextView) view.findViewById(R.id.tarif);
        tarifTot = (TextView) view.findViewById(R.id.tarif_tot);
        vehModel = (TextView) view.findViewById(R.id.curVehName);
        vehImmat = (TextView) view.findViewById(R.id.curVehImmat);
        zoneName = (TextView) view.findViewById(R.id.curZoneName2);
        //zoneName2 = (TextView) view.findViewById(R.id.curZoneName2);

        chrono.setText(countDown.getTime());

        tarif_h = CtrlTicket.getCurrent().getZone().getTarif();

        double tarif_total = (CtrlTicket.getCurrent().getDuree() * 60) * ((tarif_h/60)/60);
        DecimalFormat df = new DecimalFormat("#.##");
        tarifTot.setText(df.format(tarif_total) + " €");

        Voiture currentVeh = CtrlVoiture.getVoitureByImmat(CtrlTicket.getCurrent().getImmat());
        vehModel.setText(currentVeh.getMarque() + " - " + currentVeh.getModele());
        vehImmat.setText(currentVeh.getImmatriculation());

        zoneName.setText(CtrlTicket.getCurrent().getZone().getNom());

        minToAdd.setMaxValue(30);
        minToAdd.setMinValue(0);
        minToAdd.setValue(10);



        addMin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int min = Integer.parseInt(String.valueOf(minToAdd.getValue()));
                countDown.addMinToStartTime(min);
                CtrlTicket.addMinutes(min);
                double tarif_total = (CtrlTicket.getCurrent().getDuree() * 60) * ((tarif_h/60)/60);
                DecimalFormat df = new DecimalFormat("#.##");
                tarifTot.setText(df.format(tarif_total) + " €");

                chrono.setText(countDown.getTime());
            }
        });

        removeMin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int min = - Integer.parseInt(String.valueOf(minToAdd.getValue()));
                if(min <= countDown.remainingTime())
                    return;

                countDown.addMinToStartTime(min);
                CtrlTicket.addMinutes(min);
                double tarif_total = (CtrlTicket.getCurrent().getDuree() * 60) * ((tarif_h/60)/60);
                DecimalFormat df = new DecimalFormat("#.##");
                tarifTot.setText(df.format(tarif_total) + " €");

                chrono.setText(countDown.getTime());
            }
        });

        cancelTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CtrlTicket.cancelCurrent();
                countDown.stop();
            }
        });

        stopTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDown.stop();
                CtrlTicket.finishTicket();
            }
        });



        return view;
    }

    private void updateGUI()
    {
        if(this.getActivity() == null)
            return;

        if(CtrlTicket.getCurrent() == null)
            return;

        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run()
            {

                double _tarif = ((CtrlTicket.getCurrent().getDuree() * 60) - countDown.remainingTime()) * ((tarif_h/60)/60);
                DecimalFormat df = new DecimalFormat("#.##");
                tarif.setText(df.format(_tarif) + " €");
                chrono.setText(countDown.getTime());

                if(countDown.remainingTime() < ((CtrlTicket.getCurrent().getDuree() * 60) - 10))
                {
                    cancelTicket.setEnabled(false);
                    cancelTicket.setVisibility(View.GONE);
                }
                else
                    cancelTicket.setVisibility(View.VISIBLE);
            }
        });
    }













    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrentTicketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentTicketFragment newInstance(String param1, String param2) {
        CurrentTicketFragment fragment = new CurrentTicketFragment();
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

        tickListener = new ITickListener() {
            @Override
            public void onTick(CustomTimer timer) {
                updateGUI();
            }
        };

        stopListener = new IStopListener() {
            @Override
            public void onStop(CustomTimer timer) {
                callActivity("notifyEnd");
                callActivity("moveToChrono");
            }
        };

        finishListener = new IFinishListener() {
            @Override
            public void onFinish(CustomTimer timer) {
                callActivity("moveToChrono");
            }
        };


        this.countDown = ((HubActivity)this.getActivity()).getCountDown();
        this.countDown.setOnTickListener(tickListener);
        this.countDown.setOnStopListener(stopListener);
        this.countDown.setOnFinishListener(finishListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        tickListener = null;
        stopListener = null;
        finishListener = null;
        this.countDown.setOnTickListener(tickListener);
        this.countDown.setOnStopListener(stopListener);
        this.countDown.setOnFinishListener(finishListener);
        //this.countDown.removeStep("disableCancel");
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
