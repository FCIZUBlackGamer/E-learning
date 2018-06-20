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

public class TFAdapter extends RecyclerView.Adapter<TFAdapter.ViewHolder>{
    private List<ListItem_TF> listItemTFS;
    private Context context;
    String Subject;
    ResultDatabase resultDatabase;

    public TFAdapter(List<ListItem_TF> listItemTFS, Context context, String Subject) {
        this.listItemTFS = listItemTFS;
        this.context = context;
        this.Subject = Subject;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        resultDatabase = new ResultDatabase( context );
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ListItem_TF listItemTF = listItemTFS.get(position);
        holder.id.setText(listItemTF.getId());
        holder.ques.setText(listItemTF.getQuestion());
        holder.true11.setText(listItemTF.getTrue11());
        holder.false11.setText(listItemTF.getFalse11());
        holder.sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    int radioButtonID = holder.radioGroup.getCheckedRadioButtonId();
                    View radioButton = holder.radioGroup.findViewById( radioButtonID );
                    int idx = holder.radioGroup.indexOfChild( radioButton );
                    RadioButton r = (RadioButton) holder.radioGroup.getChildAt( idx );
                    final String selectedtext = r.getText().toString();
                    holder.sure.setEnabled( false );
                    //Get Correct Answer From Json
                    StringRequest stringRequest = new StringRequest( Request.Method.POST, Subject,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        String s = URLEncoder.encode(response,"ISO-8859-1");
                                        response = URLDecoder.decode(s,"UTF-8");
                                    }catch (UnsupportedEncodingException e){
                                        e.printStackTrace();
                                    }

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("tf_questions_data");
                                        for (int i=0; i<jsonArray.length(); i++){
                                            JSONObject object = jsonArray.getJSONObject(i);
                                            //Calculate Result
                                            if (object.getString("answer").equals(selectedtext)){
                                                resultDatabase.InsertAnswer( listItemTF.getQuestion(),selectedtext,"1" );
                                            }else {
                                                resultDatabase.InsertAnswer( listItemTF.getQuestion(),selectedtext,"0" );
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
                                Toast.makeText( context, "Server Error", Toast.LENGTH_SHORT ).show();
                            else if (error instanceof TimeoutError)
                                Toast.makeText( context, "Connection Timed Out", Toast.LENGTH_SHORT ).show();
                            else if (error instanceof NetworkError)
                                Toast.makeText( context, "Bad Network Connection", Toast.LENGTH_SHORT ).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap hashMap = new HashMap();
                            hashMap.put("type","tf");
                            hashMap.put("id",listItemTF.getId());
                            return hashMap;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);

                    holder.ques.setText( "Thanks For Answer!" );
                    holder.true11.setText( "" );
                    holder.false11.setText( "" );
                }else {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context);
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
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public int getItemCount() {
        return listItemTFS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public RadioButton true11;
        public RadioButton false11;
        TextView id, ques;
        Button sure;
        RadioGroup radioGroup;

        public ViewHolder(View itemView) {
            super(itemView);
            id =(TextView)itemView.findViewById(R.id.num_ques_tf);
            ques =(TextView)itemView.findViewById(R.id.question);
            true11=(RadioButton)itemView.findViewById(R.id.true1);
            false11=(RadioButton)itemView.findViewById(R.id.false1);
            sure=(Button) itemView.findViewById(R.id.sure_tf);
            radioGroup = (RadioGroup)itemView.findViewById(R.id.stud_tf);

        }
    }


}
