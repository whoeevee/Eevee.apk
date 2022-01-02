package com.eevee

import android.annotation.SuppressLint
import android.app.Service
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.LocaleList
import androidx.lifecycle.lifecycleScope
import org.json.JSONObject as JS
import kotlinx.android.synthetic.main.activity_sig.*
import kotlinx.android.synthetic.main.activity_chat.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import android.view.View
import android.text.Editable
import android.os.Build
import android.provider.Settings
import com.gmail.samehadar.iosdialog.IOSDialog
import kotlinx.coroutines.*
import java.util.Base64
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.content.Intent
import android.os.IBinder
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_chat.view.*
import kotlinx.android.synthetic.main.activity_sig.activityTitleLayout
import kotlinx.android.synthetic.main.activity_sig.view.*
import kotlinx.android.synthetic.main.activity_verify.*
import org.json.JSONObject
import android.app.NotificationManager

import android.app.Notification
import android.app.NotificationChannel
import androidx.core.app.NotificationManagerCompat
import okhttp3.RequestBody.Companion.create
import android.app.PendingIntent

@DelicateCoroutinesApi
@SuppressLint("SetTextI18n")

class Util() {
    companion object {

        init {
            System.loadLibrary("eevee")
        }

        external fun g(): String // url verify/
        external fun g1(): String // l (language header)
        external fun g2(): String // w (deviceid header)
        external fun g3(): String // t (network type header)
        external fun g4(): String // u (uid header)
        external fun g5(): String // v (version header)
        external fun g6(): String // nickname
        external fun g7(): String // message
        external fun g8(): String // OK
        external fun g9(): String // uid
        external fun g10(): String // timestamp

        @SuppressLint("HardwareIds")
        fun getDeviceId(context: Context): String {
            return Base64.getEncoder().encodeToString(
                "${
                    Settings.Secure.getString(
                        context.contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                };${Build.FINGERPRINT}".toByteArray()
            ).replace("=", "")
        }

        fun getNetwork(context: Context): Int {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nw = connectivityManager.activeNetwork ?: return 0
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return 0
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> 1
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> 2
                else -> 0
            }
        }

        fun toEditable(text: String): Editable =
            Editable.Factory.getInstance().newEditable(text)

        suspend fun verify(context: Context, nickname: String): String {
            var str = ""
            withContext(Dispatchers.Main) {
                val a = IOSDialog.Builder(context)
                    .setCancelable(false)
                    .show()

            withContext(Dispatchers.IO) {

                val request = Request.Builder()
                    .url("${g()}${getDeviceId(context)}")
                    .addHeader(g1(), LocaleList.getDefault()[0].toString().substring(0, 2))
                    .addHeader(g3(), getNetwork(context).toString())
                    .addHeader(g5(), BuildConfig.VERSION_NAME)
                    .post(JSONObject().put(g6(), nickname).toString().toRequestBody())
                    .build()

                str = JS(OkHttpClient().newCall(request).execute().body!!.string())[g7()].toString()
                a.dismiss()
            }}
        return str
        }

        var uid = ""

    }
}

class Verify : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)
    }

    @DelicateCoroutinesApi
    fun verify(view: View) {
        lifecycleScope.launchWhenCreated {
            val message = Util.verify(this@Verify, nickname.text.toString())
            if (message != Util.g8()) {
                withContext(Dispatchers.Main) {
                    ib(this@Verify)
                        .setTitle(getString(R.string.error))
                        .setSubtitle(message)
                        .setPositiveListener(Util.g8()) {
                            it.dismiss()
                        }
                        .build()
                        .show()
                }
            }
            else {
                startActivity(Intent(this@Verify, SignatureTest::class.java))
            }
        }
    }
}

@DelicateCoroutinesApi
class ChatActivity : AppCompatActivity() {

    companion object {

        init {
            System.loadLibrary("eevee")
        }

    }

