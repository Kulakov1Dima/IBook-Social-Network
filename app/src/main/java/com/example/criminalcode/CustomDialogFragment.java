package com.example.criminalcode;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;

public class CustomDialogFragment extends DialogFragment {


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Продолжить с последней страницы?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton("нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }})
                .setPositiveButton("да", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(getActivity(),pageRead.class);
                    intent.putExtra("Npage",readActivity.lastP);
                    startActivity(intent);                      //переход на экран чтения статей(pdf файл)
            }
        }).create();

    }
}