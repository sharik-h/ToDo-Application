package com.example.todo.Fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.whenResumed
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo.R
import com.example.todo.ViewModel.TodoViewModel
import com.example.todo.ViewModel.TodoViewModelFactory
import com.example.todo.data.TodoApplication
import com.example.todo.databinding.FragmentAddBinding
import com.example.todo.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddFragment : Fragment() {

    var color = "#00FFFF"


    private val navigationArgs: AddFragmentArgs by navArgs()

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
            findNavController().navigate(R.id.action_addFragment_to_bottom_sheet2)
            changeColor()
        }
        changeColor()
    }

   fun changeColor() {
       color = viewModel.whichcolor()
       binding.colorIdnt.setColorFilter(Color.parseColor(color))
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