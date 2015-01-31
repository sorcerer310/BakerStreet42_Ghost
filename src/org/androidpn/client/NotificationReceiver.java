/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.androidpn.client;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/** 
 * Broadcast receiver that handles push notification messages from the server.
 * This should be registered as receiver in AndroidManifest.xml. 
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public final class NotificationReceiver extends BroadcastReceiver {

    private static final String LOGTAG = LogUtil
            .makeLogTag(NotificationReceiver.class);

    //    private NotificationService notificationService;

    public NotificationReceiver() {
    }

    //    public NotificationReceiver(NotificationService notificationService) {
    //        this.notificationService = notificationService;
    //    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOGTAG, "NotificationReceiver.onReceive()...");
        String action = intent.getAction();
        Log.d(LOGTAG, "action=" + action);

        if (Constants.ACTION_SHOW_NOTIFICATION.equals(action)) {
            String notificationId = intent
                    .getStringExtra(Constants.NOTIFICATION_ID);
            String notificationApiKey = intent
                    .getStringExtra(Constants.NOTIFICATION_API_KEY);
            String notificationTitle = intent
                    .getStringExtra(Constants.NOTIFICATION_TITLE);
            String notificationMessage = intent
                    .getStringExtra(Constants.NOTIFICATION_MESSAGE);
            String notificationUri = intent
                    .getStringExtra(Constants.NOTIFICATION_URI);
            String notificationFrom = intent
            		.getStringExtra(Constants.NOTIFICATION_FROM);
            String packetId = intent
    				.getStringExtra(Constants.PACKET_ID);
            
            Log.d(LOGTAG, "notificationId=" + notificationId);
            Log.d(LOGTAG, "notificationApiKey=" + notificationApiKey);
            Log.d(LOGTAG, "notificationTitle=" + notificationTitle);
            Log.d(LOGTAG, "notificationMessage=" + notificationMessage);
            Log.d(LOGTAG, "notificationUri=" + notificationUri);

            if(NotificationReceiver.isScreenLocked(context)){
            	Notifier notifier = new Notifier(context);
            	notifier.notify(notificationId, notificationApiKey,
                    notificationTitle, notificationMessage, notificationUri,notificationFrom,packetId);
            }else{
            	Intent nintent = new Intent(context,NotificationDetailsActivity.class);
            	nintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	context.startActivity(nintent);
            }
        }
    }
    /**
     * 判断是否处于锁屏状态
     * @param c
     * @return	返回ture为锁屏,返回flase为未锁屏
     */
    public final static boolean isScreenLocked(Context c) {  
    	android.app.KeyguardManager mKeyguardManager = (KeyguardManager) c.getSystemService(c.KEYGUARD_SERVICE);  
    	return mKeyguardManager.inKeyguardRestrictedInputMode();  

    } 


}
