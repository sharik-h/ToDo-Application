package com.example.todo.adapter

import android.graphics.Color
import android.provider.Settings.Secure.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.Todo
import com.example.todo.databinding.ListSampleBinding


class TodoListAdapter(private val OnItemClicked: (Todo) -> Unit) :
        ListAdapter<Todo, TodoListAdapter.ItemViewHolder>(DiffCallback){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(
                ListSampleBinding.inflate(LayoutInflater.from(parent.context))
            )
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val current = getItem(position)
            holder.itemView.setOnClickListener {
                OnItemClicked(current)
            }
            holder.bind(current)
        }

        class ItemViewHolder(private var binding: ListSampleBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(todo: Todo) {

                binding.apply {
                    title.text = todo.Title
                    notes.text = todo.Subtitle
                    sampleCard.setCardBackgroundColor(Color.parseColor(todo.color))
                }

            }
        }

        companion object {
            private val DiffCallback = object : DiffUtil.ItemCallback<Todo>() {
                override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                    return oldItem.Title == newItem.Title
                }
            }
        }

    }