    private external fun g(): String // URL
    private external fun g4(): String // list
    private external fun g5(): String // author
    private external fun g7(): String // content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
    }

    override fun onBackPressed() {}

    fun toSignature(view: View) {
        startActivity(Intent(this@ChatActivity, SignatureTest::class.java))
    }

    private fun getLayoutParams(marginBottom: Int, marginTop: Int): LinearLayout.LayoutParams {

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.FILL_PARENT
        )
        params.setMargins(
            0,
            marginTop,
            0,
            marginBottom
        )

        return params

    }

    private suspend fun addMessage(nickname: String, text: String, IsMine: Boolean) {

        withContext(Dispatchers.Main) {

            val message = TextView(this@ChatActivity)
            message.text = text
            message.textSize = 20f

            message.layoutParams = getLayoutParams(20, 0)
            message.setTextColor(getColor(R.color.white))

            if (IsMine) {
                message.gravity = Gravity.END
                message.setBackgroundResource(R.drawable.chat_bubble_you)
            } else {
                message.setBackgroundResource(R.drawable.chat_bubble_other)
                val nick = TextView(this@ChatActivity)
                nick.text = nickname
                nick.textSize = 20f
                nick.layoutParams = getLayoutParams(10, 10)
                nick.setTextColor(getColor(R.color.gray2))
                chat_scroll_linear.addView(nick)
            }

            chat_scroll_linear.addView(message)

        }

    }

    fun sendMessage(view: View) {
        lifecycleScope.launchWhenCreated {

            withContext(Dispatchers.Main) {
                val a = IOSDialog.Builder(this@ChatActivity)
                    .setCancelable(false)
                    .show()

                withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url(g())
                        .addHeader(Util.g1(), LocaleList.getDefault()[0].toString().substring(0, 2))
                        .addHeader(Util.g2(), Util.getDeviceId(this@ChatActivity))
                        .addHeader(Util.g3(), Util.getNetwork(this@ChatActivity).toString())
                        .addHeader(Util.g4(), Util.uid)
                        .addHeader(Util.g5(), BuildConfig.VERSION_NAME)
                        .post(
                            JSONObject().put(Util.g7(), msginputtext.text).toString()
                                .toRequestBody()
                        )
                        .build()

                    val response =
                        JS(OkHttpClient().newCall(request).execute().body!!.string())[Util.g7()]

                    a.dismiss()
                    withContext(Dispatchers.Main) {
                        msginputtext.text = Util.toEditable("")
                        if (response != Util.g8()) {
                            ib(this@ChatActivity)
                                .setTitle(getString(R.string.error))
                                .setSubtitle(response.toString())
                                .setPositiveListener(Util.g8()) {
                                    it.dismiss()
                                }
                                .build()
                                .show()
                        }

                    }


                }

            }
        }
    }

    init {
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.Main) {
                if (Util.uid == "") {
                    startActivity(Intent(this@ChatActivity, SignatureTest::class.java))
                }
                window.statusBarColor = getColor(R.color.gray6)
                window.navigationBarColor = getColor(R.color.gray6)
                //
                //
                //
                val getMessages = Thread {
                    var oldlastmsgt = ""
                    var newlastmsgt = ""
                    while (true) {

                        val request = Request.Builder()
                            .url(g())
                            .addHeader(Util.g1(), LocaleList.getDefault()[0].toString().substring(0, 2))
                            .addHeader(Util.g2(), Util.getDeviceId(this@ChatActivity))
                            .addHeader(Util.g3(), Util.getNetwork(this@ChatActivity).toString())
                            .addHeader(Util.g4(), Util.uid)
                            .addHeader(Util.g5(), BuildConfig.VERSION_NAME)
                            .get()
                            .build()

                        val response = JS(
                            OkHttpClient().newCall(request).execute().body!!.string()
                        )

                        GlobalScope.launch {
                            withContext(Dispatchers.Main) {
                                chat_scroll_linear.removeAllViews()
                                for (i in 0 until response.getJSONArray(g4()).length()) {
                                    val message = response.getJSONArray(g4()).getJSONObject(i)
                                    newlastmsgt = message[Util.g10()].toString()
                                    addMessage(
                                        JS(message[g5()].toString())[Util.g6()].toString(),
                                        message[g7()].toString(),
                                        JS(message[g5()].toString())[Util.g9()] == Util.uid
                                    )
                                }

                                if (oldlastmsgt != newlastmsgt) {
                                    chat_scroll.scrollBy(0, 30)
                                    oldlastmsgt = newlastmsgt
                                }

                            }
                    }
                }
                }
                getMessages.start()
                //
                //
                //
                withContext(Dispatchers.IO) {
                }


            }

        }


    }

}

