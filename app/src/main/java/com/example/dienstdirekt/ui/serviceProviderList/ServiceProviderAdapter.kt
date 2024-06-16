package com.example.dienstdirekt.ui.serviceProviderList

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dienstdirekt.R
import com.example.dienstdirekt.ui.register.RegisterInput

class ServiceProviderAdapter(
    private var providerList: List<RegisterInput>,
    private val context: Context
) : RecyclerView.Adapter<ServiceProviderAdapter.ServiceProviderViewHolder>() {

    inner class ServiceProviderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.unternehmenName)
        val contentTextView: TextView = itemView.findViewById(R.id.unternehmenBeschreibung)
        val serviceProviderImage: ImageView = itemView.findViewById(R.id.unternehmenBild)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceProviderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dienstleistungen_item, parent, false)
        return ServiceProviderViewHolder(view)
    }

    override fun getItemCount(): Int = providerList.size

    override fun onBindViewHolder(holder: ServiceProviderViewHolder, position: Int) {
        val serviceProvider = providerList[position]
        holder.titleTextView.text = serviceProvider.companyName
        holder.contentTextView.text = serviceProvider.email
        // You can also set the image if you have a mechanism to load images
        // holder.serviceProviderImage.setImageResource(serviceProvider.imageResource)
    }

    fun refreshData(newServiceProvider: List<RegisterInput>) {
        providerList = newServiceProvider
        notifyDataSetChanged()
    }
}
