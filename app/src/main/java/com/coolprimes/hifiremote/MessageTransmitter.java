package com.coolprimes.hifiremote;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageTransmitter {
    private static final String TAG = "MessageTransmitter";
    private final LinkedBlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    private final Map<Integer, String> messageValues = new HashMap<Integer, String>(){{
        put(R.id.button_on, "3778978907");
        put(R.id.button_off, "3778941932");
        put(R.id.button_source_up, "3778984007");
        put(R.id.button_source_down, "3778959527");
        put(R.id.button_volume_up, "3778941422");
        put(R.id.button_volume_down, "3778949582");
        put(R.id.button_previous_track, "46505977807053");
        put(R.id.button_next_track, "46505977282757");
        put(R.id.button_play_pause, "46505977364614");
        put(R.id.button_stop, "46505977675971");
    }};
    private final PostThread pt;

    MessageTransmitter(Context ctx){
        pt = new PostThread(messageQueue, ctx);
        pt.start();
    }

    void add(int messageId){
        String code = messageValues.get(messageId);
        if(code == null){
            Log.e(TAG, "messageId not found: " + messageId);
        } else {
            Log.d(TAG, "queueing code: " + code);
            messageQueue.add(code);
        }
    }

    void shutdown(){
        pt.stopRunning();
    }
}
