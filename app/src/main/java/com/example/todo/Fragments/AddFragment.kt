package com.example.todo.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo.R
import com.example.todo.ViewModel.TodoViewModel
import com.example.todo.ViewModel.TodoViewModelFactory
import com.example.todo.data.TodoApplication
import com.example.todo.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddFragment : BottomSheetDialogFragment() {

    val color_list = listOf<String>("#FF0000","#FFA500","#FFFF00","#00FF00","#00FFFF","#FF1493")
    private var color: String = color_list.random()

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
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        binding.redButton.setOnClickListener{
            changeColor("#FF0000")
            color = "#FF0000"
        }
        binding.orangeButton.setOnClickListener{
            changeColor("#FFA500")
            color = "#FFA500"
        }
        binding.yellowButton.setOnClickListener{
            changeColor("#FFFF00")
            color = "#FFFF00"
        }
        binding.greenButton.setOnClickListener{
            changeColor("#00FF00")
            color = "#00FF00"
        }
        binding.aquaButton.setOnClickListener{
            changeColor("#00FFFF")
            color = "#00FFFF"
        }
        binding.pinkButton.setOnClickListener{
            changeColor("#FF1493")
            color = "#FF1493"
        }
        changeColor(color)
    }

   fun changeColor(color : String) {
       binding.card.setCardBackgroundColor(Color.parseColor(color))
   }

    private fun addNewTodoData() {
        if (isEntryValid()){
            viewModel.addNewTodoData(
                binding.TodoTitle.text.toString(),
                binding.TodoNotes.text.toString(),
                color
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