package com.venkat.digitalclininc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.venkat.digitalclininc.adapter.PrescriptionAdapter
import com.venkat.digitalclininc.databinding.PrescriptionListFragmentBinding
import com.venkat.digitalclininc.viewmodels.PrescriptionListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PrescriptionListFragment : Fragment() {

    private lateinit var adapter: PrescriptionAdapter
    private lateinit var bindingContext: PrescriptionListFragmentBinding
    private val prescriptionListViewModel: PrescriptionListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bindingContext = PrescriptionListFragmentBinding.inflate(inflater, container, false)

        prescriptionListViewModel.getPrescriptions().observe(viewLifecycleOwner) { it ->
            it.data?.let { items -> adapter = PrescriptionAdapter(items) }
            lifecycleScope.launch {
                bindingContext.prescriptionList.adapter = adapter
            }
        }
        return bindingContext.root
    }

}