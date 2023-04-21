package com.messaging.scrtm.trade.choosenft

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.messaging.scrtm.databinding.ItemChooseNftBinding

class ChooseNFTAdapter : RecyclerView.Adapter<ChooseNFTAdapter.ViewHolder>() {
    val data = mutableListOf<String>("1231123", "123123")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChooseNftBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder (val binding : ItemChooseNftBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(){

        }
    }
}