package net.forevents.foreventsandroid.Data.CreateUser.RandomUser

import androidx.recyclerview.widget.DiffUtil
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents


class EventsDiff:DiffUtil.ItemCallback<AppEvents>() {
    override fun areItemsTheSame(oldItem: AppEvents, newItem: AppEvents)=
        //oldItem.userId == newItem.userId
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: AppEvents, newItem: AppEvents)=
        oldItem == newItem

}