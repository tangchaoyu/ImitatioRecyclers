package com.myrecyclers.tcy.imitatiorecyclers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private var button: Button?=null
    private var button1: Button?=null
    private var button2: Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)

        button?.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.setClass(this,Demo1Activity::class.java)
            startActivity(intent)
        })
        button1?.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.setClass(this,Demo2Activity::class.java)
            startActivity(intent)
        })

        button2?.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.setClass(this,Demo3Activity::class.java)
            startActivity(intent)
        })


    }
}
