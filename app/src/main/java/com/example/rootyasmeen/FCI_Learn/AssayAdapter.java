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
import android.widget.EditText;
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

public class AssayAdapter extends RecyclerView.Adapter<AssayAdapter.ViewHolder2> {
    private List<ListItem_Assay> listItems2;
    private Context context2;
    String Keyword;
    ResultDatabase resultDatabase;

    public AssayAdapter(List<ListItem_Assay> listItems2, Context context2, String keyword) {
        this.listItems2 = listItems2;
        this.context2 = context2;
        this.Keyword = keyword;
    }

    @Override
    public ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v2 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item2,parent,false);
        resultDatabase = new ResultDatabase( context2 );
        return new AssayAdapter.ViewHolder2(v2);

    }

    @Override
    public void onBindViewHolder(final ViewHolder2 holder, int position) {
        final ListItem_Assay listItemAssay =listItems2.get(position);
        holder.ques.setText(listItemAssay.getQuest3());
        holder.id.setText(listItemAssay.getId());
        holder.sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    StringRequest stringRequest = new StringRequest( Request.Method.POST, Keyword,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        String s = URLEncoder.encode(response,"ISO-8859-1");
                                        response = URLDecoder.decode(s,"UTF-8");
                                    }catch (UnsupportedEncodingException e){
                                        e.printStackTrace();
                                    }
                                    //progressDialog.dismiss();
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("assay_questions_data");
                                        for (int i=0; i<jsonArray.length(); i++){
                                            JSONObject object = jsonArray.getJSONObject(i);
                                            String s = holder.ans.getText().toString();
                                            final String [] S_A = s.split( "\n" );
                                            final String [] I_A = object.getString("keywords").split( "\n" );
                                            int score = 0;
                                            for (int r = 0; r < S_A.length; r++) {
                                                for (int t = 0; t< I_A.length; t++) {
                                                    if (S_A[r].contains( I_A[t] )) {
                                                        score += 1;
                                                    }
                                                }
                                            }
                                            resultDatabase.InsertAnswer( listItemAssay.getQuest3(),holder.ans.getText().toString(),score+"" );
                                            holder.ques.setText("Thanks!");
                                            holder.ans.setText("");
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
                                Toast.makeText( context2, "Server Error", Toast.LENGTH_SHORT ).show();
                            else if (error instanceof TimeoutError)
                                Toast.makeText( context2, "Connection Timed Out", Toast.LENGTH_SHORT ).show();
                            else if (error instanceof NetworkError)
                                Toast.makeText( context2, "Bad Network Connection", Toast.LENGTH_SHORT ).show();                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap hashMap = new HashMap();
                            hashMap.put("type","assay");
                            hashMap.put("id",listItemAssay.getId());
                            return hashMap;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(context2);
                    requestQueue.add(stringRequest);

                }else {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context2, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context2);
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
        ConnectivityManager cm = (ConnectivityManager) context2.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    @Override
    public int getItemCount() {
        return listItems2.size();
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder{
        public EditText ans;
        TextView id;
        TextView ques;
        Button sure;


        public ViewHolder2(View itemView) {
            super(itemView);

            ans=(EditText)itemView.findViewById(R.id.answer3);
            id=(TextView) itemView.findViewById(R.id.num_ques_assay);
            ques=(TextView) itemView.findViewById(R.id.question3);
            sure=(Button) itemView.findViewById(R.id.sure_assay);
        }
    }
}
