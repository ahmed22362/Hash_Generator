package com.example.hashapp

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hashapp.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        binding.generateBtn.setOnClickListener {
            onGenerateClicked()

        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu , menu)

}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==R.id.clear_lbl){
            text_et.text.clear()
            showSnackBar("Cleared.")
            return true
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        val hashAlgorithms = resources.getStringArray(R.array.hash_algorithms)
        val arrayAdapter = ArrayAdapter(requireContext() , R.layout.drop_down_item , hashAlgorithms)
        binding.autoCompleteTv.setAdapter(arrayAdapter)
    }



    private fun onGenerateClicked(){
        if(TextUtils.isEmpty(text_et.text.toString().trim{it<= ' '})){
            showSnackBar("Enter Text please")
        }else{
            lifecycleScope.launch {
                applyAnimation()
                navToSuccess(getHashedData())
            }
        }
    }

    private fun getHashedData(): String{
        val algorithm = autoComplete_tv.text.toString()
        val text = text_et.text.toString()
        return homeViewModel.getHash(text , algorithm)
    }

    private suspend fun applyAnimation(){
        binding.generateBtn.isClickable = false
        binding.titleTv.animate().alpha(0f).duration = 400L
        binding.generateBtn.animate().alpha(0f).duration = 400L
        binding.textInputLayout.animate().translationXBy(1200f).duration = 400L
        binding.textEt.animate().translationXBy(-1200f).duration = 400L

        delay(300L)

        binding.homeSuccessBackground.animate().alpha(1f).duration = 600L
        binding.homeSuccessBackground.animate().rotationBy(720f).duration = 600L
        binding.homeSuccessBackground.animate().scaleXBy(720f).duration = 800L
        binding.homeSuccessBackground.animate().scaleYBy(720f).duration = 800L

        delay(500L)

        binding.homeSuccessIv.animate().alpha(1f).duration = 1000L

        delay(1000L)
    }

    private fun navToSuccess(hashedText: String){
        val directions = HomeFragmentDirections.actionHomeFragmentToSuccessFragment(hashedText)
        findNavController().navigate(directions)
    }

    private fun showSnackBar(message: String){
        val snackBar  = Snackbar.make(content , message, Snackbar.LENGTH_SHORT)
        snackBar.setAction("okay"){}
        snackBar.setActionTextColor(ContextCompat.getColor(requireContext() , R.color.blue))
        snackBar.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}