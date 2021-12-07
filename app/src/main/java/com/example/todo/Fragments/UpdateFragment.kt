package com.example.todo.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo.R
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
    }

    private fun bind(todo: Todo) {
        binding.apply {
            updateTodoTitle.setText(todo.Title, TextView.BufferType.SPANNABLE)
            updateTodoNotes.setText(todo.Notes, TextView.BufferType.SPANNABLE)
            updateButton.setOnClickListener{ updatetodo() }
            deleteButton.setOnClickListener{ deleteTodo() }
        }
    }

    private fun deleteTodo() {
        viewModel.deleteTodoData(todo)
        val action = UpdateFragmentDirections.actionUpdateFragmentToListFragment()
        findNavController().navigate(action)
    }

    private fun updatetodo() {
        if (isEntryvalid()) {
            viewModel.updateTodo(
                this.navigationArgs.id,
                this.binding.updateTodoTitle.text.toString(),
                this.binding.updateTodoNotes.text.toString() )
            val action = UpdateFragmentDirections.actionUpdateFragmentToListFragment()
            findNavController().navigate(action)
        }
    }


    fun isEntryvalid(): Boolean {
        return viewModel.isEntryValid(
            binding.updateTodoTitle.text.toString(),
            binding.updateTodoNotes.text.toString()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}