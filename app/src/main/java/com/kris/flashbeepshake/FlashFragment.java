package com.kris.flashbeepshake;


import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnTouch;


/**
 * A simple {@link Fragment} subclass.
 */
public class FlashFragment extends Fragment {

    private CommunicationInterface mCallback;
    private Context mContext;
    private CameraManager mManager;
    private String[] mList;
    private boolean mIsFlashOn;
    private boolean mHasTorch;

    @Override
    public void onActivityCreated(Bundle outState) {
        super.onActivityCreated(outState);

        mCallback = (CommunicationInterface) getActivity();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (CommunicationInterface) context;
        mContext = context;
    }

    public FlashFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_flash, container, false);

        enableTorch(v);

        ButterKnife.bind(this, v);

        return v;
    }

    /**
     * Checks for torch mode and enable if true
     *
     * @param v - passes view
     */
    private void enableTorch(View v){

        mHasTorch = v.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if(mHasTorch){
            try{
                mManager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
                mList = mManager.getCameraIdList();
            }catch (Exception e){
                Log.d("MSG: ", e.getMessage());
                Toast.makeText(mContext, "There was an issue starting the Flashlight", Toast.LENGTH_LONG).show();
            }

        }else{
            Log.d("MSG: ", "Sorry, Flashlight is not supported on your phone");
        }

    }

    /**
     * OnTouch event to turn flash on or off
     *
     * @param event - Passes MotionEvent
     * @return - Returns Boolean
     */
    @OnTouch(R.id.flash_image)
    public boolean flash(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                processTorchAction();
                break;

        }

        return true;
    }

    /**
     * Method to turn torch on or off
     */
    private void processTorchAction(){

        if(mHasTorch){
            if(!mIsFlashOn){
                turnOnFlash();

            }else{
                turnOffFlash();
            }
        }else{
            Toast.makeText(mContext, "Sorry, Flashlight is not supported on your phone", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Turns camera flash on
     */
    private void turnOnFlash(){

        try{
            mManager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
            mList = mManager.getCameraIdList();
            mManager.setTorchMode(mList[0], true);
            mIsFlashOn = true;
        }catch (Exception e){
            Log.d("MSG: ", e.getMessage());
        }

    }

    /**
     * Turns camera flash off
     */
    private void turnOffFlash(){

        try{
            mManager.setTorchMode(mList[0], false);
            mManager = null;
            mList = null;
            mIsFlashOn = false;
        }catch (Exception e){
            Log.d("MSG: ", e.getMessage());
        }

    }

    /**
     * Passes event via callback to parent activity
     *
     * @param event - Passes MotionEvent
     * @return - Returns Boolean
     */
    @OnTouch(R.id.flashLayout)
    public boolean swipeEvent(MotionEvent event) {

        mCallback.passMotionEvent(event);

        return true;

    }

}
