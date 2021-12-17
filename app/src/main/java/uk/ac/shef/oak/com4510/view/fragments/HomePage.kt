package uk.ac.shef.oak.com4510.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uk.ac.shef.oak.com4510.R
import uk.ac.shef.oak.com4510.databinding.HomePageBinding

/**
 * Fragment Class for the HomePage
 */
class HomePage : Fragment() {

    private var _binding: HomePageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Initialisation Methods.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = HomePageBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homePageJourneyButton.setOnClickListener {
            findNavController().navigate(R.id.action_HomePage_to_JourneyStart)
        }
        binding.homePagePicturePreview.setOnClickListener {
            findNavController().navigate(R.id.action_HomePage_to_BrowsePreviews)
        }
        binding.homePageMapHistory.setOnClickListener {
            findNavController().navigate(R.id.action_HomePage_to_MapHistory)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}