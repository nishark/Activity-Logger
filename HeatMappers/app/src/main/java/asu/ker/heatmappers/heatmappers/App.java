package asu.ker.heatmappers.heatmappers;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseUser;

public class App extends Application {


 @Override
public void onCreate() {
     super.onCreate();
     Parse.enableLocalDatastore(this);
    // Parse.initialize(this);
    Parse.initialize(new Parse.Configuration.Builder(this)
            .applicationId("YOUR_APP_ID")
            // if defined
            .enableLocalDataStore()
            .server("https://heatmappers-act-log.herokuapp.com/parse/")
            .build()
    );
     ParseUser.enableAutomaticUser();
     ParseUser.getCurrentUser().increment("RunCount");
     ParseUser.getCurrentUser().put("platform", "android");

     ParseUser.getCurrentUser().saveInBackground();

}
}
