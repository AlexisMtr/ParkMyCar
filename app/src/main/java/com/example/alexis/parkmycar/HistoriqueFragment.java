package com.example.alexis.parkmycar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.alexis.parkmycar.models.Ticket;
import com.example.alexis.parkmycar.models.adapter.TicketListAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoriqueFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoriqueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoriqueFragment extends Fragment
{
    private OnFragmentInteractionListener mListener;

    public HistoriqueFragment() {
        // Required empty public constructor
    }

    View view;
    ListView tickets;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_historique, container, false);

        tickets = (ListView) view.findViewById(R.id.ticketsListHisto);
        tickets.setAdapter(new TicketListAdapter(getContext(), R.layout.tickets_list_item, Ticket.getTickets()));

        return view;
    }







    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoriqueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoriqueFragment newInstance(String param1, String param2) {
        HistoriqueFragment fragment = new HistoriqueFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
