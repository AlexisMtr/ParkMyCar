package com.example.alexis.parkmycar;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexis.parkmycar.models.adapter.VehiculeListAdapter;
import com.example.alexis.parkmycar.models.controlleur.CtrlTicket;
import com.example.alexis.parkmycar.models.controlleur.CtrlVoiture;
import com.example.alexis.parkmycar.models.metier.Usager;
import com.example.alexis.parkmycar.models.metier.Voiture;
import com.example.alexis.parkmycar.utils.utils;

import java.util.ArrayList;
import java.util.List;

public class CarsFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static CarsFragment instance;

    public static CarsFragment getInstance()
    {
        if(instance == null)
            instance = CarsFragment.newInstance("","");
        return instance;
    }

    public CarsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarsFragment newInstance(String param1, String param2) {
        CarsFragment fragment = new CarsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FloatingActionButton addBtn;
    VehiculeListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_cars, container, false);
        final ListView vehiculeList = (ListView) view.findViewById(R.id.carsList);
        adapter = new VehiculeListAdapter(getContext(), R.layout.cars_list_item, CtrlVoiture.getVoituresByUsager(utils.getUsager()));
        vehiculeList.setAdapter(adapter);


        addBtn = (FloatingActionButton) view.findViewById(R.id.addCarBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(CarsFragment.this.getContext());
                dialog.setContentView(CarsFragment.this.getActivity().getLayoutInflater().inflate(R.layout.dial_new_vehicule, null));

                final EditText marque = (EditText) dialog.findViewById(R.id.newMarque);
                final EditText model = (EditText) dialog.findViewById(R.id.newModel);
                final EditText immat = (EditText) dialog.findViewById(R.id.newImmat);
                final TextView id = (TextView) dialog.findViewById(R.id.idveh);
                Button add = (Button) dialog.findViewById(R.id.addCar);

                marque.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if(keyEvent.getAction() == KeyEvent.ACTION_UP)
                        {
                            id.setText(marque.getText().toString() + "-" + model.getText().toString() + "-" + immat.getText().toString());
                        }
                        return false;
                    }
                });

                model.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if(keyEvent.getAction() == KeyEvent.ACTION_UP)
                        {
                            id.setText(marque.getText().toString() + "-" + model.getText().toString() + "-" + immat.getText().toString());
                        }
                        return false;
                    }
                });

                immat.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if(keyEvent.getAction() == KeyEvent.ACTION_UP)
                        {
                            id.setText(marque.getText().toString() + "-" + model.getText().toString() + "-" + immat.getText().toString());
                        }
                        return false;
                    }
                });

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new Voiture(marque.getText().toString(),
                                model.getText().toString(),
                                immat.getText().toString(),
                                id.getText().toString(),
                                utils.getUsager().getMail());


                        adapter.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
    }
}
