package com.faisal.basemodule.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionUtil {

    public static void startConnectionListeningTask(Context context,StatusUpdateListener listener)
    {
        ConnectorThread connectorThread  = new ConnectorThread(context);
        connectorThread.addStatusUpdateListener(listener);
        connectorThread.start();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()) {
            return isInternetAvailable();
        } else {
            return false;
        }
    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }
    private static class ConnectorThread extends Thread{
        private boolean isAlive = true;
        private Context context;
        private StatusUpdateListener statusUpdateListener;
        void addStatusUpdateListener(StatusUpdateListener statusUpdateListener)
        {
            this.statusUpdateListener = statusUpdateListener;
        }
        ConnectorThread(Context context){
            this.context = context;
            isAlive = true;
        }
        @Override
        public void run() {
            super.run();
            while (isAlive)
            {
                if(isNetworkAvailable(context))
                {

                    Log.d("chkconnection","internet connected, thread should stop");
                    isAlive = false;
                    if(statusUpdateListener!=null)
                    {
                        statusUpdateListener.onUpdateStatus(true);
                    }
                }
                else {
                    Log.d("chkconnection","internet not connected . Thread should continue!");
                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.e("chkconnection","error thread sleep:"+e.getMessage());
                    }
                }
            }
        }
    }


    public interface StatusUpdateListener{
        public void onUpdateStatus(Boolean isConnected);
    }

}

