package com.memksim.gladchenko

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.memksim.gladchenko.data.JobSeeker
import com.memksim.gladchenko.databinding.FragmentJobSeekerInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class JobSeekerInfoFragment : Fragment(R.layout.fragment_job_seeker_info) {

    @Inject
    lateinit var manager: DatabaseManager

    private var binding: FragmentJobSeekerInfoBinding? = null

    private var jobSeeker: JobSeeker? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("jobSeekerId")
        runBlocking {
            jobSeeker = manager.getJobSeekerById(id ?: 0)
        }
        binding = FragmentJobSeekerInfoBinding.bind(view)
        binding?.let {
            Log.d("TEST", "onViewCreated: $jobSeeker")
            it.fullName.text = jobSeeker?.fullName
            it.age.text = jobSeeker?.age.toString()
            it.textView3.text = jobSeeker?.wantedSalary.toString()
            it.skills.text = jobSeeker?.skills

            it.materialToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            it.add.setOnClickListener {
                lifecycleScope.launch {
                    jobSeeker?.let {
                        manager.deleteJobSeeker(it)
                    }
                }
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}