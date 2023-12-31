package com.memksim.gladchenko

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.memksim.gladchenko.data.JobSeeker
import com.memksim.gladchenko.databinding.FragmentJobSeekersListBinding
import com.memksim.gladchenko.databinding.ItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class JobSeekersListFragment : Fragment(R.layout.fragment_job_seekers_list) {

    @Inject
    lateinit var manager: DatabaseManager

    private var binding: FragmentJobSeekersListBinding? = null

    private val adapter = JobSeekersAdapter { id ->
        val bundle = Bundle()
        bundle.putInt("jobSeekerId", id)
        findNavController().navigate(
            R.id.action_jobSeekersListFragment_to_jobSeekerInfoFragment,
            bundle
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentJobSeekersListBinding.bind(view)
        binding?.let {
            it.jobSeekersRecyclerview.adapter = adapter
            it.jobSeekersRecyclerview.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            it.fab.setOnClickListener {
                findNavController().navigate(R.id.action_jobSeekersListFragment_to_newJobSeekerFragment)
            }

            it.materialToolbar2.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            adapter.jobSeekers = manager.getJobSeekers()
        }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}

class JobSeekersAdapter(
    private val doOnItemClicked: (Int) -> Unit
) : Adapter<JobSeekersAdapter.ViewHolder>() {

    var jobSeekers: List<JobSeeker> = listOf()
        set(value) {
            field = value
            Log.d("TEST", "set: $value")
            notifyDataSetChanged()
        }

    inner class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(index: Int) {
            Log.d("TEST", "onBind: ${jobSeekers[index].fullName}")
            binding.text.text = jobSeekers[index].fullName
            itemView.setOnClickListener {
                doOnItemClicked(jobSeekers[index].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = jobSeekers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind(position)

}