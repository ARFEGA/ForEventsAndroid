package net.forevents.foreventsandroid.Data.Model.Events

import androidx.recyclerview.widget.DiffUtil



class EventsDiff:DiffUtil.ItemCallback<AppEvents>() {
    override fun areItemsTheSame(oldItem: AppEvents, newItem: AppEvents)=
        //oldItem.userId == newItem.userId
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: AppEvents, newItem: AppEvents)=
        oldItem == newItem

}