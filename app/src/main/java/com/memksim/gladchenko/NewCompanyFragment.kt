package com.memksim.gladchenko

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.memksim.gladchenko.data.Company
import com.memksim.gladchenko.databinding.FragmentNewCompanyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewCompanyFragment : Fragment(R.layout.fragment_new_company) {

    @Inject
    lateinit var manager: DatabaseManager

    private var binding: FragmentNewCompanyBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewCompanyBinding.bind(view)

        binding?.let {
            it.materialToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            it.add.setOnClickListener { view ->
                if (it.nameTiet.text?.isNotEmpty() == true) {
                    lifecycleScope.launch {
                        manager.saveNewCompany(
                            Company(
                                id = 0,
                                name = it.nameTiet.text.toString(),
                                vacanciesIds = ""
                            )
                        )
                    }
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(context, "Нужно ввести название компании", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}