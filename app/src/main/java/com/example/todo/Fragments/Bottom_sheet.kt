package com.example.todo.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.todo.R
import com.example.todo.ViewModel.TodoViewModel
import com.example.todo.ViewModel.TodoViewModelFactory
import com.example.todo.data.TodoApplication
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.aquaButton.setOnClickListener{
            viewmodel.setcolor("#00FFFF")
        }
        binding.yellowButton.setOnClickListener{
            viewmodel.setcolor("#FFFF00")
        }
        binding.greenButton.setOnClickListener{
            viewmodel.setcolor("#00FF00")
        }
        binding.redButton.setOnClickListener{
            viewmodel.setcolor("#FF0000")
        }
        binding.orangeButton.setOnClickListener{
            viewmodel.setcolor("#FFA500")
        }
        binding.pinkButton.setOnClickListener{
            viewmodel.setcolor("#FF1493")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}