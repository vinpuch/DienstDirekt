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

// Diese Klasse ist ein Adapter für die RecyclerView, der die Dienstleisterdaten anzeigt.
class ServiceProviderAdapter(
    private var providerList: List<UnternehmenData>,
    private val context: Context,
) : RecyclerView.Adapter<ServiceProviderAdapter.ServiceProviderViewHolder>() {

    // Diese innere Klasse repräsentiert einen ViewHolder für die RecyclerView.
    // Sie enthält Referenzen auf die Ansichten, die in jedem Element der Liste angezeigt werden.
    inner class ServiceProviderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.unternehmenName)
        val contentTextView: TextView = itemView.findViewById(R.id.unternehmenBeschreibung)
        val serviceProviderImage: ImageView = itemView.findViewById(R.id.unternehmenBild)
    }

    // Diese Methode wird aufgerufen, wenn ein neuer ViewHolder erstellt wird.
    // Sie bläht das Layout für jedes Element auf und gibt einen neuen ViewHolder zurück.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ServiceProviderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dienstleistungen_item, parent, false)
        return ServiceProviderViewHolder(view)
    }

    // Diese Methode gibt die Anzahl der Elemente in der Liste zurück.
    override fun getItemCount(): Int = providerList.size

    // Diese Methode bindet die Daten an den ViewHolder.
    // Sie wird aufgerufen, wenn die RecyclerView ein Element anzeigen muss.
    override fun onBindViewHolder(
        holder: ServiceProviderViewHolder,
        position: Int,
    ) {
        val serviceProvider = providerList[position]
        holder.titleTextView.text = serviceProvider.name
        holder.contentTextView.text = serviceProvider.beschreibung

        // Wenn das Dienstleistungsunternehmen ein Bild hat, wird es angezeigt.
        // Wenn nicht, wird das Bild auf null gesetzt.
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

    // Diese Methode aktualisiert die Daten in der Liste und benachrichtigt den Adapter, dass sich die Daten geändert haben.
    fun refreshData(newServiceProvider: List<UnternehmenData>) {
        providerList = newServiceProvider
        notifyDataSetChanged()
    }
}
