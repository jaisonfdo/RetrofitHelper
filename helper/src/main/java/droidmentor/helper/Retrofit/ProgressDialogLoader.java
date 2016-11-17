package droidmentor.helper.Retrofit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Jaison on 18/08/16.
 */
public class ProgressDialogLoader
{
    private ProgressDialog pd;

    private static ProgressDialog progressDialog;

    Context context;
    Activity activity;

    public ProgressDialogLoader(Context context) {
        this.context = context;
        this.activity= (Activity) context;
    }

    public void progress_dialog_dismiss()
    {

            if ((pd != null) && pd.isShowing())
                pd.dismiss();

            pd = null;


    }

    public void progress_dialog_creation()
    {
        try {
            if(pd==null)
                pd = ProgressDialog.show(activity, "", "Loading", true);
        }
        catch(Exception e)
        {

        }
    }

    public static void progressdialog_creation(Activity activity,String title)
    {
        try {
            if(progressDialog==null)
                progressDialog = ProgressDialog.show(activity, "",title, true);
        }
        catch(Exception e)
        {

        }
    }

    public static void progressdialog_dismiss()
    {

        if ((progressDialog != null) && progressDialog.isShowing())
            progressDialog.dismiss();

        progressDialog = null;


    }
}
