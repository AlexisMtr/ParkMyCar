package com.example.alexis.parkmycar.models.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexis.parkmycar.R;
import com.example.alexis.parkmycar.models.Vehicule;

import java.util.List;

public class VehiculeSpinnerAdapter extends ArrayAdapter
{
    Context context;
    int layoutResourceId;
    List<Vehicule> vehicules;

    public VehiculeSpinnerAdapter(Context context, int layoutRessourceId, List<Vehicule> data)
    {
        super(context, layoutRessourceId, data);
        this.context = context;
        this.layoutResourceId = layoutRessourceId;
        this.vehicules = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
        }

        ImageView img = (ImageView) row.findViewById(R.id.veh_img);
        TextView immat = (TextView) row.findViewById(R.id.veh_immat);
        TextView marque_model = (TextView) row.findViewById(R.id.veh_marque_model);

        Vehicule v = this.vehicules.get(position);
        img.setImageResource(R.drawable.ic_trash);
        immat.setText(v.getImmat());
        marque_model.setText(v.getMarque() + " - " + v.getModel());

        return row;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        return getView(position, convertView, parent);
    }
}
