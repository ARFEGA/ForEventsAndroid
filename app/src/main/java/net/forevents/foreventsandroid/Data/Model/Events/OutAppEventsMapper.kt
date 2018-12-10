package net.forevents.foreventsandroid.Data.CreateUser.CreateUser

import net.forevents.foreventsandroid.Data.CreateUser.Mapper
import net.forevents.foreventsandroid.Data.CreateUser.User.ApiEvents
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents
import net.forevents.foreventsandroid.Data.CreateUser.User.ResultEvents
import net.forevents.foreventsandroid.Util.stringNullToString


class OutAppEventsMapper: Mapper<ResultEvents, AppEvents> {


    override fun transform(input: ResultEvents): AppEvents =
        AppEvents(
            stringNullToString(input.name),
            input.location.coordinates[1].toDouble(), //Latitude comes in second place
            input.location.coordinates[0].toDouble(), //Longitude comes in first place
            stringNullToString(input.description),
            stringNullToString(input.id),
            if (input.transactions.size != 0)input.transactions[0].idTrans else null,
            if (input.transactions.size != 0)input.transactions[0].user else null,
            if (input.media.isNullOrEmpty() || input.media[0].description.isNullOrBlank())
                            "" else input.media[0].description ,
            if (input.media.isNullOrEmpty() || input.media[0].url.isNullOrBlank())
                "" else input.media[0].url,
            stringNullToString(input.begin_date),
            stringNullToString(input.address),
            stringNullToString(input.city),
            stringNullToString(input.province),
            stringNullToString(input.country)
            )
    override fun transformList(inputList: List<ResultEvents>): List<AppEvents> =
        inputList.map {
            transform(it) }







}