package com.nkcs.friends.activity;

import java.util.Calendar;

import com.nkcs.friends.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;

public class DateTimeActivity extends Activity {

	private String myTime1, myTime2;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_time);
        DatePicker datePicker=(DatePicker)findViewById(R.id.datePicker);
        TimePicker timePicker=(TimePicker)findViewById(R.id.timePicker);
        
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int monthOfYear=calendar.get(Calendar.MONTH);
        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        
    	intent = getIntent();
        
        datePicker.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener(){        	
		    public void onDateChanged(DatePicker view, int year,
		            int monthOfYear, int dayOfMonth) {
		    	//2016-08-11 16:01:38
		            myTime1 = year + "-" + monthOfYear + "-" + dayOfMonth;
		    }		    
	    });
        
        timePicker.setOnTimeChangedListener(new OnTimeChangedListener(){

            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            	myTime2 = hourOfDay + ":" + minute + ":" + "00";
            }
        });       

	}
	
	public void confirm(View v){
		if(myTime1 == null)
			myTime1 = "";
		if(myTime2 == null)
			myTime2 = "";
		
	    intent.putExtra("datetime", myTime1 + " " + myTime2);
		setResult(RESULT_OK, intent); 
		finish();
	}
	public void cancle(View v){
		setResult(RESULT_CANCELED, intent); 
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.date_time, menu);
		return true;
	}

}