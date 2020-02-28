package asu.ker.heatmappers.heatmappers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.Date;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class CheckInFragment extends Fragment {
    private Button submit;
    private RadioGroup radio1;
    private RadioGroup radio2;
    private Integer selections=0;

    private RadioButton selectedRadio1;
    private RadioButton selectedRadio2;
    private FusedLocationProviderClient locationProviderClient;



    @Override
    public  void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstance)
    {
      final View rootView = layoutInflater.inflate(R.layout.checkin,viewGroup,false);
      final EditText notes = (EditText) rootView.findViewById(R.id.editText);
      radio1 = (RadioGroup)rootView.findViewById(R.id.location);
      radio2 = (RadioGroup)rootView.findViewById(R.id.speclocation);
      submit = (Button)rootView.findViewById(R.id.button);
      submit.setEnabled(false);
      submit.setBackgroundColor(Color.GRAY);
      final ParseObject log = new ParseObject("Logs");

      locationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

      log.put("latitude",0);
      log.put("longitude",0);

      locationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
          @Override
          public void onSuccess(Location location) {
              Log.d("Location is logged",location.toString());
              if(location != null)
              {

                  log.put("latitude",location.getLatitude());
                  log.put("longitude",location.getLongitude());
                  Log.d("log: ",String.valueOf(location.getLatitude()));
                  Log.d("latitude: ",String.valueOf(log.get("latitude")));
                  //Log.d("latitude: ",log.getString("latitude"));
                  //Log.d("longitude: ",log.getString("longitude"));
              }
          }
      });

      radio1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {

              selections+=1;
              if(selections ==2)
              {
                  submit.setEnabled(true);
                  submit.setBackgroundColor(Color.RED);

              }
          }
      });

        radio2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                selections+=1;
                if(selections ==2)
                {
                    submit.setEnabled(true);
                    submit.setBackgroundColor(Color.parseColor("#8c060d"));

                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId1 = radio1.getCheckedRadioButtonId();
                int selectedId2 = radio2.getCheckedRadioButtonId();
                selectedRadio1 =(RadioButton) rootView.findViewById(selectedId1);
                selectedRadio2 = (RadioButton) rootView.findViewById(selectedId2);
                String activity = selectedRadio1.getText().toString() +" : "+selectedRadio2.getText().toString();

                Date currentTime = Calendar.getInstance().getTime();
                Log.d("current time: ",currentTime.toString());
                String time = android.text.format.DateFormat.format("hh:mm",currentTime).toString();
                String date = android.text.format.DateFormat.format("mm/dd/yyyy",currentTime).toString();

                if(notes!=null)
                {
                    notes.clearFocus();
                }

               log.put("activity", activity);
             /*  if (ParseUser.getCurrentUser() != null) {
                   Log.d("User :",ParseUser.getCurrentUser().getUsername());
                    log.put("user", ParseUser.getCurrentUser().getUsername().substring(0, 10));
                }*/

                if (notes.getText().toString() != null) {
                    log.put("notes", notes.getText().toString());
                }
                log.put("time", time);
                log.put("date", date);

                log.saveEventually();
                log.pinInBackground();

                Toast mytoast = Toast.makeText(getActivity(),"Activity Logged",Toast.LENGTH_LONG);
                mytoast.show();

                radio1.clearCheck();
                radio2.clearCheck();
                submit.setEnabled(false);
                submit.setBackgroundColor(Color.GRAY);
                selections =0;
                notes.setText("");


            }
        });


      return rootView;
    }

}
