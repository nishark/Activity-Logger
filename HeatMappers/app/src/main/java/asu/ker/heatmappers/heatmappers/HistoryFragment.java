package asu.ker.heatmappers.heatmappers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HistoryFragment extends Fragment implements View.OnClickListener{

    private ListView myListView;
    @Override
    public  void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstance)
    {
        final View rootView = layoutInflater.inflate(R.layout.history,viewGroup,false);
        myListView = rootView.findViewById(R.id.log_list_view);
        final ParseAdapter parseAdapter = new ParseAdapter(getActivity(),new ArrayList<ParseObject>());
        myListView.setAdapter(parseAdapter);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Logs");
        //query.whereEqualTo("user", ParseUser.getCurrentUser().getUsername());
        query.fromLocalDatastore();
        query.orderByDescending("createdAt");
        query.setLimit(200);


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> logList, ParseException e) {
                if (e == null) {
                    Log.d("Custom HeatHistory","logList size here "+logList.size());
                    parseAdapter.clear();
                    parseAdapter.addAll(logList);
                    Log.d("added to adapter",logList.get(0).keySet().toString());
                }
            }
        });

        Button mapBTN =  rootView.findViewById(R.id.map_btn);
        mapBTN.setOnClickListener(this);

        return rootView;



    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.map_btn:
                Log.d("d","clicked");
                fragment = new MapFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(this.getId(), fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }

}


