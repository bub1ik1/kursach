package com.memksim.gladchenko

import android.annotation.SuppressLint
import android.os.Bundle
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
        manager
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(index: Int) {
            itemView.findViewById<TextView>(R.id.text).text = jobSeekers[index].fullName
            itemView.setOnClickListener {
                doOnItemClicked(index)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = jobSeekers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind(position)

}