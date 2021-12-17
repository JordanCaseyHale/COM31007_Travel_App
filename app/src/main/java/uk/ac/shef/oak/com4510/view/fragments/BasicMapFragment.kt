package uk.ac.shef.oak.com4510.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uk.ac.shef.oak.com4510.databinding.MapBinding
import uk.ac.shef.oak.com4510.view.BasicMapActivity

/**
 * Class contains the fragment for Basic Map
 */
class BasicMapFragment  : Fragment() {

    private var _binding: MapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Initialisiation method.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val intent = Intent (activity, BasicMapActivity::class.java)
        requireActivity().startActivity(intent)

        _binding = MapBinding.inflate(inflater, container, false)
        return binding.root

    }

    // View Created method:
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}