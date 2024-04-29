package com.example.project20.ui.notifications;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project20.R;
import com.example.project20.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private ArrayList<NotificationList> noticeArrayList;
    private String[] noticeHeading;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //View view = null;
        //RecyclerView recyclerView= view.findViewById(R.id.notifications_recycler_view);

        //final TextView textView = binding.textNotifications;
        //notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();
        recyclerView=view.findViewById(R.id.notifications_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        NotifiRecyclerAdapter adapter= new NotifiRecyclerAdapter(getContext(),noticeArrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void dataInitialize() {
        noticeArrayList= new ArrayList<>();
        noticeHeading=new String[]{
                getString(R.string.First), 
                getString(R.string.Second),
                getString(R.string.Third),
                getString(R.string.Fourth),
                getString(R.string.Fifth),
                getString(R.string.Sixth),
                getString(R.string.Seventh),
                getString(R.string.Eighth),
                getString(R.string.Ninth),
                getString(R.string.Tenth),
                getString(R.string.Eleventh),
                getString(R.string.Twelveth),
                getString(R.string.Thirteenth),
                getString(R.string.Fourteenth),
                getString(R.string.Fifteenth),
                getString(R.string.Sixteenth),
                getString(R.string.Seventeenth),
        };
        for (String s : noticeHeading) {
            NotificationList nl = new NotificationList(s);
            noticeArrayList.add(nl);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}