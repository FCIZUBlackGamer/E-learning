package com.example.rootyasmeen.FCI_Learn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ExamInfo extends AppCompatActivity {

    TextView math, eng, phy, stat, ob, intro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        math = (TextView)findViewById(R.id.matham);
        eng = (TextView)findViewById(R.id.eng);
        phy = (TextView)findViewById(R.id.phys);
        stat = (TextView)findViewById(R.id.sta);
        ob = (TextView)findViewById(R.id.ob);
        intro = (TextView)findViewById(R.id.intr);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://yasmeenosama30.000webhostapp.com/GetExamTime.php",
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
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int x=0; x<jsonArray.length(); x++){
                                JSONObject object = jsonArray.getJSONObject(x);
                                if (object.getString("sub_name").equals("Organizational Behavior")){
                                    ob.setText("Start Time: "+object.getString("sub_set_time")+"\n"+"Finish: "+object.getString("sub_end_time"));
                                }else if (object.getString("sub_name").equals("English")){
                                    eng.setText("Start Time: "+object.getString("sub_set_time")+"\n"+"Finish: "+object.getString("sub_end_time"));
                                }else if (object.getString("sub_name").equals("Physics")){
                                    phy.setText("Start Time: "+object.getString("sub_set_time")+"\n"+"Finish: "+object.getString("sub_end_time"));
                                }else if (object.getString("sub_name").equals("Introduction to Computer")){
                                    intro.setText("Start Time: "+object.getString("sub_set_time")+"\n"+"Finish: "+object.getString("sub_end_time"));
                                }else if (object.getString("sub_name").equals("Statistics")){
                                    stat.setText("Start Time: "+object.getString("sub_set_time")+"\n"+"Finish: "+object.getString("sub_end_time"));
                                }else if (object.getString("sub_name").equals("Mathematics")){
                                    math.setText("Start Time: "+object.getString("sub_set_time")+"\n"+"Finish: "+object.getString("sub_end_time"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof ServerError)
                    Toast.makeText( ExamInfo.this, "Server Error", Toast.LENGTH_SHORT ).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText( ExamInfo.this, "Connection Timed Out", Toast.LENGTH_SHORT ).show();
                else if (error instanceof NetworkError)
                    Toast.makeText( ExamInfo.this, "Bad Network Connection", Toast.LENGTH_SHORT ).show();
            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(ExamInfo.this,settings.class));
            return true;
        }else if (id == R.id.info) {
            startActivity(new Intent(ExamInfo.this,ExamInfo.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
