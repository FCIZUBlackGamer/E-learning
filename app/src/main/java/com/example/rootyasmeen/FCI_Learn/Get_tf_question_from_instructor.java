package com.example.rootyasmeen.FCI_Learn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.Map;

public class Get_tf_question_from_instructor extends AppCompatActivity {

    EditText ques;
    Button con, next;
    RadioGroup radioGroup;
    TextView textView;
    int conter;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_tf_question_from_instructor);
        conter = 1;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView)findViewById(R.id.QuestionNum_tf);
        textView.setText(""+conter);
        if (savedInstanceState!=null){
            conter = savedInstanceState.getInt("Counter");
        }
        ques = (EditText)findViewById(R.id.get_tf_question);
        con = (Button)findViewById(R.id.tf_confirm);
        next = (Button)findViewById(R.id.tf_next);
        radioGroup = (RadioGroup)findViewById(R.id.get_tf_ans);
        reset = (Button)findViewById( R.id.tf_reset);
        reset.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = getIntent().getStringExtra("Type");
                String subject = getIntent().getStringExtra("Subject");
                ResetData(type,subject);
            }
        } );

        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q = ques.getText().toString();

                if (isNetworkConnected()) {
                    if (conter<60){
                        int radioButtonID = radioGroup.getCheckedRadioButtonId();
                        View radioButton = radioGroup.findViewById(radioButtonID);
                        int idx = radioGroup.indexOfChild(radioButton);
                        RadioButton r = (RadioButton)  radioGroup.getChildAt(idx);
                        String selectedtext = r.getText().toString();

                        UpLoadData(q, selectedtext );

                        textView.setText(""+(conter++));
                    }else {
                        Toast.makeText(Get_tf_question_from_instructor.this,"Questions Can't be More Than 60 For Same Subject",Toast.LENGTH_LONG).show();
                        //conter = 1;
                    }
                } else {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(Get_tf_question_from_instructor.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(Get_tf_question_from_instructor.this);
                    }
                    builder.setTitle("Error Message!")
                            .setMessage("Make Sure You Are Connected To Wifi!")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conter = 1;
                Intent intent=new Intent (Get_tf_question_from_instructor.this , Switch_Instructor.class);
                startActivity(intent);
            }
        });

    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(Get_tf_question_from_instructor.this,settings.class));
            return true;
        }else if (id == R.id.info) {
            startActivity(new Intent(Get_tf_question_from_instructor.this,ExamInfo.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void UpLoadData(final String q, final String selectedtext) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest( Request.Method.POST, "http://momenshaheen.16mb.com/e_learning/InsertTFQuestion.php",
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
                        Toast.makeText(Get_tf_question_from_instructor.this,response,Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof ServerError)
                    Toast.makeText( Get_tf_question_from_instructor.this, "Server Error", Toast.LENGTH_SHORT ).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText( Get_tf_question_from_instructor.this, "Connection Timed Out", Toast.LENGTH_SHORT ).show();
                else if (error instanceof NetworkError)
                    Toast.makeText( Get_tf_question_from_instructor.this, "Bad Network Connection", Toast.LENGTH_SHORT ).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("subject",getIntent().getStringExtra("Subject"));
                hashMap.put("question",q);
                hashMap.put("ans1",selectedtext);
                return hashMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void ResetData(final String type, final String subject) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest( Request.Method.POST, "http://momenshaheen.16mb.com/e_learning/ResetQuestions.php",
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
                        Toast.makeText(Get_tf_question_from_instructor.this,response,Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof ServerError)
                    Toast.makeText( Get_tf_question_from_instructor.this, "Server Error", Toast.LENGTH_SHORT ).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText( Get_tf_question_from_instructor.this, "Connection Timed Out", Toast.LENGTH_SHORT ).show();
                else if (error instanceof NetworkError)
                    Toast.makeText( Get_tf_question_from_instructor.this, "Bad Network Connection", Toast.LENGTH_SHORT ).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("subject",subject);
                hashMap.put("type",type);
                return hashMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("Counter",conter);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        conter = savedInstanceState.getInt("Counter");
    }
}
