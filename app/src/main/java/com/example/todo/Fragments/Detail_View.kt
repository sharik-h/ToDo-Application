package com.example.todo.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo.R
import com.example.todo.ViewModel.TodoViewModel
import com.example.todo.ViewModel.TodoViewModelFactory
import com.example.todo.data.Todo
import com.example.todo.data.TodoApplication
import com.example.todo.databinding.FragmentDetailBinding
import com.example.todo.databinding.FragmentDetailViewBinding


class Detail_View : Fragment() {

    private var _binding: FragmentDetailViewBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: Detail_ViewArgs by navArgs()

    private lateinit var todo: Todo

    private val viewModel: TodoViewModel by activityViewModels {
        TodoViewModelFactory(
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
        _binding = FragmentDetailViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        viewModel.retriveData(id).observe(this.viewLifecycleOwner){  selectedItem ->
            todo = selectedItem
            bind(todo)
        }
        binding.editButton.setOnClickListener { editAction(id) }
    }

    private fun bind(todo: Todo) {
        binding.apply {
            TodoTitletext.text = todo.Title
            TodoNotestext.text = todo.Notes
        }
    }

    private fun editAction(id: Int) {
        val action = Detail_ViewDirections.actionDetailViewToUpdateFragment(id)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}