package uk.ac.shef.oak.com4510.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uk.ac.shef.oak.com4510.R
import uk.ac.shef.oak.com4510.databinding.JourneyInProgressBinding

class JourneyInProgressFragment : Fragment() {

    private var _binding: JourneyInProgressBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = JourneyInProgressBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.JourneyEnd.setOnClickListener {
            findNavController().navigate(R.id.action_JourneyInProgress_to_JourneyStart)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}