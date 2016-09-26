package com.hopop.hopop.alertdialog.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;

public class AlertDialogActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.SignUpHeader);
        setContentView(R.layout.activity_alert);
        AlertDialog alertDialog = new AlertDialog.Builder(
                AlertDialogActvity.this).create();
        // Setting Dialog Title
        alertDialog.setTitle("Registration Successful");
        // Setting Dialog Message
        alertDialog.setMessage("Welcome to the HopOp Family");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.tick);
        // Setting OK Button
        alertDialog.setButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // Write your code here to execute after dialog
                        // closed
                        // Toast.makeText(getApplicationContext(),
                        //       "You clicked on OK", Toast.LENGTH_SHORT)
                        //     .show();
                        Intent intent_1 = new Intent(AlertDialogActvity.this, LoginActivity.class);
                        startActivity(intent_1);
                    }
                });

        // Showing Alert Message
        alertDialog.show();

    }

}