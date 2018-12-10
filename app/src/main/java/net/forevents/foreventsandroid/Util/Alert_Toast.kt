package net.forevents.foreventsandroid.Util


import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import net.forevents.foreventsandroid.R


fun ShowAlert(context: Context, title:String, msg:String)
{
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(msg)
        .show()
}

fun showDeleteDialog_(context: Context){
    // Late initialize an alert dialog object
    lateinit var dialog:AlertDialog
    context.setTheme(R.style.AlertDialogDeleteUser)
    // Initialize a new instance of alert dialog builder object
    val builder = AlertDialog.Builder(context)//ContextThemeWrapper(context, R.style.AlertDialogCustom))

    // Set a title for alert dialog
    builder.setTitle(R.string.title_alert_delete_profile)

    // Set a message for alert dialog
    builder.setMessage(R.string.msg_alert_delete_profile)

     var resultAlertDialog : Boolean = false

    // On click listener for dialog buttons
    val resultDialogClickListener = DialogInterface.OnClickListener{_,which ->
        when(which){
            DialogInterface.BUTTON_POSITIVE ->  resultAlertDialog = true
            //DialogInterface.BUTTON_NEGATIVE -> Toast.makeText(context,"ENJOY IT",Toast.LENGTH_LONG)
            DialogInterface.BUTTON_NEUTRAL -> resultAlertDialog =false
        }
    }


    // Set the alert dialog positive/yes button
    builder.setPositiveButton("BORRAR PERFIL",resultDialogClickListener)


    // Set the alert dialog negative/no button
    //builder.setNegativeButton("NO",dialogClickListener)

    // Set the alert dialog neutral/cancel button
    builder.setNeutralButton("CANCELAR",resultDialogClickListener)


    // Initialize the AlertDialog using builder object
    dialog = builder.create()
    dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.WHITE)
    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE)
    dialog.setIcon(R.drawable.delete_user)
    // Finally, display the alert dialog
    dialog.show()

}

fun showDialog(context: Context, title:String, msg:String) {
    val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))


    with(builder) {
        setTitle("BIENVENIDO")
        setMessage("Accede y disfruta ${msg} ")
        //setPositiveButton(" ", null)
        //setPositiveButtonIcon(context.resources.getDrawable(R.drawable.logo_grande))
        //setNegativeButton("CANCEL", null)
        //setNeutralButton("NEUTRAL", null)
        //setIcon(R.drawable.logo_2)



    }
    val alertDialog = builder.create()
    alertDialog.show()


    //val button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
    //with(button) {
    //    setBackgroundColor(rgb(0,0,0))
    //    setPadding(10, 10, 10, 10)
    // setTextColor(Color.WHITE)

    //}
}