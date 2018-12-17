package net.forevents.foreventsandroid.Data.Model.Transactions

import net.forevents.foreventsandroid.Data.CreateUser.Mapper
import net.forevents.foreventsandroid.Util.formatoFechaZ
import net.forevents.foreventsandroid.Util.stringNullToString


class OutAppTransactionsMapper: Mapper<ResultTransactions, AppTransactions> {


    override fun transform(input: ResultTransactions): AppTransactions =
        AppTransactions(
            stringNullToString(input.id),
            stringNullToString(input.event._id),
            stringNullToString(input.event.name),
            stringNullToString(input.event.address),
            formatoFechaZ(stringNullToString(input.event.begin_date)),
            stringNullToString(input.event.city),
            stringNullToString(input.event.province),
            if (input.event.media.isNullOrEmpty() || input.event.media[0].url.isNullOrBlank())
                "" else input.event.media[0].url
            )
    override fun transformList(inputList: List<ResultTransactions>): List<AppTransactions> =
        inputList.map {
            transform(it) }







}