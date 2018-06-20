package com.example.rootyasmeen.FCI_Learn;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rootyasmeen on 29/01/18.
 */

public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.ViewHolder1> {
    private List<ListItem_Choice> listItems1;
    private Context context1;
    ResultDatabase resultDatabase;
    String answer;

    public ChoiceAdapter(List<ListItem_Choice> listItems1, Context context1, String answer) {
        this.listItems1 = listItems1;
        this.context1 = context1;
        this.answer = answer;
    }
    @Override
    public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v1 =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item1,parent,false);
        resultDatabase = new ResultDatabase( context1 );
        return new ViewHolder1(v1);
    }

    @Override
    public void onBindViewHolder(final ViewHolder1 holder, int position) {
        final ListItem_Choice listItem_choice = listItems1.get(position);
        holder.id.setText(listItem_choice.getId());
        holder.quest2.setText(listItem_choice.getQuest());
        holder.choice11.setText(listItem_choice.getChoice11());
        holder.choice22.setText(listItem_choice.getChoice22());
        holder.choice33.setText(listItem_choice.getChoice33());
        holder.choice44.setText(listItem_choice.getChoice44());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    int radioButtonID = holder.radioGroup.getCheckedRadioButtonId();
                    View radioButton = holder.radioGroup.findViewById( radioButtonID );
                    int idx = holder.radioGroup.indexOfChild( radioButton );
                    RadioButton r = (RadioButton) holder.radioGroup.getChildAt( idx );
                    final String selectedtext = r.getText().toString();
                    holder.button.setEnabled( false );

                    ///Get Correct Answer From Json
                    StringRequest stringRequest = new StringRequest( Request.Method.POST, answer,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("choice_questions_data");
                                        for (int i=0; i<jsonArray.length(); i++){
                                            JSONObject object = jsonArray.getJSONObject(i);
                                            //Calculate Result
                                            if (object.getString("answer1").equals(selectedtext)){
                                                resultDatabase.InsertAnswer( listItem_choice.getQuest(),selectedtext,"1" );
                                            }else {
                                                resultDatabase.InsertAnswer( listItem_choice.getQuest(),selectedtext,"0" );
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //progressDialog.dismiss();
                            if (error instanceof ServerError)
                                Toast.makeText( context1, "Server Error", Toast.LENGTH_SHORT ).show();
                            else if (error instanceof TimeoutError)
                                Toast.makeText( context1, "Connection Timed Out", Toast.LENGTH_SHORT ).show();
                            else if (error instanceof NetworkError)
                                Toast.makeText( context1, "Bad Network Connection", Toast.LENGTH_SHORT ).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap hashMap = new HashMap();
                            hashMap.put("type","choice");
                            hashMap.put("id",listItem_choice.getId());
                            return hashMap;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(context1);
                    requestQueue.add(stringRequest);

                    holder.quest2.setText( "Thanks For Answer!" );
                    holder.choice11.setText( "" );
                    holder.choice22.setText( "" );
                    holder.choice33.setText( "" );
                    holder.choice44.setText( "" );
                }else {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context1, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context1);
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
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context1.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public int getItemCount() {
        return listItems1.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder{
        public TextView id;
        public TextView quest2;
        public RadioButton choice11;
        public RadioButton choice22;
        public RadioButton choice33;
        public RadioButton choice44;
        RadioGroup radioGroup;
        Button button;

        public ViewHolder1(View itemView) {
            super(itemView);
            id = (TextView)itemView.findViewById( R.id.num_ques_choice );
            quest2=(TextView) itemView.findViewById(R.id.question1);
            choice11=(RadioButton) itemView.findViewById(R.id.choice1);
            choice22=(RadioButton) itemView.findViewById(R.id.choice2);
            choice33=(RadioButton) itemView.findViewById(R.id.choice3);
            choice44=(RadioButton) itemView.findViewById(R.id.choice4);
            radioGroup=(RadioGroup) itemView.findViewById(R.id.stud_choice);
            button=(Button) itemView.findViewById(R.id.sure_choice);


        }
    }

}