abstract class MessageService : Service() {

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
            val name ="чо"
            val descriptionText = "ничо!"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("wha", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    override fun onCreate() {

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.keys)
            .setContentTitle("My Notification Title")
            .setContentText("Something interesting happened")
        val NOTIFICATION_ID = 12345

        val targetIntent = Intent(this, SignatureTest::class.java)
        val contentIntent =
            PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(contentIntent)
        val nManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nManager.notify(NOTIFICATION_ID, builder.build())


    }

}

class SignatureTest : AppCompatActivity() {



    @DelicateCoroutinesApi
    fun openChat(view: View) {
        lifecycleScope.launchWhenCreated {
            when (device.text.toString()) {

                getString(R.string.unknown) -> {
                    ib(this@SignatureTest)
                        .setTitle(getString(R.string.verify))
                        .setSubtitle(getString(R.string.request_cert))
                        .setPositiveListener(getString(R.string.not_now)) {
                            it.dismiss()
                        }
                        .setNegativeListener(getString(R.string.request_cert_btn))
                        {
                            it.dismiss()
                            startActivity(Intent(this@SignatureTest, Verify::class.java))
                        }
                        .build1()
                        .show()
                }

                getString(R.string.not_certified) -> {

                    ib(this@SignatureTest)
                        .setTitle(getString(R.string.wait))
                        .setSubtitle(getString(R.string.device_already_requested))
                        .setPositiveListener(Util.g8()) {
                            it.dismiss()
                        }
                        .build()
                        .show()

                    GlobalScope.launch {
                        withContext(Dispatchers.Main) {
                            checkDevice()
                        }
                    }
                }
                else -> {
                    startService(Intent(this@SignatureTest, MessageService::class.java))
                    startActivity(Intent(this@SignatureTest, ChatActivity::class.java))
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sig)
    }

    fun PushNotification(context: Context) {
        val nm = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder = Notification.Builder(context)
        //set
        builder.setSmallIcon(R.drawable.keys)
        builder.setContentText("Contents")
        builder.setContentTitle("title")
        builder.setAutoCancel(true)
        builder.setDefaults(Notification.DEFAULT_ALL)
        val notification = builder.build()
        nm.notify(System.currentTimeMillis().toInt(), notification)
    }

    companion object {

        init {
            System.loadLibrary("eevee")
        }

    }

    @DelicateCoroutinesApi
    @SuppressLint("ResourceType")
    private external fun g(): String
    private external fun g1(): String
    private external fun g2(): String
    private external fun g3(): String
    private external fun g4(): String
    private external fun g5(): String
    private external fun g6(): String
    private external fun g8(): String
    private external fun g9(): String
    private external fun g10(): String
    private external fun g11(): String
    private external fun g12(): String
    private external fun g14(): String
    private external fun g16(): String
    private external fun g18(): String
    private external fun g19(): String
    private external fun g20(): String
    private external fun g23(): String
    private external fun g24(): String



    @DelicateCoroutinesApi
    suspend fun checkDevice() {
        withContext(Dispatchers.Main) {

            PushNotification(this@SignatureTest)

            //
            //
            //
            if (Util.getNetwork(this@SignatureTest) != 0) {
                val showAnimation = Thread {
                    runOnUiThread {
                        SIGVerifyButton.setBackgroundColor(getColor(R.color.gray3))
                        SIGVerifyButton.setStrokeColorResource(R.color.gray5)
                        chatButton.setBackgroundColor(getColor(R.color.gray3))
                        chatButton.setStrokeColorResource(R.color.gray5)

                        SIGVerifyButton.isEnabled = false
                        sigInputLayout.isEnabled = false
                        chatButton.isEnabled = false
                        dataInputLayout.isEnabled = false
                        device.text = getString(R.string.loading)
                    }

                    while (true) {
                        try {
                            runOnUiThread {
                                device.append(".")
                            }
                            Thread.sleep(100)
                        } catch (e: Exception) {
                            break
                        }
                    }
                }
                showAnimation.start()
                //
                //
                //

                // set background
                Thread {
                    val background = Drawable.createFromStream(
                        OkHttpClient().newCall(
                            Request.Builder()
                                .url(g18())
                                .get()
                                .build()
                        ).execute().body!!.byteStream(), null
                    )
                    runOnUiThread { govno.background = background }

                }.start()
                //
                //

                val request = Request.Builder()
                    .url("${Util.g()}${Util.getDeviceId(this@SignatureTest)}")
                    .addHeader(Util.g1(), LocaleList.getDefault()[0].toString().substring(0, 2))
                    .get()
                    .build()

                withContext(Dispatchers.IO) {
                    val response = JS(OkHttpClient().newCall(request).execute().body!!.string())
                    showAnimation.interrupt()
                    withContext(Dispatchers.Main) {

                        // window.statusBarColor = "#FFFFFF"
                        // activityTitleLayout.background = ColorDrawable("#FFFFFF"))

                        when (response[g16()]) {
                            0 -> {
                                device.text = getString(R.string.unknown)
                                sigInputLayout.visibility = View.VISIBLE
                            }

                            1 -> {
                                device.text = getString(R.string.certified)
                                sigInputLayout.visibility = View.GONE
                            }

                            2 -> {
                                device.text = getString(R.string.not_certified)
                                sigInputLayout.visibility = View.VISIBLE
                            }
                        }

                        Util.uid = response[Util.g9()].toString()

                        val color = Color.parseColor(response[g19()] as String?)
                        val color2 = Color.parseColor(response[g20()] as String?)

                        window.navigationBarColor = color2
                        window.statusBarColor = color2
                        //sigInputText.setTextColor(color)
                        //dataInputText.setTextColor(color)
                        activityTitleLayout.background = ColorDrawable(color)
                        SIGVerifyButton.setBackgroundColor(color)
                        SIGVerifyButton.strokeColor = ColorStateList.valueOf(color2)
                        chatButton.strokeColor = ColorStateList.valueOf(color2)
                        chatButton.setBackgroundColor(color)

                    }

                }


                SIGVerifyButton.isEnabled = true
                sigInputLayout.isEnabled = true
                chatButton.isEnabled = true
                dataInputLayout.isEnabled = true


            }

            else {

                ib(this@SignatureTest)
                                .setCancelable(true)
                                .setTitle(getString(R.string.no_internet))
                                .setSubtitle(getString(R.string.internet_required))
                                .setCancelable(false)
                                .setPositiveListener(getString(R.string.retry)) {
                                    it.dismiss()
                                    GlobalScope.launch {
                                        checkDevice()
                                    }
                                }
                                .build1()
                                .show()

            }

        }
    }

    init {
        lifecycleScope.launchWhenCreated {
                checkDevice()
        }
    }


    @DelicateCoroutinesApi
    private suspend fun verify(a: IOSDialog) {

        withContext(Dispatchers.IO) {
            OkHttpClient().newCall(
                Request.Builder()
                    .url("${Util.g()}${Util.getDeviceId(this@SignatureTest)}")
                    .post("".toRequestBody())
                    .build()
            ).execute()

        a.dismiss()

        }
    }

    @DelicateCoroutinesApi
    fun startSIGVerify(view: View) {
            // dataInputText.isEnabled = false
            // sigInputText.isEnabled = false
            // SIGVerifyButton.isEnabled = false


            val a = IOSDialog.Builder(this@SignatureTest)
                .setCancelable(false)
                .show()
       //  a.dismiss()

            lifecycleScope.launchWhenCreated {
                withContext(Dispatchers.IO) {
                    window.statusBarColor = getColor(R.color.gray6)

                    val host = g()
                    val siga = g1()

                    val data = dataInputText.text.toString()
                    var sig = sigInputText.text.toString()

                    val client = OkHttpClient()

                    suspend fun setText(s: String) {
                        withContext(Dispatchers.IO) {
                            withContext(Dispatchers.Main) {
                                sigInputText.text = Util.toEditable(s)
                            }
                            sig = s
                        }

                    }

                    val kek = Util.g1()

                    for (element in sig) {
                        if (element !in '\u0020'..'\u007e') {
                            setText(g10())
                            break
                        }
                    }

                    val request = Request.Builder()
                        .url(host)
                        .addHeader(kek, LocaleList.getDefault()[0].toString().substring(0, 2))
                        .addHeader(Util.g5(), BuildConfig.VERSION_NAME)
                        .addHeader(Util.g2(), Util.getDeviceId(this@SignatureTest))
                        .addHeader(Util.g3(), Util.getNetwork(this@SignatureTest).toString())
                        .post(
                            data.toRequestBody(data.toMediaTypeOrNull())
                        )

                    if (device.text.toString() == getString(R.string.certified)) {
                        request.addHeader(siga, Util.getDeviceId(this@SignatureTest))
                    } else {
                        request.addHeader(siga, sig)
                    }

                    val response = client.newCall(request.build())
                    val result = response.execute().body!!.string()

                    a.dismiss()

                    withContext(Dispatchers.Main) {

                        val l = g2()
                        val i = g3()
                        val o = g4()
                        val e = g5()
                        val y = g6()
                        val k = Util.g8()
                        val p = g8()
                        val c = g9()

                        val code = JS(result)
                            .get(l)

                        val msg = JS(result)
                            .get(c)

                        // Мама
                        if (code == getString(R.string.im)) {
                            ib(this@SignatureTest)
                                .setCancelable(true)
                                .setTitle(g12())
                                .setSubtitle(i)
                                .setCancelable(false)
                                .setPositiveListener(o) {
                                    a.show()
                                    //
                                    //
                                    val b = Thread {
                                        Thread.sleep(g14().toLong())
                                        a.dismiss()
                                        if ((1..2).random() == 1) {
                                            println((1..2).random())
                                            runOnUiThread {
                                                ib(this@SignatureTest)
                                                    .setCancelable(true)
                                                    .setTitle(e)
                                                    .setSubtitle(y)
                                                    .setCancelable(false)
                                                    .setPositiveListener(k) {
                                                        it.dismiss()
                                                    }
                                                    .build()
                                                    .show()
                                            }
                                        }
                                        else {
                                            runOnUiThread {
                                                ib(this@SignatureTest)
                                                    .setCancelable(true)
                                                    .setTitle(g23())
                                                    .setSubtitle(g24())
                                                    .setCancelable(false)
                                                    .setPositiveListener(k) {
                                                        it.dismiss()
                                                    }
                                                    .build()
                                                    .show()
                                            }
                                        }
                                            a.dismiss()
                                        }

                                    //
                                    //

                                    it.dismiss()
                                    b.start()
                                }

                                .setNegativeListener(p)
                                {
                                    it.dismiss()
                                }
                                .build()
                                .show()

                        }

                        // Не мама
                        else {

                            if (code == getString(R.string.incorrect_sig) && device.text.toString() == getString(
                                    R.string.unknown
                                )
                            ) {
                                ib(this@SignatureTest)
                                    .setCancelable(true)
                                    .setTitle(code.toString())
                                    .setSubtitle(getString(R.string.request_cert))
                                    .setCancelable(false)
                                    .setPositiveListener(getString(R.string.not_now)) {
                                        it.dismiss()
                                    }
                                    .setNegativeListener(getString(R.string.request_cert_btn))
                                    {
                                        it.dismiss()
                                        GlobalScope.launch {
                                            startActivity(Intent(this@SignatureTest, Verify::class.java))
                                        }
                                    }
                                    .build1()
                                    .show()

                            } else {
                                ib(this@SignatureTest)
                                    .setCancelable(true)
                                    .setTitle(code.toString())
                                    .setSubtitle(msg.toString())
                                    .setCancelable(false)
                                    .setPositiveListener(k) {
                                        it.dismiss()
                                    }
                                    .build1()
                                    .show()
                            }

                            checkDevice()

                        }

                    }
                }
            }


    }
    }






