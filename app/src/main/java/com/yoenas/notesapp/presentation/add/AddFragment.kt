package com.yoenas.notesapp.presentation.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yoenas.notesapp.R
import com.yoenas.notesapp.data.entity.Notes
import com.yoenas.notesapp.databinding.FragmentAddBinding
import com.yoenas.notesapp.presentation.NotesViewModel
import com.yoenas.notesapp.utils.ExtensionFunctions.setupActionBar
import com.yoenas.notesapp.utils.HelperFunctions.dateTodaySimpleFormat
import com.yoenas.notesapp.utils.HelperFunctions.parseToPriority
import com.yoenas.notesapp.utils.HelperFunctions.spinnerListener
import com.yoenas.notesapp.utils.HelperFunctions.verifyDataFromUser

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding as FragmentAddBinding

    private val addViewModel by viewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.apply {
            toolbarAdd.setupActionBar(this@AddFragment, R.id.addFragment)
            spinnerPriorities.onItemSelectedListener = spinnerListener(context, binding.priorityIndicator)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        val item = menu.findItem(R.id.menu_save)
        item.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
            insertNotes()
        }
    }

    private fun insertNotes() {
        binding.apply {
            val title = edtTitle.text.toString()
            val priority = spinnerPriorities.selectedItem.toString()
            val description = edtDescription.text.toString()

            val validation = verifyDataFromUser(title, description)
            if (validation) {
                val note = Notes(
                    0,
                    title,
                    parseToPriority(context, priority),
                    description,
                    dateTodaySimpleFormat()
                )
                addViewModel.insertData(note)
                Toast.makeText(context, "Successfully added.", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_homeFragment)
            } else {
                Toast.makeText(context, "Please input title and description.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}