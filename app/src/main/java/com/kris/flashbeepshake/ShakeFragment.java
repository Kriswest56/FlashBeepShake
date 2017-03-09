package com.kris.flashbeepshake;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnTouch;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShakeFragment extends Fragment {

    CommunicationInterface mCallback;

    @Override
    public void onActivityCreated(Bundle outState) {
        super.onActivityCreated(outState);

        mCallback = (CommunicationInterface) getActivity();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (CommunicationInterface) context;

    }

    public ShakeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_shake, container, false);

        ButterKnife.bind(this, v);

        return v;

    }

    @OnTouch(R.id.quake_image)
    public boolean vibrate(MotionEvent event){

        Vibrator vibrator = (Vibrator) this.getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        switch(event.getAction()){

            case MotionEvent.ACTION_DOWN:

                vibrator.vibrate(1000*60*10);
                break;

            case MotionEvent.ACTION_UP:

                vibrator.cancel();
                break;

        }

        return true;
    }

    @OnTouch(R.id.shakeLayout)
    public boolean swipeEvent(MotionEvent event){

        mCallback.passMotionEvent(event);

        return true;

    }

}
