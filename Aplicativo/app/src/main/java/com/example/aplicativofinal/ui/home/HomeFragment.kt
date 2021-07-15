package com.example.aplicativofinal.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.aplicativofinal.R
import com.example.aplicativofinal.RunAudioActivity
import com.example.aplicativofinal.databinding.FragmentHomeBinding

/** Esta parte corresponde à tela de inicial(home). Nenhuma alteração foi feita nesse  fragment,
 * portanto toda a programação aqui é padrão do Android Studio e serve apenas para quesito de
 * layout, toda a parte lógica foi implementada no arquivo MainActivity.kt */

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.searchBar
        //homeViewModel.text.observe(viewLifecycleOwner, Observer {
           // textView.text = it
        //})

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}