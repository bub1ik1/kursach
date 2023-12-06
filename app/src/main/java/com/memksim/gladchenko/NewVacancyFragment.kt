package com.memksim.gladchenko

import android.annotation.SuppressLint
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
import com.memksim.gladchenko.data.HeadHunter
import com.memksim.gladchenko.data.Vacancy
import com.memksim.gladchenko.databinding.FragmentNewVacancyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewVacancyFragment : Fragment(R.layout.fragment_new_vacancy) {

    @Inject
    lateinit var manager: DatabaseManager

    private var binding: FragmentNewVacancyBinding? = null

    private var selectedHeadHunter: HeadHunter? = null
    private val adapter = HeadHuntersAdapter {
        selectedHeadHunter = it
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewVacancyBinding.bind(view)
        binding?.let { binding ->
            binding.fab.setOnClickListener {
                if (selectedHeadHunter != null
                    && binding.nameTiet.text?.isNotEmpty() == true
                    && binding.salaryTiet.text?.isNotEmpty() == true
                    && binding.skillsTiet.text?.isNotEmpty() == true
                ) {
                    lifecycleScope.launch {
                        manager.saveNewVacancy(
                            Vacancy(
                                id = 0,
                                title = binding.nameTiet.text.toString(),
                                skills = binding.skillsTiet.text.toString(),
                                salary = binding.salaryTiet.text.toString().toDouble(),
                                hunterId = selectedHeadHunter!!.id,
                                companyId = arguments?.getInt("COMPANY_ID") ?: 0
                            )
                        )
                    }
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(
                        context,
                        "Укажите рекрутера и заполните все поля",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.materialToolbar3.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            binding.headHuntersList.adapter = adapter
            binding.headHuntersList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            adapter.items =
                manager.getHeadHuntersByCompany(companyId = arguments?.getInt("COMPANY_ID") ?: 0)
        }
        adapter.notifyDataSetChanged()
    }

    inner class HeadHuntersAdapter(
        private val doOnItemClicked: (HeadHunter) -> Unit
    ) : RecyclerView.Adapter<HeadHuntersAdapter.ViewHolder>() {

        var items: List<HeadHunter> = listOf()

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