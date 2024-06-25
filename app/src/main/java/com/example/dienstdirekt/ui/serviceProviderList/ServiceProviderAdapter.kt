package com.example.dienstdirekt.ui.serviceProviderList

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dienstdirekt.R
import com.example.dienstdirekt.ui.unternehmen.UnternehmenData

class ServiceProviderAdapter(
    private var providerList: List<UnternehmenData>,
    private val context: Context,
) : RecyclerView.Adapter<ServiceProviderAdapter.ServiceProviderViewHolder>() {
    inner class ServiceProviderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.unternehmenName)
        val contentTextView: TextView = itemView.findViewById(R.id.unternehmenBeschreibung)
        val serviceProviderImage: ImageView = itemView.findViewById(R.id.unternehmenBild)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ServiceProviderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dienstleistungen_item, parent, false)
        return ServiceProviderViewHolder(view)
    }

    override fun getItemCount(): Int = providerList.size

    override fun onBindViewHolder(
        holder: ServiceProviderViewHolder,
        position: Int,
    ) {
        val serviceProvider = providerList[position]
        holder.titleTextView.text = serviceProvider.name
        holder.contentTextView.text = serviceProvider.beschreibung

        if (serviceProvider.picture != null) {
            val bitmap =
                BitmapFactory.decodeByteArray(
                    serviceProvider.picture,
                    0,
                    serviceProvider.picture.size,
                )
            holder.serviceProviderImage.setImageBitmap(bitmap)
        } else {
            holder.serviceProviderImage.setImageDrawable(null)
        }
    }

    fun refreshData(newServiceProvider: List<UnternehmenData>) {
        providerList = newServiceProvider
        notifyDataSetChanged()
    }
}
