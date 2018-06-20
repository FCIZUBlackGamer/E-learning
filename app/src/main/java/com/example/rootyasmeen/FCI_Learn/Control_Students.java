package com.example.rootyasmeen.FCI_Learn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by fci on 21/03/18.
 */

public class Control_Students extends Fragment {

    Button button,button1,button2,button3,button4,button5;
    Button dashborad;
    Spinner spinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.control_student,container,false );
        button = (Button)view.findViewById(R.id.math);
        button1 = (Button)view.findViewById(R.id.physics);
        button2 = (Button)view.findViewById(R.id.ob);
        button3 = (Button)view.findViewById(R.id.intro);
        button4 = (Button)view.findViewById(R.id.english);
        button5 = (Button)view.findViewById(R.id.statistics);
        dashborad = (Button)view.findViewById( R.id.get_files );
        spinner = (Spinner)view.findViewById( R.id.subject_title );
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        dashborad.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DashBoard.class);
                intent.putExtra("Subject",spinner.getSelectedItem().toString());
                startActivity(intent);
            }
        } );


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),View_posts.class);
                intent.putExtra("Subject","Math");
                startActivity(intent);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),View_posts.class);
                intent.putExtra("Subject","Physics");
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),View_posts.class);
                intent.putExtra("Subject","Organizational Behavior");
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),View_posts.class);
                intent.putExtra("Subject","Introduction to Computer");
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),View_posts.class);
                intent.putExtra("Subject","English");
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),View_posts.class);
                intent.putExtra("Subject","Statistics");
                startActivity(intent);
            }
        });

    }

}
