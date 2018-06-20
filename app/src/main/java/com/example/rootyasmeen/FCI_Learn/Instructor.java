package com.example.rootyasmeen.FCI_Learn;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Instructor extends Fragment {

    Spinner spinner;
    Button tf, mcq, assay, starttime, finishtime;
    String type, stime, ftime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_b,container,false );
        spinner = (Spinner)view.findViewById(R.id.spin_subject);
        tf = (Button)view.findViewById( R.id.truefalse );
        mcq = (Button)view.findViewById( R.id.mcq );
        assay = (Button)view.findViewById( R.id.assay );
        starttime = view.findViewById(R.id.starttime);
        finishtime = view.findViewById(R.id.endtime);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        stime = selectedHour + ":" + selectedMinute;
                        Toast.makeText(getActivity(),"Time set to start "+spinner.getSelectedItem().toString()+" is "+stime,Toast.LENGTH_LONG).show();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        finishtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        ftime = selectedHour + ":" + selectedMinute;
                        Toast.makeText(getActivity(),"Time set to finish "+spinner.getSelectedItem().toString()+" is "+ftime,Toast.LENGTH_LONG).show();
                        UpLoadData(stime,ftime,spinner.getSelectedItem().toString());
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        tf.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (getActivity() , Get_tf_question_from_instructor.class);
                intent.putExtra("Subject",spinner.getSelectedItem().toString());
                intent.putExtra("Type","tf");
                startActivity(intent);
            }
        } );
        mcq.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent (getActivity() , Get_choice_question_from_instructor.class);
                        intent.putExtra("Subject",spinner.getSelectedItem().toString());
                        intent.putExtra("Type","choice");
                        startActivity(intent);
                    }
                } );
        assay.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent (getActivity() , Get_assay_question_from_instructor.class);
                        intent.putExtra("Subject",spinner.getSelectedItem().toString());
                        intent.putExtra("Type","assay");
                        startActivity(intent);
                    }
                } );

    }

    private void UpLoadData(final String settime, final String endtime, final String subject) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://yasmeenosama30.000webhostapp.com/SetExamTime.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String s = URLEncoder.encode(response,"ISO-8859-1");
                            response = URLDecoder.decode(s,"UTF-8");
                        }catch (UnsupportedEncodingException e){
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();

                        Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof ServerError)
                    Toast.makeText( getActivity(), "Server Error", Toast.LENGTH_SHORT ).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText( getActivity(), "Connection Timed Out", Toast.LENGTH_SHORT ).show();
                else if (error instanceof NetworkError)
                    Toast.makeText( getActivity(), "Bad Network Connection", Toast.LENGTH_SHORT ).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("subject",subject);
                hashMap.put("set_date",settime);
                hashMap.put("end_date",endtime);
                return hashMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
/*
        <item>Discrete Mathematics</item>
        <item>Management</item>
        <item>Structural Programming</item>
        <item>Computer Skills</item>
        <item>Writing Reports and Presentation Skills</item>
        <item>Information System</item>
        <item>Economy</item>
        <item>Data Structure</item>
        <item>Numerical</item>
        <item>OR</item>
        <item>Architecture</item>
        <item>Project Management</item>
        <item>DataBase </item>
        <item>Object Oriented </item>
        <item>Ethics</item>
        <item>Algorithms</item>
        <item>Operating System</item>
        <item>System Analysis and Design</item>
        <item>DataBase Management System </item>
        <item>GIS</item>
        <item>Graphics </item>
        <item>Artificial Intelligence</item>
        <item>Network</item>
        <item>Formal language</item>
        <item>Multimedia</item>
        <item>Simulation </item>
        <item>System Analysis and Design2</item>
        <item>Internet Technology </item>
        <item>Expert System</item>
*
* */