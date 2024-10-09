package com.ardacolak.fotografpaylasma

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ardacolak.fotografpaylasma.databinding.FragmentKullaniciBinding


class KullaniciFragment : Fragment() {

    private var _binding: FragmentKullaniciBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKullaniciBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.kayitButton.setOnClickListener {kayitOl(it) }
        binding.girisButton.setOnClickListener { girisYap(it) }

    }
    fun kayitOl(view:View){
        println("Kayit ol tiklandi")
    }
    fun girisYap(view: View){}



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}