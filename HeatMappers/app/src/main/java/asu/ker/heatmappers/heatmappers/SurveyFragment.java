package asu.ker.heatmappers.heatmappers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class SurveyFragment extends Fragment {
    WebView webView;
    @Override
    public  void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstance)
    {
        View v=layoutInflater.inflate(R.layout.survey, viewGroup, false);
        webView = (WebView) v.findViewById(R.id.surveyform);
        webView.loadUrl("https://asu.co1.qualtrics.com/jfe/form/SV_eCESSriycGWGTk1");

        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        webView.setWebViewClient(new WebViewClient());

        return v;
    }
}
