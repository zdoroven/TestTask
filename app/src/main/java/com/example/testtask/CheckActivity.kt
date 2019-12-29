package com.example.testtask

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_check.*

class CheckActivity : AppCompatActivity() {

    var bList = hashMapOf("" to 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)

        var field = findViewById<LinearLayout>(R.id.check_field)
        var totalamount = findViewById<TextView>(R.id.amount_of_products)
        var price = findViewById<TextView>(R.id.price)
        price.setText(intent.getStringExtra("price"))

        var list = intent.getStringExtra("products")
        var data = list.split(";")
        totalamount.setText((data.count() - 1).toString())

        var unique = data.distinct().toMutableList()

        for(prod in unique){
            if(prod != "") {
                var ind = 0
                data.forEach{
                    if(it == prod)
                        ind++
                }
                var separData = prod.split(":")

                bList[prod] = ind

                var layoutText = TextView(applicationContext)
                layoutText.setText(prod)
                layoutText.setLayoutParams(
                    LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                )
                field.addView(layoutText)

                var linear = LinearLayout(applicationContext)
                linear.setOrientation(LinearLayout.HORIZONTAL)
                linear.setLayoutParams(
                    LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                )

                var amount = TextView(applicationContext)
                amount.setText(ind.toString())
                amount.setLayoutParams(
                    LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                )

                var delBut = Button(applicationContext)
                delBut.setText("x")
                delBut.setLayoutParams(
                    LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                )
                delBut.setOnClickListener{
                    field.removeView(linear)
                    field.removeView(layoutText)
                    totalamount.setText((totalamount.text.toString().toInt() -
                            amount.text.toString().toInt()).toString())
                    price.setText((price.text.toString().toInt() -
                            amount.text.toString().toInt() * separData[1].toInt()).toString())
                    unique.removeAt(unique.indexOf(prod))
                    bList.remove(prod)
                }

                var minBut = Button(applicationContext)
                minBut.setText("-")
                minBut.setLayoutParams(
                    LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                )
                minBut.setOnClickListener{
                    if(amount.text != "1") {
                        amount.setText((amount.text.toString().toInt() - 1).toString())
                        totalamount.setText((totalamount.text.toString().toInt() - 1).toString())
                        price.setText((price.text.toString().toInt() - separData[1].toInt()).toString())
                        bList[prod] = bList[prod].toString().toInt() - 1
                    }
                }

                var maxBut = Button(applicationContext)
                maxBut.setText("+")
                maxBut.setLayoutParams(
                    LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                )
                maxBut.setOnClickListener{
                    amount.setText((amount.text.toString().toInt() + 1).toString())
                    totalamount.setText((totalamount.text.toString().toInt() + 1).toString())
                    price.setText((price.text.toString().toInt() + separData[1].toInt()).toString())
                    bList[prod] = bList[prod].toString().toInt() + 1
                }

                linear.addView(delBut)
                linear.addView(minBut)
                linear.addView(amount)
                linear.addView(maxBut)

                field.addView(linear)
            }
        }
    }

    override fun onBackPressed() {
        sendResults()
        super.onBackPressed()
    }

    private fun sendResults(){
        intent.putExtra("backprice", price.text.toString())
        intent.putExtra("backamount", amount_of_products.text.toString())
        var string = ""
        for(name in bList.keys)
            if(name != "")
                for(i in 1..bList[name].toString().toInt())
                    string+=name+";"
        intent.putExtra("backlist", string)
        setResult(Activity.RESULT_OK, intent)
    }
}