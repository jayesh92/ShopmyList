package com.example.startedservicexampleusingintentservice;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;


public class DownloadServiceActivity extends Activity {

   private Handler handler = new Handler() {
		
		public void handleMessage(Message message)
		{
			
			Object path = message.obj;
			if(message.arg1 == RESULT_OK && path !=null)
			{
				Toast.makeText(DownloadServiceActivity.this, "Downloaded "+path.toString(), Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(DownloadServiceActivity.this, "Download Failed", Toast.LENGTH_LONG).show();
			}
		}
	};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_service);
		Log.d("Debug", "On Click");
		Intent intent = new Intent(this, DownloadService.class);
		Messenger messenger = new Messenger(handler);
		intent.putExtra("MESSENGER", messenger);
		intent.setData(Uri.parse("http://developer.android.com/guide/components/services.html"));
		intent.putExtra("urlpath","http://developer.android.com/guide/components/services.html");
		startService(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_download_service, menu);
        return true;
    }


}
