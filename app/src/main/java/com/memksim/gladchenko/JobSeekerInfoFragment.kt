package com.memksim.gladchenko

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.memksim.gladchenko.databinding.FragmentJobSeekerInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JobSeekerInfoFragment : Fragment(R.layout.fragment_job_seeker_info) {

    private var binding: FragmentJobSeekerInfoBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentJobSeekerInfoBinding.bind(view)
        binding?.let {
            it.age.text = arguments?.getInt("jobSeekerId").toString()

            it.materialToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}