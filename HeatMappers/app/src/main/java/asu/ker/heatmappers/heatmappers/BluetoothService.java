package asu.ker.heatmappers.heatmappers;

import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BluetoothService extends Service {

    public class LocalBinder extends Binder{
        BluetoothService getService() {
            return BluetoothService.this;
        }
    }
    private final IBinder mBinder = new LocalBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //cleaning up
        return super.onUnbind(intent);
    }

}




