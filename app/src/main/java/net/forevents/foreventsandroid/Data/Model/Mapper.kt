package net.forevents.foreventsandroid.Data.CreateUser

interface Mapper<in R, out T>{
    fun transform(input:R):T
    fun transformList(inputList:List<R>):List<T>
}
