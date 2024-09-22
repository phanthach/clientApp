package com.example.clientapp.Service

import android.util.Log
import com.google.firebase.inappmessaging.FirebaseInAppMessagingClickListener
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage

class MyClickListenerBundles : FirebaseInAppMessagingClickListener {
    override fun messageClicked(inAppMessage: InAppMessage, action: Action) {
        val url = action.actionUrl
        val metadata = inAppMessage.campaignMetadata
        val dataBundle = inAppMessage.data
        Log.d(
            "MyClickListener",
            "url: $url , metadata: ${metadata?.campaignId} - ${metadata?.campaignName} - $dataBundle"
        )

    }
}
