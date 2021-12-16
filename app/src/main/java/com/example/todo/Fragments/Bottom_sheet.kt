package com.example.todo.Fragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.example.todo.R
import com.example.todo.ViewModel.TodoViewModel
import com.example.todo.ViewModel.TodoViewModelFactory
import com.example.todo.data.TodoApplication
import com.example.todo.databinding.FragmentAddBinding
import com.example.todo.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Bottom_sheet : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!


    private val viewmodel: TodoViewModel by activityViewModels {
        TodoViewModelFactory (
            (activity?.application as TodoApplication).database.todoDao()
                )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("StringFormatMatches")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.aquaButton.setOnClickListener{
           changeColor("#00FFFF")
        }
        binding.yellowButton.setOnClickListener{
           changeColor("#FFFF00")
        }
        binding.greenButton.setOnClickListener{
           changeColor("#00FF00")
        }
        binding.redButton.setOnClickListener{
           changeColor("#FF0000")
        }
        binding.orangeButton.setOnClickListener{
            changeColor("#FFA500")
        }
        binding.pinkButton.setOnClickListener{
          changeColor("#FF1493")
        }

    }

    fun changeColor(color: String) {
        viewmodel.setcolor(color)
        val action = Bottom_sheetDirections.actionBottomSheetToAddFragment(color)
        findNavController().navigate(action)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}