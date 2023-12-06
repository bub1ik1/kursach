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
import com.memksim.gladchenko.data.HeadHunter
import com.memksim.gladchenko.data.Vacancy
import com.memksim.gladchenko.databinding.FragmentHeadHuntersListBinding
import com.memksim.gladchenko.databinding.FragmentVacanciesListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class HeadHuntersListFragment : Fragment(R.layout.fragment_head_hunters_list) {
    @Inject
    lateinit var manager: DatabaseManager

    private val adapter = HeadHuntersAdapter {
        val bundle = Bundle()
        bundle.putInt("HEAD_HUNTER_ID", it)
        bundle.putString("FROM_WHAT", "HEAD_HUNTERS")
        findNavController().navigate(
            R.id.action_headHuntersListFragment_to_vacanciesListFragment,
            bundle
        )
    }
    private var binding: FragmentHeadHuntersListBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHeadHuntersListBinding.bind(view)
        binding?.let {
            it.vacanciesRecyclerview.adapter = adapter
            it.vacanciesRecyclerview.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            it.fab.setOnClickListener {
                findNavController().navigate(R.id.action_headHuntersListFragment_to_newHeadHunterFragment)
            }

            it.materialToolbar2.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        runBlocking {
            adapter.items = manager.getHeadHunters()
        }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}

class HeadHuntersAdapter(
    private val doOnItemClicked: (Int) -> Unit
) : RecyclerView.Adapter<HeadHuntersAdapter.ViewHolder>() {

    var items: List<HeadHunter> = listOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(index: Int) {
            itemView.findViewById<TextView>(R.id.text).text = items[index].name
            itemView.setOnClickListener {
                doOnItemClicked(items[index].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind(position)

}