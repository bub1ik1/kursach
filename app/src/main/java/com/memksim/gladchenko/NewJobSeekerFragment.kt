package com.memksim.gladchenko

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Insert
import com.memksim.gladchenko.data.JobSeeker
import com.memksim.gladchenko.databinding.FragmentNewJobSeekerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewJobSeekerFragment : Fragment(R.layout.fragment_new_job_seeker) {

    @Inject
    lateinit var manager: DatabaseManager

    private var binding: FragmentNewJobSeekerBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            manager.getJobSeekers()
        }
        binding = FragmentNewJobSeekerBinding.bind(view)
        binding?.let {
            it.materialToolbar3.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            it.fab.setOnClickListener {
                doneButtonClicked()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun doneButtonClicked() {
        if (checkFields()) {
            lifecycleScope.launch {
                manager.saveNewJobSeeker(makeJobSeeker())
            }
            findNavController().navigateUp()
        }
    }

    private fun checkFields(): Boolean {
        binding?.let {
            if ((it.fullNameTiet.text?.isEmpty() == true) || (it.ageTiet.text?.isEmpty() == true) || (it.salaryTiet.text?.isEmpty() == true)) {
                Toast.makeText(context, "Заполнены не все обязательные поля", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
        }
        return true
    }

    private fun makeJobSeeker(): JobSeeker = JobSeeker(
        id = 0,
        fullName = binding?.fullNameTiet?.text.toString(),
        age = binding?.ageTiet?.text.toString().toInt(),
        skills = binding?.skillsTiet?.text.toString(),
        wantedSalary = binding?.salaryTiet?.text.toString().toDouble(),
        vacanciesIds = ""
    )

}