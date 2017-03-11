package com.kris.flashbeepshake;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CommunicationInterface {

    private int mFragNum = 1;
    private static final int FRAG_1_FLASH = 1;
    private static final int FRAG_2_BEEP = 2;
    private static final int FRAG_3_SHAKE = 3;
    private float mX1;
    private float mX2;
    static final int MIN_DISTANCE = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment frag = new FlashFragment();
        ft.replace(R.id.fragment_frame, frag);
        mFragNum = FRAG_1_FLASH;
        ft.commit();

        ButterKnife.bind(this);

    }

    /**
     *
     * Checks which way the user swiped, changes fragment depending on the direction
     *
     * @param event - Passes MotionEvent
     * @return - Returns Boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch(event.getAction()){

            case MotionEvent.ACTION_DOWN:
                mX1 = event.getX();
                break;

            case MotionEvent.ACTION_UP:
                mX2 = event.getX();
                float deltaX = mX2 - mX1;

                if (Math.abs(deltaX) > MIN_DISTANCE && mX1 < mX2) {
                    setFragmentRightToLeft();
                } else if (Math.abs(deltaX) > MIN_DISTANCE && mX1 >= mX2) {
                    setFragmentLeftToRight();
                }

                break;

        }

        return super.onTouchEvent(event);
    }

    /**
     * switches to the fragment to the right of the current fragment
     */
    private void setFragmentLeftToRight(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        Fragment frag = null;

        switch (mFragNum){

            case 1:

                frag = new BeepFragment();

                mFragNum = FRAG_2_BEEP;
                break;

            case 2:

                frag = new ShakeFragment();

                mFragNum = FRAG_3_SHAKE;
                break;

            case 3:

                frag = new FlashFragment();

                mFragNum = FRAG_1_FLASH;
                break;
        }

        ft.replace(R.id.fragment_frame, frag);
        ft.commit();

    }

    /**
     * switches to the fragment to the left of the current fragment
     */
    private void setFragmentRightToLeft(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        Fragment frag = null;

        switch (mFragNum){

            case 1:

                frag = new ShakeFragment();

                mFragNum = FRAG_3_SHAKE;
                break;

            case 2:

                frag = new FlashFragment();

                mFragNum = FRAG_1_FLASH;
                break;

            case 3:

                frag = new BeepFragment();

                mFragNum = FRAG_2_BEEP;
                break;
        }

        ft.replace(R.id.fragment_frame, frag);
        ft.commit();

    }

    /**
     * Receives MotionEvent from fragment, passes to Activity onTouchEvent
     *
     * @param e - Fragment MotionEvent
     */
    @Override
    public void passMotionEvent(MotionEvent e){

        onTouchEvent(e);

    }
}
