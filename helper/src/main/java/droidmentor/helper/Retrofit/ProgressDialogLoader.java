package droidmentor.helper.Retrofit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

/**
 * Created by Jaison on 18/08/16.
 */
public class ProgressDialogLoader
{
    private ProgressDialog pd;

    private static ProgressDialog progressDialog;
    public static ProgressUtils progressUtils;

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

           if(progressUtils==null)
           {
               progressUtils=new ProgressUtils(activity);
               Log.d("dialog null","show");
               progressUtils.show_dialog(false);
           }

           /* if(progressDialog==null)
                progressDialog = ProgressDialog.show(activity, "",title, true);*/
        }
        catch(Exception e)
        {

        }
    }

    public static void progressdialog_dismiss()
    {

        if(progressUtils!=null)
        {
            Log.d("dialog not null","dismiss");
            progressUtils.dismiss_dialog();
        }
        progressUtils=null;

        /*if ((progressDialog != null) && progressDialog.isShowing())
            progressDialog.dismiss();*/

       // progressDialog = null;


    }
}
