package com.hopop.hopop.sidenavigation.wallet.activity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hopop.hopop.communicators.CommunicatorClass;
import com.hopop.hopop.login.activity.LoginActivity;
import com.hopop.hopop.login.activity.R;
import com.hopop.hopop.login.data.LoginUser;
import com.hopop.hopop.payment.data.WalletInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Wallet extends AppCompatActivity {
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        //Initialize a LoadViewTask object and call the execute() method
        new LoadViewTask().execute();
        setTitle("Account Balance");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_keyboard_backspace_white_48px));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        String userMobileNum = LoginActivity.usrMobileNum;
        Log.i(getClass().getSimpleName(), "UserLogin Mobile Number:" + userMobileNum);


        LoginUser loginUser = new LoginUser();

        loginUser.setMobile_number(userMobileNum);

        CommunicatorClass.getRegisterClass().forWallet(loginUser).enqueue(new Callback<WalletInfo>() {
            @Override
            public void onResponse(Call<WalletInfo> call, Response<WalletInfo> response) {
                //Toast.makeText(Wallet.this, "Success", Toast.LENGTH_SHORT).show();

                Log.e(getClass().getSimpleName(), "Success");

                WalletInfo walletInfo = response.body();
                for (com.hopop.hopop.database.Wallet wallet : walletInfo.getWallet()) {
                    Log.i(getClass().getSimpleName(), "user wallet is:" + wallet.getBalance().toString());

                    TextView balance = (TextView) findViewById(R.id.textView_paise);

                    String bal = wallet.getBalance().toString();

                    balance.setText(bal + "Rs");
                }

            }

            @Override
            public void onFailure(Call<WalletInfo> call, Throwable t) {
                Log.i(getClass().getSimpleName(), "Failure");

            }
        });
    }

    @Bind(R.id.textView_paise)
    TextView balance;
    @Bind(R.id.editText_sa)
    EditText amount;
    @Bind(R.id.textView_cb)
    TextView currentBalance;
    @Bind(R.id.textView_lm)
    TextView loadMoney;

    @OnClick(R.id.textView_paise1)
    public void paiseUser1(View view) {

    }

    @OnClick(R.id.textView_paise2)
    public void paiseUser2(View view) {

    }

    @OnClick(R.id.textView_paise3)
    public void paiseUser3(View view) {

    }

    @OnClick(R.id.textView_paise4)
    public void paiseUser4(View view) {

    }

    @OnClick(R.id.button_add)
    public void addUser(View view) {

    }

    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(Wallet.this);
            //Set the dialog title to 'Loading...'
            //progressDialog.setTitle("Loading...");
            //Set the dialog message to 'Loading application View, please wait...'
            progressDialog.setMessage("Loading...");
            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(false);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(true);
            //The maximum number of items is 100
            progressDialog.setMax(100);
            //Set the current progress to zero
            progressDialog.setProgress(0);
            //Display the progress dialog
            progressDialog.show();
        }

        //The code to be executed in a background thread.
        @Override
        protected Void doInBackground(Void... params)
        {
            /* This is just a code that delays the thread execution 4 times,
             * during 850 milliseconds and updates the current progress. This
             * is where the code that is going to be executed on a background
             * thread must be placed.
             */
            try
            {
                //Get the current thread's token
                synchronized (this)
                {
                    //Initialize an integer (that will act as a counter) to zero
                    int counter = 0;
                    //While the counter is smaller than four
                    while(counter <= 4)
                    {
                        //Wait 500 milliseconds
                        this.wait(500);
                        //Increment the counter
                        counter++;
                        //Set the current progress.
                        //This value is going to be passed to the onProgressUpdate() method.
                        publishProgress(counter*25);
                    }
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        //Update the progress
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            //set the current progress of the progress dialog
            progressDialog.setProgress(values[0]);
        }

        //after executing the code in the thread
        @Override
        protected void onPostExecute(Void result)
        {
            //close the progress dialog
            progressDialog.dismiss();
            //initialize the View
            //setContentView(R.layout.content_booking);
        }
    }
}