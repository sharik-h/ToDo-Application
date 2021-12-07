package com.example.todo.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.ViewModel.TodoViewModel
import com.example.todo.ViewModel.TodoViewModelFactory
import com.example.todo.data.TodoApplication
import com.example.todo.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddFragment : Fragment() {

    val colors = arrayOf("#FF0000", "#1E90FF", "#00FF00", "#00FFFF", "#FF1493", "#FFA500", "#FFFF00")
    var color = "#FFFFFFFF"

    private val viewModel: TodoViewModel by activityViewModels {
        TodoViewModelFactory (
            (activity?.application as TodoApplication).database.todoDao()
                )
    }

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.saveButton.setOnClickListener {
            addNewTodoData()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        binding.changeColor.setOnClickListener{
            color = colors.random()
            findNavController().navigate(R.id.action_addFragment_to_bottom_sheet2)
        }
    }

    private fun addNewTodoData() {
        if (isEntryValid()){
            viewModel.addNewTodoData(
                binding.TodoTitle.text.toString(),
                binding.TodoNotes.text.toString(),

            )
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.TodoTitle.text.toString(),
            binding.TodoNotes.text.toString()
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}