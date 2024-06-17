package com.example.dienstdirekt.ui.serviceProviderList

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dienstdirekt.R
import com.example.dienstdirekt.ui.register.RegisterInput
import com.example.dienstdirekt.ui.unternehmen.UnternehmenData

class ServiceProviderAdapter(
    private var providerList: List<UnternehmenData>,
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
        holder.titleTextView.text = serviceProvider.name
        holder.contentTextView.text = serviceProvider.beschreibung

        // Convert byteArray to Bitmap and set it to the ImageView
        if (serviceProvider.picture != null) {
            val bitmap = BitmapFactory.decodeByteArray(serviceProvider.picture, 0,
                serviceProvider.picture.size)
            holder.serviceProviderImage.setImageBitmap(bitmap)
        } else {
            // Handle case where picture ByteArray is null or empty
            // For example, set a placeholder image or hide the ImageView
            holder.serviceProviderImage.setImageDrawable(null) // Clears any existing image
            // Alternatively, you can set a placeholder image:
            // holder.serviceProviderImage.setImageResource(R.drawable.placeholder_image)
        }
    }

    fun refreshData(newServiceProvider: List<UnternehmenData>) {
        providerList = newServiceProvider
        notifyDataSetChanged()
    }
}
