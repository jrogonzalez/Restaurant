package com.jro.restaurant.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ViewSwitcher;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.jro.restaurant.R;
import com.jro.restaurant.framework.VolleyS;

/**
 * Created by jro on 02/12/2016.
 */

public class BaseVolleyFragment extends Fragment {
    private VolleyS volley;
    protected RequestQueue fRequestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volley = VolleyS.getInstance(getActivity().getApplicationContext());
        fRequestQueue = volley.getRequestQueue();
    }

    public void addToQueue(Request request) {
        if (request != null) {
            request.setTag(this);
            if (fRequestQueue == null)
                fRequestQueue = volley.getRequestQueue();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )); 
            onPreStartConnection();
            fRequestQueue.add(request);
        }
    }

    private void onPreStartConnection() {
        getActivity().setProgressBarIndeterminateVisibility(true);
    }

    public void onConnectionFinished(ViewSwitcher mViewSwitcher) {
        getActivity().setProgressBarIndeterminateVisibility(false);
        mViewSwitcher.setDisplayedChild(TableFragment.TABLE_VIEW_INDEX);
    }

    public void onConnectionFailed(String error) {
        getActivity().setProgressBarIndeterminateVisibility(false);
//        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(R.string.download_error_title);
        alertDialog.setMessage(error);
        alertDialog.setPositiveButton(R.string.download_accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }



}
