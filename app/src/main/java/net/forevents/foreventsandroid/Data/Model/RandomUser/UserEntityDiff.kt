package net.forevents.foreventsandroid.Data.CreateUser.RandomUser

import androidx.recyclerview.widget.DiffUtil
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents


class UserEntityDiff:DiffUtil.ItemCallback<UserEntity>() {
    override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity)=
        //oldItem.userId == newItem.userId
        oldItem.first_name == newItem.first_name


    override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity)=
        oldItem == newItem

}