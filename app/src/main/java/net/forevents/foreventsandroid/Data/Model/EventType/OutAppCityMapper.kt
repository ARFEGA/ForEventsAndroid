package net.forevents.foreventsandroid.Data.Model.EventType

import net.forevents.foreventsandroid.Data.CreateUser.Mapper
import net.forevents.foreventsandroid.Data.CreateUser.User.*
import net.forevents.foreventsandroid.Data.Model.EventType.AppEventType
import net.forevents.foreventsandroid.Data.Model.EventType.ResultEventType
import net.forevents.foreventsandroid.Util.stringNullToString


class OutAppEventTypeMapper: Mapper<ResultEventType, AppEventType> {


    override fun transform(input: ResultEventType): AppEventType =
        AppEventType(
            stringNullToString(input.id),
            stringNullToString(input.name)
        )


    override fun transformList(inputList: List<ResultEventType>): List<AppEventType> =
        inputList.map {
            transform(it) }







}