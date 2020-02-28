package asu.ker.heatmappers.heatmappers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class HeatFragment extends Fragment {

    @Override
    public  void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstance)
    {
        final View rootView = layoutInflater.inflate(R.layout.heat,viewGroup,false);
        return rootView;
    }
}
