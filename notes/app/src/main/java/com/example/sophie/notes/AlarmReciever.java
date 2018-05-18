package com.example.sophie.notes;

import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

public class AlarmReciever extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1  = new Intent(context,RingingAlarm.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Toast.makeText(context, "now", Toast.LENGTH_SHORT).show();

        intent1.putExtra("note",intent.getStringExtra("note"));
        intent1.putExtra("alarm",intent.getStringExtra("alarm"));
        intent1.putExtra("img",intent.getStringExtra("img"));
        context.startActivity(intent1);

    }
}
