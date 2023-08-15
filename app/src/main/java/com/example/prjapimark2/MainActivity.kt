package com.example.prjapimark2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import java.net.URL
import java.util.concurrent.Executors
data class PostUser(var ID: String, var Amount: String,var PracticeID:String)
data class Received(var received_ID:String,
                    var received_Amount:String,var received_PracticeID:String,
                    var message:String )

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Post()
    }
    fun Read()
    {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute{
            val url = URL ("https://wordapidata.000webhostapp.com/?getuserdb");
            val json = url.readText()
            val userList = Gson().fromJson(json,Array<User>::class.java).toList()

            Handler(Looper.getMainLooper()).post{
                Log.d("AddNewUser", "Plain Json Vars :" + json.toString())
                Log.d("AddNewUser", "Converted Json :" + userList.toString())
            }

        }
    }


    fun Post()
    {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute{
            val user = PostUser("15", "50000","123456")

            val (_, _, result) = "https://opsc.azurewebsites.net/Add.php".httpPost()
                .jsonBody(Gson().toJson(user).toString())
                .responseString()
            val Json = "["+result.component1()+"]"
            val userList = Gson().fromJson(Json,Array<Received>::class.java).toList()

            Handler(Looper.getMainLooper()).post{
                var Text = findViewById<TextView>(R.id.txtOutput)
                Text.setText(userList.toString())
            }

        }
    }
}