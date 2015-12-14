package com.example.administrator.weathertoday;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class ThreeFragment extends Fragment implements OnClickListener {

    private View view;
    Context thiscontext;
    Button openDialog;
    //MainDialog mMainDialog;
    FragmentManager manager;
    private TimePicker tp;
    TextView gettime;



    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_three, container, false);
        thiscontext = container.getContext();


        //time = (TextView) view.findViewById(R.id.textview);
        // time.setText("oneFragment");
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/afonts.ttf");
        //tp(ctx, R.style.AppTheme new TimePickerDialog.OnTimeSetListener)
        tp = (TimePicker) view.findViewById(R.id.timePicker);

        gettime = (TextView) view.findViewById(R.id.gettime);


        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                gettime.setText(hourOfDay + "시" + minute +"분");
            }
        });




            return view;
    }


    public void onClick(View view) {

       // mMainDialog = new MainDialog();
        //mMainDialog.show(getFragmentManager(), "MYTAG");



    }
/*
    public static class MainDialog extends DialogFragment implements  OnClickListener{

        private Button check;


        @Override
        public Dialog onCreateDialog(Bundle saveInstanceState){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
            mBuilder.setView(mLayoutInflater.inflate(R.layout.dialog, null));
            mBuilder.setTitle("Dialog Title");
            mBuilder.setMessage("message");




            return mBuilder.create();
        }
        @Override
        public void onStop(){
            super.onStop();
        }

        public void onClick(View view) {



        }

    }


*/


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        }
    }

}