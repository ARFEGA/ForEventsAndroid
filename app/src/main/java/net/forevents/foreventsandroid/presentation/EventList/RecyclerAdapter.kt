package net.forevents.foreventsandroid.presentation.EventList


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_item.view.*
import net.forevents.foreventsandroid.Data.CreateUser.RandomUser.EventsDiff

import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents
import net.forevents.foreventsandroid.R



typealias OnEventClick = (appEvents: AppEvents) -> Unit

class RecyclerAdapter(val onEventClick : OnEventClick): ListAdapter<AppEvents,RecyclerAdapter.AdapterViewHolder>(EventsDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.row_item,parent,false)
        return AdapterViewHolder(row)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class AdapterViewHolder(view: View) :RecyclerView.ViewHolder(view){

        fun bind(appEvents: AppEvents){
            with(itemView){//itemView es una propiedad que apunta a cada una de las filas
                event_name.text = appEvents.description

                Glide.with(event_image)
                    .load(appEvents.imgUrl)
                    .into(event_image)

            }
            //Asignación del evento click a la fila
            itemView.setOnClickListener {
                //onUserClick(getItem(adapterPosition)) Esta o la siguiente opción
                onEventClick(appEvents)
            }
        }
    }
}