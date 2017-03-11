package com.kris.flashbeepshake;


import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnTouch;


/**
 * A simple {@link Fragment} subclass.
 */
public class BeepFragment extends Fragment {

    private CommunicationInterface mCallback;
    private ToneGenerator mToneG;

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

    public BeepFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_beep, container, false);

        ButterKnife.bind(this, v);

        return v;
    }

    /**
     * OnTouch event to beep or stop beeping phone
     *
     * @param event - Passes MotionEvent
     * @return - Returns Boolean
     */
    @OnTouch(R.id.beep_image)
    public boolean beep(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                mToneG = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                mToneG.startTone(ToneGenerator.TONE_CDMA_MED_PBX_L, 200000);
                break;

            case MotionEvent.ACTION_UP:

                mToneG.stopTone();
                mToneG = null;
                break;

        }

        return true;
    }

    /**
     * Passes event via callback to parent activity
     *
     * @param event - Passes MotionEvent
     * @return - Returns Boolean
     */
    @OnTouch(R.id.beepLayout)
    public boolean swipeEvent(MotionEvent event) {

        mCallback.passMotionEvent(event);

        return true;

    }


}
