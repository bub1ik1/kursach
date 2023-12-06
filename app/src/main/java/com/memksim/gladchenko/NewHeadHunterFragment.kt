package com.memksim.gladchenko

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.memksim.cursach.data.Company
import com.memksim.gladchenko.data.HeadHunter
import com.memksim.gladchenko.databinding.FragmentNewHeadHunterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class NewHeadHunterFragment : Fragment(R.layout.fragment_new_head_hunter) {

    @Inject
    lateinit var manager: DatabaseManager

    private var binding: FragmentNewHeadHunterBinding? = null

    private var selectedCompany: Company? = null
    private val adapter = CompaniesAdapter {
        selectedCompany = it
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewHeadHunterBinding.bind(view)
        binding?.let {
            it.fab.setOnClickListener { _ ->
                if (selectedCompany != null
                    && it.nameTiet.text?.isNotEmpty() == true
                    && it.phoneTiet.text?.isNotEmpty() == true
                ) {
                    kotlin.runCatching {
                        lifecycleScope.launch {
                            manager.saveNewHeadHunter(
                                HeadHunter(
                                    id = 0,
                                    name = it.nameTiet.text.toString(),
                                    companyId = selectedCompany!!.id,
                                    contactPhone = it.phoneTiet.text.toString(),
                                    vacanciesIds = ""
                                )
                            )
                        }
                    }.onSuccess {
                        Toast.makeText(context, "Запись сохранена", Toast.LENGTH_SHORT).show()
                    }
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(
                        context,
                        "Выберите компанию и заполните обязательные поля",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            it.materialToolbar3.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            it.companiesList.adapter = adapter
            it.companiesList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onResume() {
        super.onResume()
        runBlocking {
            adapter.items = manager.getCompanies()
        }
        adapter.notifyDataSetChanged()
    }

    inner class CompaniesAdapter(
        private val doOnItemClicked: (Company) -> Unit
    ) : RecyclerView.Adapter<CompaniesAdapter.ViewHolder>() {

        var items: List<Company> = listOf()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun onBind(index: Int) {
                itemView.findViewById<TextView>(R.id.text).text = items[index].name
                itemView.setOnClickListener {
                    doOnItemClicked(items[index])
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
}