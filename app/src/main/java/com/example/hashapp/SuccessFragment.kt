package com.example.hashapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hashapp.databinding.FragmentSuccessBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SuccessFragment : Fragment() {

    private val args: SuccessFragmentArgs by navArgs()
    private var _binding: FragmentSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuccessBinding.inflate(inflater, container, false)

        binding.hashTv.text = args.hashed

        binding.copyBtn.setOnClickListener {
            onCopyClicked()
        }


        Log.e("TAG", "onCreateView: "+ args.hashed )

        return binding.root
    }

    private fun onCopyClicked(){
       lifecycleScope.launch {
           applyAnimation()
           copyToCliPBord(args.hashed)
       }
    }

    private fun copyToCliPBord(hash: String) {
        val clipboardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Encrypted Text" , hash)
        clipboardManager.setPrimaryClip(clipData)
    }


    private suspend fun applyAnimation(){
        binding.include.messageBackground.animate().translationY(80f).duration = 200L
        binding.include.messageTextView.animate().translationY(80f).duration = 200L
        delay(2000L)
        binding.include.messageBackground.animate().translationY(-80f).duration = 500L
        binding.include.messageTextView.animate().translationY(-80f).duration = 500L
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}