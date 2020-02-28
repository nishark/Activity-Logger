package asu.ker.heatmappers.heatmappers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

public class ParseAdapter extends ArrayAdapter<ParseObject> {
    private Context context;
    private LayoutInflater inflater;
    private List<ParseObject> logs;

    public ParseAdapter(Context newcontext, List<ParseObject> newlogs)
    {
        super(newcontext, R.layout.list, newlogs);
        context = newcontext;
        logs = newlogs;
        inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = inflater.inflate(R.layout.list, parent, false);


        TextView titleTextView =
                (TextView) rowView.findViewById(R.id.title);

        //TextView subtitleTextView =
        //(TextView) rowView.findViewById(R.id.recipe_list_subtitle);

        TextView detailTextView =
                (TextView) rowView.findViewById(R.id.descr);

        ParseObject log = logs.get(position);

        titleTextView.setText(log.getString("activity"));
        //subtitleTextView.setText(log.time);
        detailTextView.setText(log.getString("time") + " " +log.getString("date"));


        return rowView;
    }

}
