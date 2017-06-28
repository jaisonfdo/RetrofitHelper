package droidmentor.helper.Retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.ProgressBar;

import droidmentor.helper.R;


/**
 * Created by Jaison on 03/02/17.
 */

public class ProgressUtils {

    Context context;
    private ProgressDialog progress_dialog;

    public ProgressUtils(Context context) {
        this.context=context;
    }

    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progressdialog);
        // dialog.setMessage(Message);
        return dialog;
    }

    public void show_dialog(boolean isCancelable)
    {
        progress_dialog = ProgressDialog.show(context, null, null, true, isCancelable);
        progress_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progress_dialog.setContentView(R.layout.progressdialog);
        ProgressBar progressbar=(ProgressBar)progress_dialog.findViewById(R.id.progressBar1);
        progressbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#2D9CDB"), android.graphics.PorterDuff.Mode.SRC_IN);
        progress_dialog.setCancelable(isCancelable);
        Log.d("util","show dialog");

    }

    public void dismiss_dialog()
    {
        if(progress_dialog!=null&& progress_dialog.isShowing())
            progress_dialog.dismiss();

        Log.d("util","dismiss dialog");
    }

}
