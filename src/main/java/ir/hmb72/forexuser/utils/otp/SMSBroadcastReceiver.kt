package ir.hmb72.forexuser.utils.otp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import javax.inject.Inject

class SMSBroadcastReceiver @Inject constructor() :BroadcastReceiver(){
    override fun onReceive(p0: Context?, p1: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == p1.action){
            val extras = p1.extras //-->?
            val status = extras?.get(SmsRetriever.EXTRA_STATUS)as Status //-->??
            when(status.statusCode){
                CommonStatusCodes.SUCCESS->{
                    val message = extras.get((SmsRetriever.EXTRA_SMS_MESSAGE)) as String //-->??
                    receiveMessage?.let { it(message) }
                }
            }

        }

    }

    private var receiveMessage:((String)-> Unit)? =null
    fun onReceiveMessage(listener:(String)->Unit ){
        receiveMessage = listener
    }
}