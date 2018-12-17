package net.forevents.foreventsandroid.Data.Model.City

import net.forevents.foreventsandroid.Data.CreateUser.Mapper
import net.forevents.foreventsandroid.Data.CreateUser.User.*
import net.forevents.foreventsandroid.Data.Model.City.AppCity
import net.forevents.foreventsandroid.Data.Model.City.ResultCities
import net.forevents.foreventsandroid.Util.stringNullToString


class OutAppCityMapper: Mapper<ResultCities, AppCity> {


    override fun transform(input: ResultCities): AppCity =
        AppCity(
            input.location.coordinates[1].toDouble(), //Latitude comes in second place
            input.location.coordinates[0].toDouble(), //Longitude comes in first place
            stringNullToString(input.id),
            stringNullToString(input.city),
            stringNullToString(input.province),
            stringNullToString(input.country),
            stringNullToString(input.zip_code)
            )
    override fun transformList(inputList: List<ResultCities>): List<AppCity> =
        inputList.map {
            transform(it) }







}