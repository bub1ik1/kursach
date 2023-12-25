package com.memksim.gladchenko

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.memksim.gladchenko.data.Vacancy
import com.memksim.gladchenko.databinding.FragmentVacanciesListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class VacanciesListFragment : Fragment(R.layout.fragment_vacancies_list) {

    @Inject
    lateinit var manager: DatabaseManager

    private val adapter = VacanciesAdapter {
        val bundle = Bundle()
        bundle.putInt("VACANCY_ID", it)
        findNavController().navigate(
            R.id.action_vacanciesListFragment_to_vacancyInfoFragment,
            bundle
        )
    }
    private var binding: FragmentVacanciesListBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVacanciesListBinding.bind(view)
        binding?.let {
            it.vacanciesRecyclerview.adapter = adapter
            it.vacanciesRecyclerview.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            Log.d("CHMO", "onViewCreated: from what = ${arguments?.getString("FROM_WHAT")}")

            if (arguments?.getString("FROM_WHAT") == "JOB_SEEKER_INFO") {
                it.fab.isVisible = false
                it.materialToolbar2.title = "Подходящие вакансии"
            }

            it.fab.setOnClickListener {
                val bundle = Bundle()
                if (arguments?.getString("FROM_WHAT").equals("HEAD_HUNTERS")) {
                    runBlocking {
                        bundle.putInt(
                            "COMPANY_ID",
                            manager.getHeadHunterById(
                                arguments?.getInt("HEAD_HUNTER_ID") ?: 0
                            ).companyId
                        )
                    }
                } else {
                    bundle.putInt("COMPANY_ID", arguments?.getInt("COMPANY_ID") ?: 0)
                }
                findNavController().navigate(
                    R.id.action_vacanciesListFragment_to_newVacancyFragment,
                    bundle
                )
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
            when (arguments?.getString("FROM_WHAT")) {
                "HEAD_HUNTERS" -> {
                    adapter.items =
                        manager.getVacanciesByCompany(arguments?.getInt("HEAD_HUNTER_ID") ?: 0)
                }

                "COMPANIES" -> {
                    adapter.items =
                        manager.getVacanciesByCompany(arguments?.getInt("COMPANY_ID") ?: 0)
                }

                "JOB_SEEKER_INFO" -> {
                    val skills = arguments?.getString("SKILLS")?.split(' ').orEmpty()
                    adapter.items = manager.getVacanciesBySkills(skills)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}

class VacanciesAdapter(
    private val doOnItemClicked: (Int) -> Unit
) : RecyclerView.Adapter<VacanciesAdapter.ViewHolder>() {

    var items: List<Vacancy> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(index: Int) {
            itemView.findViewById<TextView>(R.id.text).text = items[index].title
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