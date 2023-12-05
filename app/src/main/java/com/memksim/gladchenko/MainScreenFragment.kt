package com.memksim.gladchenko

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenFragment : Fragment(R.layout.fragment_main_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button).setOnClickListener {
            findNavController().navigate(R.id.action_mainScreenFragment_to_jobSeekersListFragment)
        }
        view.findViewById<Button>(R.id.button2).setOnClickListener {
            findNavController().navigate(R.id.action_mainScreenFragment_to_companiesListFragment)

        }
        view.findViewById<Button>(R.id.button3).setOnClickListener {
            findNavController().navigate(R.id.action_mainScreenFragment_to_headHuntersListFragment)
        }
    }

}