package com.example.lesson18.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lesson18.databinding.PhotoItemBinding
import com.example.lesson18.other_classes.image

class MyAdapter(
    private var context: Context,
    private var list: List<image>
) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.dateText.text = list[position].date

        Glide.with(context)
            .load(list[position].imgSrc)
            .into(holder.binding.imageView)
    }
}

class MyViewHolder(val binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root)