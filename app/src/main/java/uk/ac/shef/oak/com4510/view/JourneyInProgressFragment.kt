package uk.ac.shef.oak.com4510.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uk.ac.shef.oak.com4510.MapsActivity
import uk.ac.shef.oak.com4510.R
import uk.ac.shef.oak.com4510.databinding.ActivityMapsBinding

class JourneyInProgressFragment : Fragment() {

    private var _binding: ActivityMapsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val intent = Intent (activity, MapsActivity::class.java)
        requireActivity().startActivity(intent)

        _binding = ActivityMapsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}