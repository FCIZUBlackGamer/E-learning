package com.example.rootyasmeen.FCI_Learn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Choice_Activity_Student extends AppCompatActivity {

    private RecyclerView recyclerView1;
    private RecyclerView.Adapter adapter1;
    private List<ListItem_Choice> listItems1;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button = (Button)findViewById(R.id.Exam_next_one);
        recyclerView1 = (RecyclerView) findViewById(R.id.recycleview2);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));


        if (isNetworkConnected()) {
            listItems1 = new ArrayList<>();
            LoadRecyclerViewData();
        }else {
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

                Intent intent = new Intent(Choice_Activity_Student.this,TF_Activity_Student.class);
                intent.putExtra("Subject",getIntent().getStringExtra("Subject"));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(Choice_Activity_Student.this,settings.class));
            return true;
        }else if (id == R.id.info) {
            startActivity(new Intent(Choice_Activity_Student.this,ExamInfo.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void LoadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data ...");
        progressDialog.show();
        if (getIntent().getStringExtra("Subject").equals("Mathematics")){
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
                                JSONArray jsonArray = jsonObject.getJSONArray("choice_questions_data");
                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    ListItem_Choice item = new ListItem_Choice();
                                    item = SetRand(item,object);
                                    listItems1.add(item);
                                }
                                adapter1= new ChoiceAdapter(listItems1 ,Choice_Activity_Student.this,"http://momenshaheen.16mb.com/e_learning/GetMathExam.php");
                                recyclerView1.setAdapter(adapter1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    if (error instanceof ServerError)
                        Toast.makeText( Choice_Activity_Student.this, "Server Error", Toast.LENGTH_SHORT ).show();
                    else if (error instanceof TimeoutError)
                        Toast.makeText( Choice_Activity_Student.this, "Connection Timed Out", Toast.LENGTH_SHORT ).show();
                    else if (error instanceof NetworkError)
                        Toast.makeText( Choice_Activity_Student.this, "Bad Network Connection", Toast.LENGTH_SHORT ).show();                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap hashMap = new HashMap();
                    hashMap.put("type","choice");
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else if (getIntent().getStringExtra("Subject").equals("Organizational Behavior")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://momenshaheen.16mb.com/e_learning/GetOBExam.php",
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
                                JSONArray jsonArray = jsonObject.getJSONArray("choice_questions_data");
                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    ListItem_Choice item = new ListItem_Choice();
                                    item = SetRand(item,object);
                                    listItems1.add(item);
                                }
                                adapter1= new ChoiceAdapter(listItems1 ,Choice_Activity_Student.this,"http://momenshaheen.16mb.com/e_learning/GetOBExam.php");
                                recyclerView1.setAdapter(adapter1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Choice_Activity_Student.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap hashMap = new HashMap();
                    hashMap.put("type","choice");
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else if (getIntent().getStringExtra("Subject").equals("Introduction to Computer")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://momenshaheen.16mb.com/e_learning/GetIntroductionToComputerExam.php",
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
                                JSONArray jsonArray = jsonObject.getJSONArray("choice_questions_data");
                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    ListItem_Choice item = new ListItem_Choice();
                                    item = SetRand(item,object);
                                    listItems1.add(item);
                                }
                                adapter1= new ChoiceAdapter(listItems1 ,Choice_Activity_Student.this,"http://momenshaheen.16mb.com/e_learning/GetIntroductionToComputerExam.php");
                                recyclerView1.setAdapter(adapter1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Choice_Activity_Student.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap hashMap = new HashMap();
                    hashMap.put("type","choice");
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else if (getIntent().getStringExtra("Subject").equals("English")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://momenshaheen.16mb.com/e_learning/GetEnglishExam.php",
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
                                JSONArray jsonArray = jsonObject.getJSONArray("choice_questions_data");
                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    ListItem_Choice item = new ListItem_Choice();
                                    item = SetRand(item,object);
                                    listItems1.add(item);
                                }
                                adapter1= new ChoiceAdapter(listItems1 ,Choice_Activity_Student.this,"http://momenshaheen.16mb.com/e_learning/GetEnglishExam.php");
                                recyclerView1.setAdapter(adapter1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Choice_Activity_Student.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap hashMap = new HashMap();
                    hashMap.put("type","choice");
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else if (getIntent().getStringExtra("Subject").equals("Statistics")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://momenshaheen.16mb.com/e_learning/GetStatisticsExam.php",
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
                                JSONArray jsonArray = jsonObject.getJSONArray("choice_questions_data");
                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    ListItem_Choice item = new ListItem_Choice();
                                    item = SetRand(item,object);
                                    listItems1.add(item);
                                }
                                adapter1= new ChoiceAdapter(listItems1 ,Choice_Activity_Student.this,"http://momenshaheen.16mb.com/e_learning/GetStatisticsExam.php");
                                recyclerView1.setAdapter(adapter1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Choice_Activity_Student.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap hashMap = new HashMap();
                    hashMap.put("type","choice");
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else if (getIntent().getStringExtra("Subject").equals("Physics")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://momenshaheen.16mb.com/e_learning/GetPhysicsExam.php",
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
                                JSONArray jsonArray = jsonObject.getJSONArray("choice_questions_data");
                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    ListItem_Choice item = new ListItem_Choice();
                                    item = SetRand(item,object);
                                    listItems1.add(item);
                                }
                                adapter1= new ChoiceAdapter(listItems1 ,Choice_Activity_Student.this,"http://momenshaheen.16mb.com/e_learning/GetPhysicsExam.php");
                                recyclerView1.setAdapter(adapter1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Choice_Activity_Student.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap hashMap = new HashMap();
                    hashMap.put("type","choice");
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(Choice_Activity_Student.this,"No Subject To Get Questions",Toast.LENGTH_SHORT).show();
        }

    }
    private static ListItem_Choice SetRand(ListItem_Choice listItem_choice, JSONObject jsonObject) throws JSONException {
        Random random = new Random();
        int x = random.nextInt(4);
        switch (x){
            case 0:
                listItem_choice.setId(jsonObject.getString( "id" ));
                listItem_choice.setQuest(jsonObject.getString( "quuestion" ));
                listItem_choice.setChoice11(jsonObject.getString( "answer1" ));
                listItem_choice.setChoice22(jsonObject.getString( "answer2" ));
                listItem_choice.setChoice33(jsonObject.getString( "answer3" ));
                listItem_choice.setChoice44(jsonObject.getString( "answer4" ));
                break;
            case 1:
                listItem_choice.setId(jsonObject.getString( "id" ));
                listItem_choice.setQuest(jsonObject.getString( "quuestion" ));
                listItem_choice.setChoice11(jsonObject.getString( "answer2" ));
                listItem_choice.setChoice22(jsonObject.getString( "answer3" ));
                listItem_choice.setChoice33(jsonObject.getString( "answer4" ));
                listItem_choice.setChoice44(jsonObject.getString( "answer1" ));
                break;
            case 2:
                listItem_choice.setId(jsonObject.getString( "id" ));
                listItem_choice.setQuest(jsonObject.getString( "quuestion" ));
                listItem_choice.setChoice11(jsonObject.getString( "answer3" ));
                listItem_choice.setChoice22(jsonObject.getString( "answer4" ));
                listItem_choice.setChoice33(jsonObject.getString( "answer1" ));
                listItem_choice.setChoice44(jsonObject.getString( "answer2" ));
                break;
            case 3:
                listItem_choice.setId(jsonObject.getString( "id" ));
                listItem_choice.setQuest(jsonObject.getString( "quuestion" ));
                listItem_choice.setChoice11(jsonObject.getString( "answer4" ));
                listItem_choice.setChoice22(jsonObject.getString( "answer3" ));
                listItem_choice.setChoice33(jsonObject.getString( "answer2" ));
                listItem_choice.setChoice44(jsonObject.getString( "answer1" ));
                break;
            default:
                Log.e( "ERROR QUESTION","NO NUMBER" );
        }
        return listItem_choice;
    }
}
