package com.example.rootyasmeen.FCI_Learn;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class settings extends AppCompatActivity {

    Button logout, change_pass;
    EditText new_pass, con_pass;
    LinearLayout linearLayout;
    ResultDatabase resultDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity__settings );
        logout = (Button)findViewById( R.id.logout );
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        resultDatabase = new ResultDatabase(this);
        change_pass = (Button)findViewById( R.id.change_pass );
        logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity( new Intent( settings.this,Login_as.class ) );
                //settings.super.onDestroy();
                resultDatabase.UpdateData("1","0");
                Intent intent = new Intent(settings.this, Login_as.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } );


        change_pass.setOnClickListener( new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                linearLayout = new LinearLayout( settings.this );
                linearLayout.setOrientation( LinearLayout.VERTICAL );
                new_pass = new EditText( settings.this );
                new_pass.setHint( "Enter New Password" );
                new_pass.setHintTextColor( getColor(R.color.colorAccent) );
                con_pass = new EditText( settings.this );
                con_pass.setHint( "Confirm Password" );
                con_pass.setHintTextColor( getColor(R.color.colorAccent) );
                linearLayout.addView( new_pass );
                linearLayout.addView( con_pass );
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(settings.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(settings.this);
                }
                builder.setTitle("Change Password!")
                        .setView( linearLayout )
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (con_pass.getText().toString().equals( new_pass.getText().toString() )){
                                    Toast.makeText( settings.this,"Correct Password!",Toast.LENGTH_SHORT ).show();
                                }else {
                                    Toast.makeText( settings.this,"Password Doesn't Match!",Toast.LENGTH_SHORT ).show();
                                }
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
        } );
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(settings.this,settings.class));
            return true;
        }else if (id == R.id.info) {
            startActivity(new Intent(settings.this,ExamInfo.class));
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
