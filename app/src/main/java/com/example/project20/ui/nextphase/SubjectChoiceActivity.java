package com.example.project20.ui.nextphase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project20.R;

public class SubjectChoiceActivity extends AppCompatActivity {



    ImageButton physics,computer,maths;
    TextView pathTextSub;
    String selection,year,path;
    Bundle extras;
    Bundle extras2=new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subject_choice);

        init();
        pathTextSub.setText(path);
        onclick();



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
    }
    private void init()
    {
        Intent intent=getIntent();
        if (intent.getExtras()!=null)
           extras=intent.getExtras();
        physics=findViewById(R.id.physics);
        computer=findViewById(R.id.computer);
        maths=findViewById(R.id.maths);
        selection=extras.getString("Selection");
        year= extras.getString("Year");
        path=selection+"/"+year;
        pathTextSub=findViewById(R.id.pathTextSub);
    }

    void setClick(ImageButton button, String value)
    {
        Intent iNext=new Intent(this, DataDisplayActivity.class);
        if(selection!=null && year!=null)
        {extras2.putString("Selection",selection);
        extras2.putString("Year",year);}
        else {
            System.out.println("Either Selection or Year is null!");
            return;
        }
        button.setOnClickListener(v -> {

                extras2.putString("subject",value);
                    iNext.putExtras(extras2);
                    startActivity(iNext);

        });
    }

    private void onclick()
    {

        if(physics!=null)
        {
           setClick(physics,"physics") ;
        }
        if(computer!=null)
        {
            setClick(computer,"computer") ;
        }
        if(maths!=null)
        {
            setClick(maths,"maths") ;
        }
    }
}