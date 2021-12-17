package uk.ac.shef.oak.com4510.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uk.ac.shef.oak.com4510.databinding.BrowsePreviewsBinding
import uk.ac.shef.oak.com4510.view.BrowseActivity

/**
 * Class for Browsing Previews
 */
class BrowsePreviewsFragment : Fragment() {
    private var _binding: BrowsePreviewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Initialision methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val intent = Intent (activity, BrowseActivity::class.java)
        requireActivity().startActivity(intent)

        _binding = BrowsePreviewsBinding.inflate(inflater, container, false)
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