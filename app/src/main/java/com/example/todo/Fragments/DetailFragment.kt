package com.example.todo.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo.R
import com.example.todo.ViewModel.TodoViewModel
import com.example.todo.ViewModel.TodoViewModelFactory
import com.example.todo.data.Todo
import com.example.todo.data.TodoApplication
import com.example.todo.databinding.FragmentDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DetailFragment : BottomSheetDialogFragment() {

    private val navigationArgs: DetailFragmentArgs by navArgs()

    lateinit var todo: Todo

    private val viewModel: TodoViewModel by activityViewModels {
        TodoViewModelFactory(
            (activity?.application as TodoApplication).database.todoDao()
        )
    }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        viewModel.retriveData(id).observe(this.viewLifecycleOwner){ selectedItem ->
            todo = selectedItem
            bind(todo)
        }
    }

    private fun bind(todo: Todo){
        binding.apply {
            TodoTitletext.text = todo.Title
            TodoSubtitle.text = todo.Subtitle
            TodoNotes.text = todo.Notes
            TagColor.setBackgroundColor(Color.parseColor(todo.color))
            detailButton.setOnClickListener{ gotoDetail()}
            deleteButton.setOnClickListener{ deleteAction() }
        }
    }

    private fun deleteAction() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage("Are you sure you want to delete it.")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ ->}
            .setPositiveButton("Yes") { _, _ ->
                deleteTodo()
            }
            .show()
    }

    private fun deleteTodo() {
        dismiss()
        viewModel.deleteTodoData(todo)
    }


    private fun gotoDetail() {
        val action = DetailFragmentDirections.actionDetailFragmentToDetailView(todo.id)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}