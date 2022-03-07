package com.example.a12.ui.web

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a12.R
import com.example.a12.databinding.FragmentWebBinding

class WebFragment : Fragment() {

    private var _binding: FragmentWebBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebBinding.inflate(inflater, container, false)

        return binding.root
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val webView = binding.webView
        val webViewClient = MyWebViewClient()
        webView.webViewClient = webViewClient
        // включаем поддержку JavaScript
        webView.settings.javaScriptEnabled = true
        // указываем страницу загрузки
        webView.loadUrl("https://www.google.com")

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.webView.canGoBack()){
                    webView.goBack();
                }else{
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        })


        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}