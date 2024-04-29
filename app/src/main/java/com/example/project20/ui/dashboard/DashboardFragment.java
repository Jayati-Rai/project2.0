package com.example.project20.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project20.R;
import com.example.project20.databinding.FragmentDashboardBinding;
import com.example.project20.ui.nextphase.SubjectChoiceActivity;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    ImageButton button_1st,button_2nd,button_3rd;
    Bundle extras=new Bundle();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       //this is to inflate the current layout
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        onclick(root);

        //final TextView textView = binding.textDashboard;
        //dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }



    private void init(@NonNull View view) {
        button_1st=view.findViewById(R.id.pyqs_imgbutton1);
        button_2nd=view.findViewById(R.id.pyqs_imgbutton2);
        button_3rd=view.findViewById(R.id.pyqs_imgbutton3);
    }

    private void setclick(ImageButton button,String value)
    {
        Intent iNext=new Intent(getActivity(), SubjectChoiceActivity.class);
        if(button!=null)
        {
            button.setOnClickListener(v -> {
                extras.putString("Year",value);
                iNext.putExtras(extras);
                startActivity(iNext);
            });
        }
    }
    private void onclick(@NonNull View view)
    {
        extras.putString("Selection","pyqs");
        init(view);


        if(button_1st!=null) {
            setclick(button_1st,"1st");
        }
        if(button_2nd!=null){
            setclick(button_2nd,"2nd");
        }
        if(button_3rd!=null) {
            setclick(button_3rd,"3rd");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}