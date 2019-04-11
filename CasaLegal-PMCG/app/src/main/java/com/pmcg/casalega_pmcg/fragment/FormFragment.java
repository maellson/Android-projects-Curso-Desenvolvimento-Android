package com.pmcg.casalega_pmcg.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pmcg.casalega_pmcg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormFragment extends Fragment {


    public FormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String url ="https://docs.google.com/forms/d/e/1FAIpQLSeMtBPC2MlmSc75l8finxzeGD8REf4OOoI5yLahAsy7xtmYag/viewform";

        View v = inflater.inflate(R.layout.fragment_form, container, false);
        WebView webView = (WebView)v.findViewById(R.id.webViewForm);
        webView.getSettings().setJavaScriptEnabled(true);// habilitado javaScript
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        // Inflate the layout for this fragment
        return v;
    }



}
