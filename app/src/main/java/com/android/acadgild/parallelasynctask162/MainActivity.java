package com.android.acadgild.parallelasynctask162;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    //Declaring objects of Button, Progressbar & MyAsyncTask
    Button buttonStart;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;
    MyAsyncTask asyncTask1, asyncTask2, asyncTask3, asyncTask4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViewById for progressbars
        progressBar1 = (ProgressBar)findViewById(R.id.progressbar1);
        progressBar2 = (ProgressBar)findViewById(R.id.progressbar2);
        progressBar3 = (ProgressBar)findViewById(R.id.progressbar3);
        progressBar4 = (ProgressBar)findViewById(R.id.progressbar4);

        //findViewById for Start Button
        buttonStart = (Button)findViewById(R.id.start);

        //setOnClickListener for Start Button
        buttonStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Call execute method of asyncTask1 for progressBar1
                asyncTask1 = new MyAsyncTask(progressBar1);
                asyncTask1.execute();
                //Call StartAsyncTaskInParallel method of asyncTask2 for progressBar2 to start parallel with asyncTask1
                asyncTask2 = new MyAsyncTask(progressBar2);
                StartAsyncTaskInParallel(asyncTask2);
                //Call execute method of asyncTask3 for progressBar3
                asyncTask3 = new MyAsyncTask(progressBar3);
                asyncTask3.execute();
                //Call execute method of asyncTask4 for progressBar4
                asyncTask4 = new MyAsyncTask(progressBar4);
                asyncTask4.execute();
            }});
    }

    //Method StartAsyncTaskInParallel to start asynctask parallel
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void StartAsyncTaskInParallel(MyAsyncTask task) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }


    //MyAsyncTask class

    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        ProgressBar myProgressBar;
        //Constructor for class MyAsyncTask
        public MyAsyncTask(ProgressBar target) {
            myProgressBar = target;
        }

        //doInBackground method to publish progress
        @Override
        protected Void doInBackground(Void... params) {
            for(int i=0; i<100; i++){
                publishProgress(i);
                SystemClock.sleep(100);
            }
            return null;
        }

        //onProgressUpdate update progressbar
        @Override
        protected void onProgressUpdate(Integer... values) {
            myProgressBar.setProgress(values[0]);
        }

    }
}
