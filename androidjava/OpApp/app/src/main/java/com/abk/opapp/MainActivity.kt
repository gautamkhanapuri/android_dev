package com.abk.opapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etA: EditText
    private lateinit var etB: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnSubtract: Button
    private lateinit var btnMultiply: Button
    private lateinit var btnDivide: Button
    private lateinit var resultTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        etA = findViewById(R.id.et_a)
        etB = findViewById(R.id.et_b)
        btnAdd = findViewById(R.id.btn_add)
        btnSubtract = findViewById(R.id.btn_subtract)
        btnMultiply = findViewById(R.id.btn_multiply)
        btnDivide = findViewById(R.id.btn_divide)
        resultTv = findViewById(R.id.result_tv)
        btnAdd.setOnClickListener(this)
        btnSubtract.setOnClickListener(this)
        btnMultiply.setOnClickListener(this)
        btnDivide.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val a = etA.text.toString().toDouble()
        val b = etB.text.toString().toDouble()
        var res = 0.0
        when(v?.id) {
            R.id.btn_add -> {
                res = a + b
            }
            R.id.btn_subtract -> {
                res = a - b
            }
            R.id.btn_multiply -> {
                res = a * b
            }
            R.id.btn_divide -> {
                res = a/b
            }
        }
        resultTv.text = getString(R.string.resultis, res)
    }

    //style="?android:actionButtonStyle"
}