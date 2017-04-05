package com.example.alexis.parkmycar;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexis.parkmycar.models.Ticket;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


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

    public CurrentTicketFragment() {
        // Required empty public constructor
    }


    View view;
    EditText minToAdd;
    ImageButton removeMin;
    ImageButton addMin;
    TextView chrono;
    ImageButton stopTicket;
    Ticket current;

    int min = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_current_ticket, container, false);

        minToAdd = (EditText) view.findViewById(R.id.timeToAdd);
        removeMin = (ImageButton) view.findViewById(R.id.removeTimeTicket);
        addMin = (ImageButton) view.findViewById(R.id.addTimeTicket);
        chrono = (TextView) view.findViewById(R.id.chronoTime);
        stopTicket = (ImageButton) view.findViewById(R.id.stopTicket);


        addMin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                min = Integer.parseInt(minToAdd.getText().toString());
                DateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.FRANCE);
                Calendar c = Calendar.getInstance(Locale.FRANCE);
                try {
                    Date time = sdf.parse(chrono.getText().toString());
                    c.setTime(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                c.add(Calendar.MINUTE, min);

                chrono.setText(sdf.format(c.getTime()));
            }
        });

        return view;
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
