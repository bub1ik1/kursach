package com.memksim.gladchenko

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.memksim.cursach.data.Company
import com.memksim.gladchenko.data.HeadHunter
import com.memksim.gladchenko.data.Vacancy
import com.memksim.gladchenko.databinding.FragmentVacancyInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class VacancyInfoFragment : Fragment(R.layout.fragment_vacancy_info) {

    @Inject
    lateinit var manager: DatabaseManager

    private var binding: FragmentVacancyInfoBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVacancyInfoBinding.bind(view)
        var vacancy: Vacancy?
        var company: Company?
        var headHunter: HeadHunter?
        runBlocking {
            vacancy = manager.getVacancyById(arguments?.getInt("VACANCY_ID") ?: 0)
            company = manager.getCompanyById(vacancy?.id ?: 0)
            headHunter = manager.getHeadHunterById(vacancy?.hunterId ?: 0)
        }
        binding?.let {
            it.materialToolbar4.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            it.floatingActionButton.setOnClickListener {
                lifecycleScope.launch {
                    manager.deleteVacancy(vacancy ?: return@launch)
                }
                findNavController().navigateUp()
            }
            it.textView.text = vacancy?.title
            it.textView2.text = company?.name
            it.textView4.text = vacancy?.salary.toString()
            it.contactsTextView.text = "Звоните: ${headHunter?.contactPhone}, ${headHunter?.name}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}