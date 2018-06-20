package com.example.rootyasmeen.FCI_Learn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Student_subjects extends Fragment {

    Button button,button1,button2,button3,button4,button5;
    Date currentDate, examDate;
    SimpleDateFormat parser;
    String timeStamp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_student_subjects,container,false );
        button = (Button)view.findViewById(R.id.math);
        button1 = (Button)view.findViewById(R.id.physics);
        button2 = (Button)view.findViewById(R.id.ob);
        button3 = (Button)view.findViewById(R.id.intro);
        button4 = (Button)view.findViewById(R.id.english);
        button5 = (Button)view.findViewById(R.id.statistics);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDate("Mathematics");

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDate("Physics");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDate("Organizational Behavior");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDate("Introduction to Computer");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDate("English");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDate("Statistics");
            }
        });

    }

    private void checkDate(final String subject) {
        parser = new SimpleDateFormat("HH:mm");
        timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        try {
            currentDate = parser.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://yasmeenosama30.000webhostapp.com/TakeExam.php",
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

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String res = jsonObject.getString("state");
                            if (res.equals("no")){
                                Toast.makeText( getActivity(), "No Subject Found!", Toast.LENGTH_SHORT ).show();
                            }else {
                                Date fdate = parser.parse(jsonObject.getString("endd"));
                                examDate = parser.parse(jsonObject.getString("setd"));
                                if (currentDate.after(examDate) && currentDate.before(fdate)){
                                    Intent intent = new Intent(getActivity(),Choice_Activity_Student.class);
                                    intent.putExtra("Subject",subject);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText( getActivity(), "Sorry Next Exam Hasn't Started yet!\n    Please Check Exam Information", Toast.LENGTH_SHORT ).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
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
