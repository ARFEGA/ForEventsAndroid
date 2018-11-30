package net.forevents.foreventsandroid.Util

fun stringNullToString(value:String?):String{
     value?.let{
        return it
    }
    return ""
}

