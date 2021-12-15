package com.example.mobilesoftwareassignment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobilesoftwareassignment.R
import com.example.mobilesoftwareassignment.databinding.JourneyStartBinding
import com.example.mobilesoftwareassignment.viewmodel.MainViewModel

class JourneyStartFragment : Fragment() {

    private var _binding: JourneyStartBinding? = null
    private var MainViewModel: MainViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = JourneyStartBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.JourneyStart.setOnClickListener {
            findNavController().navigate(R.id.action_JourneyStart_to_JourneyInProgress)
        }

        //this.MainViewModel = ViewModelProvider(this)[MainViewModel!!::class.java]
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}