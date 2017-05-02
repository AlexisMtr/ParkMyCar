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
import com.example.alexis.parkmycar.models.metier.Ticket;
import com.example.alexis.parkmycar.utils.SwipeButtonClickListener;
import com.example.alexis.parkmycar.utils.SwipeDetector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TicketListAdapter extends ArrayAdapter
{
    Context context;
    int layoutResourceId;
    List<Ticket> tickets;

    public TicketListAdapter(Context context, int layoutRessourceId, List<Ticket> data)
    {
        super(context, layoutRessourceId, data);
        this.context = context;
        this.layoutResourceId = layoutRessourceId;
        this.tickets = data;
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

        ImageView img = (ImageView) row.findViewById(R.id.ticket_img);
        TextView date = (TextView) row.findViewById(R.id.ticket_date);
        TextView duree = (TextView) row.findViewById(R.id.ticket_duree);
        TextView emplacement = (TextView) row.findViewById(R.id.ticket_emplacement);
        Button deleteL = (Button) row.findViewById(R.id.btnDeleteLTicket);
        Button deleteR = (Button) row.findViewById(R.id.btnDeleteRTicket);
        LinearLayout leftLayout = (LinearLayout) row.findViewById(R.id.ticketTrashLeft);
        LinearLayout rightLayout = (LinearLayout) row.findViewById(R.id.ticketTrashRight);
        LinearLayout mainLayout = (LinearLayout) row.findViewById(R.id.ticketContent);

        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/Y, HH:mm", Locale.FRANCE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Ticket t = this.tickets.get(position);
        img.setImageResource(R.drawable.ic_history_black_24px);
        try {
            System.out.println(t.getDateDebut());
            System.out.println(sdf.parse(t.getDateDebut()));
            System.out.println(df.format(sdf.parse(t.getDateDebut())));
            date.setText(df.format(sdf.parse(t.getDateDebut())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        emplacement.setText(t.getZone().getNom());
        duree.setText(String.valueOf(t.getDureePayanteMinute()));

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
        this.tickets.remove((int)object);
        this.notifyDataSetChanged();
    }
}
