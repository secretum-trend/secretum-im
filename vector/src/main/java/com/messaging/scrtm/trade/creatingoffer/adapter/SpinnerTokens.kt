package com.messaging.scrtm.trade.creatingoffer.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.messaging.scrtm.R
import com.messaging.scrtm.data.solana.entity.Value

class SpinnerTokens(context: Context, objects: List<Value>) :
    ArrayAdapter<Value>(context, R.layout.item_spinner_token, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val item = getItem(position)
        if (item != null) {
            (view.findViewById<TextView>(android.R.id.text1)).text = item.pubkey
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val item = getItem(position)
        if (item != null) {
            (view.findViewById<TextView>(android.R.id.text1)).text = item.pubkey
            (view.findViewById<TextView>(android.R.id.text1)).setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        return view
    }
}
