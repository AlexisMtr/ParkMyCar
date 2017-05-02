package com.example.alexis.parkmycar.models.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alexis.parkmycar.R;
import com.example.alexis.parkmycar.models.metier.Voiture;
import com.example.alexis.parkmycar.utils.SwipeButtonClickListener;
import com.example.alexis.parkmycar.utils.SwipeDetector;

import java.util.List;

public class VehiculeListAdapter extends ArrayAdapter
{
    Context context;
    int layoutResourceId;
    List<Voiture> vehicules;

    public VehiculeListAdapter(Context context, int layoutRessourceId, List<Voiture> data)
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
        Button deleteL = (Button) row.findViewById(R.id.btnDeleteL);
        Button deleteR = (Button) row.findViewById(R.id.btnDeleteR);
        LinearLayout leftLayout = (LinearLayout) row.findViewById(R.id.itemTrashLeft);
        LinearLayout rightLayout = (LinearLayout) row.findViewById(R.id.itemTrashRight);
        LinearLayout mainLayout = (LinearLayout) row.findViewById(R.id.itemContent);

        Voiture v = this.vehicules.get(position);
        img.setImageResource(R.drawable.ic_directions_car_black_24dp);
        immat.setText(v.getImmatriculation());
        marque_model.setText(v.getMarque() + " - " + v.getModele());

        SwipeDetector detector = new SwipeDetector(row, leftLayout, null, mainLayout);
        row.setOnTouchListener(detector);

        View.OnClickListener listener = new SwipeButtonClickListener(leftLayout, null, mainLayout, position, this)
        {
            @Override
            public void onClick(View view)
            {
                this.getLeftLayout().setVisibility(View.GONE);
                //this.getRightLayout().setVisibility(View.GONE);
                this.getMainLayout().setVisibility(View.VISIBLE);
                this.getAdapter().remove(this.getPosition());
            }
        };
        deleteL.setOnClickListener(listener);
        deleteR.setOnClickListener(listener);

        return row;
    }

    @Override
    public void remove(@Nullable Object object) {
        super.remove(object);
        this.vehicules.remove((int)object);
        this.notifyDataSetChanged();
    }
}
