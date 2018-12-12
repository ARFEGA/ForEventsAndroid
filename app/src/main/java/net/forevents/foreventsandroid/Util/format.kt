package net.forevents.foreventsandroid.Util

fun stringNullToString(value:String?):String{
     value?.let{
        return it
    }
    return ""
}

fun formatoFechaZ(fecha:String):String{

    val arrayFecha = fecha.split("T".toRegex())
    val fechaOrdenada = arrayFecha[0].split("-".toRegex())

    var fechaFinal:String =""


    fechaOrdenada.map {fechaFinal = "${it}/${fechaFinal}" }
    fechaFinal =fechaFinal.dropLast(1)
    fechaFinal = "${fechaFinal} ${arrayFecha[1].replaceAfter(".","").replace(".","")}"

    return fechaFinal

}

