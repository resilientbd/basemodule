package com.faisal.basemodule;

import android.app.ProgressDialog;
import android.content.Context;

public class BaseProgressbarHandler {
    static ProgressDialog progressDialog;

    /**
     * Show dailog progress loading
     *
     * @param context context
     */
    public static void ShowLoadingProgress(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    /**
     * Dismiss progress of loading
     *
     * @param context context
     */
    public static void DismissProgress(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        if (progressDialog.isShowing())
        {
            try {
                progressDialog.dismiss();
            }catch (Exception e)
            {

            }

        }
    }
}