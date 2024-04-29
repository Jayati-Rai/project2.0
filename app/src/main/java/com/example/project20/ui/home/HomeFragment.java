package com.example.project20.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project20.R;
import com.example.project20.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    WebView webView;
    ProgressBar pgbar;
    private FragmentHomeBinding binding;
    public HomeFragment()
    {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setview(root);
        //onBackPressed();
        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }



    private void setview(View root) {
        webView=root.findViewById(R.id.webview);
        pgbar=root.findViewById(R.id.pgbar);
        webView.loadUrl("https://ecc.ac.in/");
        webView.setWebViewClient(new WebViewClient());


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}