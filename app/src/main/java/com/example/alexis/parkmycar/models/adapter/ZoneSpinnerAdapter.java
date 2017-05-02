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
import com.example.alexis.parkmycar.models.metier.Voiture;
import com.example.alexis.parkmycar.models.metier.Zone;

import java.util.List;

public class ZoneSpinnerAdapter extends ArrayAdapter
{
    Context context;
    int layoutResourceId;
    List<Zone> zones;

    public ZoneSpinnerAdapter(Context context, int layoutRessourceId, List<Zone> data)
    {
        super(context, layoutRessourceId, data);
        this.context = context;
        this.layoutResourceId = layoutRessourceId;
        this.zones = data;
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

        ImageView img = (ImageView) row.findViewById(R.id.zone_img);
        TextView immat = (TextView) row.findViewById(R.id.zone_nom);

        Zone z = this.zones.get(position);
        immat.setText(z.getNom());

        return row;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        return getView(position, convertView, parent);
    }
}
