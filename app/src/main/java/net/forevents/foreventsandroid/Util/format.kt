package net.forevents.foreventsandroid.Util

fun stringNullToString(value:String?):String{
     value?.let{
        return it
    }
    return ""
}


/*

//No lo tengo claro, soy un tanto zoquete, para hacer que la actividad se entere de algo, lo hago así:

//En el fragment
// 1º Creo interfaz

interface OnButtonClickedListener{
    fun onButtonClicked(x:TipoX)
}

//2º tambien en fragment
//Creo variable de tipo la interfaz creada

private var onButtonClickedListener: OnButtonClickedListener? = null

//3º El disparador del button

button.setOnClickListener{
    onButtonClickedListener.onButtonClicked(x as TipoX)
}

//4º Métodos onAttach y onDetach


//Ahora en la activity
//1º Asocio la interfaz a la clase
//2º Implemento el método de la interfaz


//Pero si es pura:

////En el fragment
//// 1º Creo interfaz
//
interface InterfazDirecta {
    fun passData()
}

//2º Creo la variable de tipo interfaz
private var interfazDirecta: InterfazDirecta? = null

//3º El disparador cual es, a mi me llega del adapter la ejecución de un
// metodo disparado por la pulsación en una imagen

button.setOnClickListener{
    onButtonClickedListener.onButtonClicked(x as TipoX)
}



//Ahora en la activity
//1º Asocio la interfaz a la clase
//2º Implemento el método de la interfaz
*/