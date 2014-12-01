package com.hans.localirk.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.hans.localirk.R;

public class DialogServerResponseError extends DialogFragment implements DialogInterface.OnClickListener {
    private static int _title, _message;

    public static DialogServerResponseError newInstance(int title, int message) {
        _title = title;
        _message = message;

        return new DialogServerResponseError();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle(_title)
                .setPositiveButton(R.string.ok, this)
                .setMessage(_message);
        return adb.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                break;
        }
    }
}