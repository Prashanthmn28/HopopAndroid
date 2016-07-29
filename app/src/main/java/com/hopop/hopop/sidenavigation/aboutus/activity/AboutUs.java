package com.hopop.hopop.sidenavigation.aboutus.activity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.hopop.hopop.login.activity.R;

public class AboutUs extends AppCompatActivity {
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        setTitle("AboutUs");
        //Initialize a LoadViewTask object and call the execute() method
        new LoadViewTask().execute();
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

        WebView view = (WebView) findViewById(R.id.webView);
        String text;
        text = "<html><body><p align=\"justify\">";
        text+= "At RedBeak, we all come here to solve the biggest problem in Travel Domain.\n" +
                "And yes!! We are Damn Serious about this stuff!!! Ever wondered!\n" +
                "Why Bangalore has the worst Traffic in India? It’s because Bangalore\n" +
                "lack in a good public and private Transportation system.Added to that\n" +
                "its not managed and organised properly. This problem planted a seed in\n" +
                "our minds which eventually started to grow and here we are right in front of You!!\n" +
                "You might think we have restricted ourselves to Bangalore... Nope!!!\n" +
                "The Tree always grows… We believe Bangalore is a good place to start.\n" +
                "We have a great team who are passionate about innovating things and also fun\n" +
                "and adventure loving. Finally, we dream of making India a better and a smart nation\n" +
                "where transportation will never become a problem for people. Well, you want to know\n" +
                "more about the things we are working on ??!!!.Just scroll down a bit...";
        text+= "</p></body></html>";
        view.loadData(text, "text/html", "utf-8");

    }

    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(AboutUs.this);
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
                        //Wait 850 milliseconds
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
