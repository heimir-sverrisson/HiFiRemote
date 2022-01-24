package com.coolprimes.hifiremote;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PostThread extends Thread {
    private static final String TAG = "PostThread";
    private final LinkedBlockingQueue<String> messageQueue;
    private final RequestQueue queue;
    private boolean running;
    String url = "http://ir-blaster.local/ir";


    PostThread(LinkedBlockingQueue<String> messageQueue, Context context) {
        this.messageQueue = messageQueue;
        this.queue = Volley.newRequestQueue(context);
    }

    void stopRunning(){
        running = false;
    }

    public void run() {
        running = true;
        while (running) {
            String code = null;
            try {
                code = messageQueue.poll(100, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Log.e(TAG, "polling messageQueue: " + e.getLocalizedMessage());
            }
            if(code != null){
                httpSend(code);
            } else {
                Log.d(TAG, "no code to send");
            }
        }
    }

    // This method does a synchronous http post because the wifi-ir blaster
    // has a single threaded web server and is rather slow as well
    private void httpSend(String code) {
        String requestBody = "code=" + code;
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest sr = new StringRequest(
                Request.Method.POST,
                url,
                future,
                future) {
            @Override
            public byte[] getBody() {
                    return requestBody.getBytes(StandardCharsets.UTF_8);
            }
        };
        queue.add(sr);
        try {
            String response = future.get();
            Log.d(TAG, "response is: " + response);
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

    }
}
