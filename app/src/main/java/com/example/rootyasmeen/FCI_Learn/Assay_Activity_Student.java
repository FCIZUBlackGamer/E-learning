package com.example.rootyasmeen.FCI_Learn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.itextpdf.text.pdf.BaseFont;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assay_Activity_Student extends AppCompatActivity {
    private RecyclerView recyclerView3;
    private RecyclerView.Adapter adapter3;
    private List<ListItem_Assay> listItems3;

    Button button;
    ResultDatabase resultDatabase;

    Date currentDate, examDate;
    SimpleDateFormat parser;
    String timeStamp;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = getIntent();
        button = (Button) findViewById(R.id.Finish);
        recyclerView3 = (RecyclerView) findViewById(R.id.recycleview3);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        resultDatabase = new ResultDatabase(this);

        if (isNetworkConnected()) {
            listItems3 = new ArrayList<>();
            LoadRecyclerViewData();
        } else {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDate(getIntent().getStringExtra("Subject"));

            }
        });
    }


    private void writeToFile() {
        try {

            ResultDatabase resultDatabase = new ResultDatabase(Assay_Activity_Student.this);
            Cursor cursor = resultDatabase.ShowQuestionANDAnswer();
            int result = 0;
            while (cursor.moveToNext()) {
                result += Integer.parseInt(cursor.getString(3));
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(Assay_Activity_Student.this);
            builder.setTitle("Result")
                    .setMessage("Congratulations!\n Your Score is: " + result)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Assay_Activity_Student.this, Switch_Student.class));
                            GeneratePDF();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Assay_Activity_Student.this, Switch_Student.class));
                    GeneratePDF();

                }
            }).show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkDate(final String subject) {
        parser = new SimpleDateFormat("HH:mm");
        timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        try {
            currentDate = parser.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final ProgressDialog progressDialog = new ProgressDialog(Assay_Activity_Student.this);
        progressDialog.setMessage("Loading Data ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://yasmeenosama30.000webhostapp.com/TakeExam.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String s = URLEncoder.encode(response, "ISO-8859-1");
                            response = URLDecoder.decode(s, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String res = jsonObject.getString("state");
                            if (res.equals("no")) {
                                Toast.makeText(Assay_Activity_Student.this, "No Subject Found!", Toast.LENGTH_SHORT).show();
                            } else {
                                Date fdate = parser.parse(jsonObject.getString("endd"));
                                examDate = parser.parse(jsonObject.getString("setd"));
                                if (currentDate.after(examDate) && currentDate.before(fdate)) {
                                    writeToFile();
                                } else {
                                    Toast.makeText(Assay_Activity_Student.this, "Sorry Next Exam Hasn't Started yet!\n    Please Check Exam Information", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Assay_Activity_Student.this, "Server Error", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(Assay_Activity_Student.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(Assay_Activity_Student.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("subject", subject);
                return hashMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Assay_Activity_Student.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(Assay_Activity_Student.this, settings.class));
            return true;
        } else if (id == R.id.info) {
            startActivity(new Intent(Assay_Activity_Student.this, ExamInfo.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void GeneratePDF() {
        // TODO Auto-generated method stub
        String filename = "Result-FCI_Learn";
        //String filecontent = "Contenido";
        Methods fop = new Methods(this);
        if (fop.write(filename, getIntent().getStringExtra("Subject"))) {
            Toast.makeText(getApplicationContext(),
                    filename + ".pdf created", Toast.LENGTH_SHORT)
                    .show();
            String[] mailto = {"momen.shahen2020@gmail.com"};
            Uri uri = Uri.parse("/sdcard/" + filename + ".pdf");

            File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename + ".pdf");
            Uri path = Uri.fromFile(filelocation);

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, mailto);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Result of Exam Powered by @FCI-Learn");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Thanks For Your Kinds!");
            emailIntent.setType("application/pdf");
            emailIntent.putExtra(Intent.EXTRA_STREAM, path);

            startActivity(Intent.createChooser(emailIntent, "Send email using:"));
            resultDatabase.DeleteTableAnswer();
        } else {
            Toast.makeText(getApplicationContext(), "I/O error",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void LoadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data ...");
        progressDialog.show();
        if (getIntent().getStringExtra("Subject").equals("Mathematics")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://momenshaheen.16mb.com/e_learning/GetMathExam.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            try {
//                                String s = URLEncoder.encode(response,"ISO-8859-1");
//                                response = URLDecoder.decode(s,"UTF-8");
//                            }catch (UnsupportedEncodingException e){
//                                e.printStackTrace();
//                            }
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("assay_questions_data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    ListItem_Assay item = new ListItem_Assay(
                                            object.getString("id"),
                                            object.getString("quuestion"),
                                            object.getString("keywords")
                                    );
                                    listItems3.add(item);

                                }
                                adapter3 = new AssayAdapter(listItems3, Assay_Activity_Student.this, "http://momenshaheen.16mb.com/e_learning/GetMathExam.php");
                                recyclerView3.setAdapter(adapter3);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    if (error instanceof ServerError)
                        Toast.makeText(Assay_Activity_Student.this, "Server Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof TimeoutError)
                        Toast.makeText(Assay_Activity_Student.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                    else if (error instanceof NetworkError)
                        Toast.makeText(Assay_Activity_Student.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap hashMap = new HashMap();
                    hashMap.put("type", "assay");
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else if (getIntent().getStringExtra("Subject").equals("Organizational Behavior")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://momenshaheen.16mb.com/e_learning/GetOBExam.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                String s = URLEncoder.encode(response, "ISO-8859-1");
                                response = URLDecoder.decode(s, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("assay_questions_data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    ListItem_Assay item = new ListItem_Assay(
                                            object.getString("id"),
                                            object.getString("quuestion"),
                                            object.getString("keywords")
                                    );
                                    listItems3.add(item);

                                }
                                adapter3 = new AssayAdapter(listItems3, Assay_Activity_Student.this, "http://momenshaheen.16mb.com/e_learning/GetOBExam.php");
                                recyclerView3.setAdapter(adapter3);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Assay_Activity_Student.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap hashMap = new HashMap();
                    hashMap.put("type", "assay");
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else if (getIntent().getStringExtra("Subject").equals("Introduction to Computer")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://momenshaheen.16mb.com/e_learning/GetIntroductionToComputerExam.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                String s = URLEncoder.encode(response, "ISO-8859-1");
                                response = URLDecoder.decode(s, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("assay_questions_data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    ListItem_Assay item = new ListItem_Assay(
                                            object.getString("id"),
                                            object.getString("quuestion"),
                                            object.getString("keywords")
                                    );
                                    listItems3.add(item);
                                }
                                adapter3 = new AssayAdapter(listItems3, Assay_Activity_Student.this, "http://momenshaheen.16mb.com/e_learning/GetIntroductionToComputerExam.php");
                                recyclerView3.setAdapter(adapter3);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Assay_Activity_Student.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap hashMap = new HashMap();
                    hashMap.put("type", "assay");
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else if (getIntent().getStringExtra("Subject").equals("English")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://momenshaheen.16mb.com/e_learning/GetEnglishExam.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                String s = URLEncoder.encode(response, "ISO-8859-1");
                                response = URLDecoder.decode(s, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("assay_questions_data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    ListItem_Assay item = new ListItem_Assay(
                                            object.getString("id"),
                                            object.getString("quuestion"),
                                            object.getString("keywords")
                                    );
                                    listItems3.add(item);
                                }
                                adapter3 = new AssayAdapter(listItems3, Assay_Activity_Student.this, "http://momenshaheen.16mb.com/e_learning/GetEnglishExam.php");
                                recyclerView3.setAdapter(adapter3);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Assay_Activity_Student.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap hashMap = new HashMap();
                    hashMap.put("type", "assay");
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else if (getIntent().getStringExtra("Subject").equals("Statistics")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://momenshaheen.16mb.com/e_learning/GetStatisticsExam.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                String s = URLEncoder.encode(response, "ISO-8859-1");
                                response = URLDecoder.decode(s, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("assay_questions_data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    ListItem_Assay item = new ListItem_Assay(
                                            object.getString("id"),
                                            object.getString("quuestion"),
                                            object.getString("keywords")
                                    );
                                    listItems3.add(item);
                                }
                                adapter3 = new AssayAdapter(listItems3, Assay_Activity_Student.this, "http://momenshaheen.16mb.com/e_learning/GetStatisticsExam.php");
                                recyclerView3.setAdapter(adapter3);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Assay_Activity_Student.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap hashMap = new HashMap();
                    hashMap.put("type", "assay");
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else if (getIntent().getStringExtra("Subject").equals("Physics")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://momenshaheen.16mb.com/e_learning/GetPhysicsExam.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                String s = URLEncoder.encode(response, "ISO-8859-1");
                                response = URLDecoder.decode(s, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("assay_questions_data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    ListItem_Assay item = new ListItem_Assay(
                                            object.getString("id"),
                                            object.getString("quuestion"),
                                            object.getString("keywords")
                                    );
                                    listItems3.add(item);
                                }
                                adapter3 = new AssayAdapter(listItems3, Assay_Activity_Student.this, "http://momenshaheen.16mb.com/e_learning/GetPhysicsExam.php");
                                recyclerView3.setAdapter(adapter3);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Assay_Activity_Student.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap hashMap = new HashMap();
                    hashMap.put("type", "assay");
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(Assay_Activity_Student.this, "No Subject To Get Questions", Toast.LENGTH_SHORT).show();
        }
    }

}
