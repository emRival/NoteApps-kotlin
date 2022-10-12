package com.yoenas.notesapp.presentation.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yoenas.notesapp.R
import com.yoenas.notesapp.data.entity.Notes
import com.yoenas.notesapp.databinding.FragmentUpdateBinding
import com.yoenas.notesapp.presentation.NotesViewModel
import com.yoenas.notesapp.utils.ExtensionFunctions.setupActionBar
import com.yoenas.notesapp.utils.HelperFunctions.dateTodaySimpleFormat
import com.yoenas.notesapp.utils.HelperFunctions.parseToPriority
import com.yoenas.notesapp.utils.HelperFunctions.spinnerListener
import com.yoenas.notesapp.utils.HelperFunctions.verifyDataFromUser

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding as FragmentUpdateBinding

    private val navArgs by navArgs<UpdateFragmentArgs>()

    private val updateViewModel by viewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.safeArgs = navArgs

        setHasOptionsMenu(true)

        binding.apply {
            toolbarUpdate.setupActionBar(this@UpdateFragment, R.id.updateFragment)
            spinnerPrioritiesUpdate.onItemSelectedListener =
                spinnerListener(context, binding.priorityIndicator)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        val item = menu.findItem(R.id.menu_save)
        item.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
            updateItem()
        }
    }

    private fun updateItem() {
        binding.apply {
            val title = edtTitleUpdate.text.toString()
            val desc = edtDescriptionUpdate.text.toString()
            val priority = spinnerPrioritiesUpdate.selectedItem.toString()

            val validation = verifyDataFromUser(title, desc)
            if (validation) {
                val updatedItem = Notes(
                    navArgs.currentItem.id,
                    title,
                    parseToPriority(context, priority),
                    desc,
                    getString(R.string.txt_edited_on) + "\n" + dateTodaySimpleFormat()
                )
                updateViewModel.updateData(updatedItem)
                Toast.makeText(context, "Successfully updated.", Toast.LENGTH_SHORT)
                    .show()
                val action =
                    UpdateFragmentDirections.actionUpdateFragmentToDetailFragment(updatedItem)
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "Please input title and description.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}