package com.example.todo.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo.ViewModel.TodoViewModel
import com.example.todo.ViewModel.TodoViewModelFactory
import com.example.todo.data.Todo
import com.example.todo.data.TodoApplication
import com.example.todo.databinding.FragmentUpdateBinding


class UpdateFragment : Fragment() {

    private val navigationArgs: UpdateFragmentArgs by navArgs()

    private val viewModel: TodoViewModel by activityViewModels {
        TodoViewModelFactory(
            (activity?.application as TodoApplication).database.todoDao()
        )
    }

    lateinit var todo: Todo
    private lateinit var color: String


    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id

        viewModel.retriveData(id).observe(this.viewLifecycleOwner){ selectedItem ->
             todo = selectedItem

            bind(todo)
        }
        binding.redButton.setOnClickListener{
          color  = "#FF0000"
        }
        binding.orangeButton.setOnClickListener{
            color  = "#FFA500"
        }
        binding.yellowButton.setOnClickListener{
            color  = "#FFFF00"
        }
        binding.greenButton.setOnClickListener{
            color  = "#00FF00"
        }
        binding.aquaButton.setOnClickListener{
            color  = "#00FFFF"
        }
        binding.pinkButton.setOnClickListener{
            color  = "#FF1493"
        }
    }

    private fun bind(todo: Todo) {
        binding.apply {
            updateTodoTitle.setText(todo.Title, TextView.BufferType.SPANNABLE)
            updateTodoSubtitle.setText(todo.Notes, TextView.BufferType.SPANNABLE)
            updateButton.setOnClickListener{ updatetodo() }
        }
    }

    private fun updatetodo() {
        if (isEntryvalid()) {
            viewModel.updateTodo(
                this.navigationArgs.id,
                this.binding.updateTodoTitle.text.toString(),
                this.binding.updateTodoSubtitle.text.toString(),
                color)
            val action = UpdateFragmentDirections.actionUpdateFragmentToListFragment()
            findNavController().navigate(action)
        }
    }


    fun isEntryvalid(): Boolean {
        return viewModel.isEntryValid(
            binding.updateTodoTitle.text.toString(),
            binding.updateTodoSubtitle.text.toString()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}