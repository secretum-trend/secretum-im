package com.messaging.scrtm.trade.choosenft

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.messaging.scrtm.data.solana.entity.Value
import com.messaging.scrtm.databinding.ItemChooseNftBinding

class ChooseNFTAdapter : RecyclerView.Adapter<ChooseNFTAdapter.ViewHolder>() {
    var data = listOf<Value>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemSelected: Value? = null
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChooseNftBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(val binding: ItemChooseNftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (data[layoutPosition] != itemSelected) {
                    itemSelected = data[layoutPosition]
                }
            }
        }

        fun bind(item: Value) {
            binding.imvCheck.isSelected = item == itemSelected
            binding.tvNameNft.text = item.account.data.parsed.info.mint
        }
    }
}