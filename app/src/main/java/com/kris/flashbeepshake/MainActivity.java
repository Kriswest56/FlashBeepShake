package com.kris.flashbeepshake;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CommunicationInterface {

    private int fragNum = 1;
    private static final int FRAG_1_FLASH = 1;
    private static final int FRAG_2_BEEP = 2;
    private static final int FRAG_3_SHAKE = 3;
    private float x1;
    private float x2;
    static final int MIN_DISTANCE = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment frag = new BeepFragment();
        ft.replace(R.id.fragment_frame, frag);
        fragNum = FRAG_2_BEEP;
        ft.commit();

        ButterKnife.bind(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch(event.getAction()){

            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;

            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE && x1 < x2) {
                    setFragmentRightToLeft();
                } else if (Math.abs(deltaX) > MIN_DISTANCE && x1 >= x2) {
                    setFragmentLeftToRight();
                }

                break;

        }

        return super.onTouchEvent(event);
    }

    private void setFragmentLeftToRight(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        Fragment frag = null;

        switch (fragNum){

            case 1:

                frag = new BeepFragment();

                fragNum = FRAG_2_BEEP;
                break;

            case 2:

                frag = new ShakeFragment();

                fragNum = FRAG_3_SHAKE;
                break;

            case 3:

                frag = new FlashFragment();

                fragNum = FRAG_1_FLASH;
                break;
        }

        ft.replace(R.id.fragment_frame, frag);
        ft.commit();

    }

    private void setFragmentRightToLeft(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        Fragment frag = null;

        switch (fragNum){

            case 1:

                frag = new ShakeFragment();

                fragNum = FRAG_3_SHAKE;
                break;

            case 2:

                frag = new FlashFragment();

                fragNum = FRAG_1_FLASH;
                break;

            case 3:

                frag = new BeepFragment();

                fragNum = FRAG_2_BEEP;
                break;
        }

        ft.replace(R.id.fragment_frame, frag);
        ft.commit();

    }

    @Override
    public void passMotionEvent(MotionEvent e){

        onTouchEvent(e);

    }
}
