package net.forevents.foreventsandroid.Data.Model.Transactions

data class ApiCreateTransaction(
    val `data`: Data,
    val message: String,
    val ok: Boolean
)

data class Data(
    val _id: String,
    val create_date: String,
    val event: String,
    val user: String
)