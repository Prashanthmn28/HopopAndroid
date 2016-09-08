package com.hopop.hopop.sidenavigation.wallet.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.hopop.hopop.registration.activity.RegisterActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Wallet extends AppCompatActivity {
    private ProgressDialog progressDialog;
    String frmSplMob,lMob,rMob;
    Context context;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
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
      /*  String userMobileNum = LoginActivity.usrMobileNum;
        Log.i(getClass().getSimpleName(), "UserLogin Mobile Number:" + userMobileNum);*/
        lMob = LoginActivity.usrMobileNum;
        rMob = RegisterActivity.userMobNum;
        context = Wallet.this.getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        frmSplMob = sharedPreferences.getString("lMob",null);
        LoginUser loginUser = new LoginUser();

        if (lMob == null && rMob ==null)
        {
            loginUser.setMobile_number(frmSplMob);
            CommunicatorClass.getRegisterClass().forWallet(loginUser).enqueue(new Callback<WalletInfo>() {
                @Override
                public void onResponse(Call<WalletInfo> call, Response<WalletInfo> response) {
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
        else if (lMob ==null)
        {
            loginUser.setMobile_number(rMob);
            CommunicatorClass.getRegisterClass().forWallet(loginUser).enqueue(new Callback<WalletInfo>() {
                @Override
                public void onResponse(Call<WalletInfo> call, Response<WalletInfo> response) {
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
        else
        {
            loginUser.setMobile_number(lMob);
            CommunicatorClass.getRegisterClass().forWallet(loginUser).enqueue(new Callback<WalletInfo>() {
                @Override
                public void onResponse(Call<WalletInfo> call, Response<WalletInfo> response) {
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
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(Wallet.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                synchronized (this)
                {
                    int counter = 0;
                    while(counter <= 4)
                    {
                        this.wait(500);
                        counter++;
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

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result)
        {
            progressDialog.dismiss();
        }
    }
}