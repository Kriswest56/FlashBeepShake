package com.kris.flashbeepshake;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Vibrator;
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

    private CommunicationInterface mCallback;
    private Vibrator mVibrator;

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

    /**
     * OnTouch event to vibrate or stop vibrating phone
     *
     * @param event - Passes MotionEvent
     * @return - Returns Boolean
     */
    @OnTouch(R.id.quake_image)
    public boolean vibrate(MotionEvent event){

        mVibrator = (Vibrator) this.getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        switch(event.getAction()){

            case MotionEvent.ACTION_DOWN:

                mVibrator.vibrate(1000*60*10);
                break;

            case MotionEvent.ACTION_UP:

                mVibrator.cancel();
                break;

        }

        mVibrator = null;
        return true;
    }

    /**
     * Passes event via callback to parent activity
     *
     * @param event - Passes MotionEvent
     * @return - Returns Boolean
     */
    @OnTouch(R.id.shakeLayout)
    public boolean swipeEvent(MotionEvent event){

        mCallback.passMotionEvent(event);

        return true;

    }

}
