package com.example.rootyasmeen.FCI_Learn;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_as extends AppCompatActivity {


    Spinner spinner;
    EditText editText ;
    ProgressDialog progressDialog;
    String id = "";
    String type;
    Button login;
    ResultDatabase resultDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        resultDatabase = new ResultDatabase(this);
        spinner = findViewById(R.id.user);
        editText = findViewById(R.id.getid);
        login = findViewById(R.id.button2);
        editText.setHint("Enter Id");
        editText.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
        editText.setTextColor(getResources().getColor(R.color.cardview_light_background));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = spinner.getSelectedItem().toString();
                id = editText.getText().toString();
                if (type.equals("Instructor")){
                    checkIn(id,"https://yasmeenosama30.000webhostapp.com/LoginIns.php", "Instructor");
                }else {
                    checkIn(id,"https://yasmeenosama30.000webhostapp.com/LoginUser.php", "Student");
                }
            }
        });

    }

    public void checkIn(final String id, String url, final String type){

        StringRequest stringRequest = new StringRequest( Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
//                            String s = URLEncoder.encode(response,"ISO-8859-1");
//                            response = URLDecoder.decode(s,"UTF-8");
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("response");
                            if (result.equals("Welcome Home!")){
                                resultDatabase.InsertData(id,"1");
                                if (type.equals("Instructor")){
                                    Intent intent = new Intent(Login_as.this, Switch_Instructor.class);
                                    startActivity(intent);
                                }else{
                                    Intent intent = new Intent(Login_as.this, Switch_Student.class);
                                    startActivity(intent);
                                }
                            }else{
                                Toast.makeText(Login_as.this,"Id Not Found",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }/*catch (UnsupportedEncodingException e){
                            e.printStackTrace();
                        }*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof ServerError)
                    Toast.makeText( Login_as.this, "Server Error", Toast.LENGTH_SHORT ).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText( Login_as.this, "Connection Timed Out", Toast.LENGTH_SHORT ).show();
                else if (error instanceof NetworkError)
                    Toast.makeText( Login_as.this, "Bad Network Connection", Toast.LENGTH_SHORT ).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("id",id);
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}








