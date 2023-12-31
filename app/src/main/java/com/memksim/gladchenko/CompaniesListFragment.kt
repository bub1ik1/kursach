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
import com.memksim.gladchenko.data.Company
import com.memksim.gladchenko.databinding.FragmentCompaniesListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class CompaniesListFragment : Fragment(R.layout.fragment_companies_list) {

    @Inject
    lateinit var manager: DatabaseManager

    private val adapter = CompaniesAdapter(
        doOnItemClicked = {
            val bundle = Bundle()
            bundle.putInt("COMPANY_ID", it)
            bundle.putString("FROM_WHAT", "COMPANIES")
            findNavController().navigate(
                R.id.action_companiesListFragment_to_vacanciesListFragment,
                bundle
            )
        },
        doOnItemLongClicked = { company ->
            lifecycleScope.launch {
                manager.deleteCompany(company)
            }

        }
    )
    private var binding: FragmentCompaniesListBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCompaniesListBinding.bind(view)
        binding?.let {
            it.vacanciesRecyclerview.adapter = adapter
            it.vacanciesRecyclerview.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            it.fab.setOnClickListener {
                findNavController().navigate(R.id.action_companiesListFragment_to_newCompanyFragment)
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
            adapter.items = manager.getCompanies()
        }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}

class CompaniesAdapter(
    private val doOnItemClicked: (Int) -> Unit,
    private val doOnItemLongClicked: (Company) -> Unit
) : RecyclerView.Adapter<CompaniesAdapter.ViewHolder>() {

    var items: List<Company> = listOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(index: Int) {
            itemView.findViewById<TextView>(R.id.text).text = items[index].name
            itemView.setOnClickListener {
                doOnItemClicked(items[index].id)
            }
            itemView.setOnLongClickListener {
                val company = items[index]
                val newList = items.toMutableList()
                newList.remove(company)
                items = newList
                notifyDataSetChanged()
                doOnItemLongClicked(company)
                true
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