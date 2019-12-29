package com.example.testtask

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.File
import android.widget.*
import android.widget.LinearLayout.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout.LayoutParams.WRAP_CONTENT


class ScrollingActivity : AppCompatActivity() {

    var listOfProd = ""
    var file = File.createTempFile("data", ".json")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        file.writeText("{\n" +
                "  \"product\" : [\n" +
                "      {\n" +
                "    \"title\" : \"coffee\",\n" +
                "    \"price\" : 100,\n" +
                "    \"sugar\" : 15,\n" +
                "    \"milk\" : 20,\n" +
                "    \"size\" : \"standart\",\n" +
                "    \"type\" : \"non alcohol\",\n" +
                "    \"description\" : \"beautiful coffee\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\" : \"tea\",\n" +
                "    \"price\" : 90,\n" +
                "    \"sugar\" : 15,\n" +
                "    \"milk\" : 20,\n" +
                "    \"size\" : \"standart\",\n" +
                "    \"type\" : \"non alcohol\",\n" +
                "    \"description\" : \"beautiful tea\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\" : \"cola\",\n" +
                "    \"price\" : 80,\n" +
                "    \"size\" : \"standart\",\n" +
                "    \"type\" : \"non alcohol\",\n" +
                "    \"description\" : \"beautiful cola\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\" : \"cola-zero\",\n" +
                "    \"price\" : 80,\n" +
                "    \"size\" : \"standart\",\n" +
                "    \"type\" : \"non alcohol\",\n" +
                "    \"description\" : \"sugar free cola\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\" : \"beer\",\n" +
                "    \"price\" : 110,\n" +
                "    \"size\" : \"standart\",\n" +
                "    \"type\" : \"no alcohol\",\n" +
                "    \"description\" : \"great beer for nice fellas\"\n" +
                "  },\n" +
                "  ]\n" +
                "}")

        var totalprice = findViewById<Button>(R.id.price)
        var alco_cb = findViewById<CheckBox>(R.id.cb)

        totalprice.setOnClickListener{
            val intent = Intent(this, CheckActivity::class.java)
            intent.putExtra("products", listOfProd)
            intent.putExtra("price", totalprice.text.toString())
            startActivityForResult(intent, 1)
        }

        var obj = JSONObject(file.readText())

        alco_cb.setOnClickListener{
            refreshLayout(obj)
        }
        refreshLayout(obj)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var price = findViewById<Button>(R.id.price)
        var amount = findViewById<TextView>(R.id.amount_of_products)

        if (data != null) {
            price.setText(data.getStringExtra("backprice"))
            amount.setText(data.getStringExtra("backamount"))
            listOfProd=data.getStringExtra("backlist")
        }
    }

    private fun refreshLayout(obj: JSONObject){
        var price = findViewById<Button>(R.id.price)
        var amount = findViewById<TextView>(R.id.amount_of_products)
        var layout = findViewById<LinearLayout>(R.id.field)
        var alco_cb = findViewById<CheckBox>(R.id.cb)

        layout.removeAllViews()

        var prodArr = obj.getJSONArray("product")

        for(ind in 0..(obj.getJSONArray("product").length() - 2)) {
            var prodData = ""
            var product = prodArr.getJSONObject(ind)
            if (product.getString("type") == "non alcohol" ||
                alco_cb.isChecked
            ) {
                var prodPrice = product.getInt("price")
                for (option in product.keys())
                    if (option != "sugar" && option != "milk" && option != "size")
                        prodData += option + ": " + product.getString(option) + "\n"

                var layoutText = TextView(applicationContext)
                layoutText.setText(prodData)
                layoutText.setLayoutParams(
                    LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                )
                layout.addView(layoutText)

                for (option in product.keys()) {
                    if (option == "sugar") {
                        var linear = LinearLayout(applicationContext)
                        linear.setOrientation(LinearLayout.HORIZONTAL)
                        linear.setLayoutParams(
                            LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                        )

                        var sugarText = TextView(applicationContext)
                        sugarText.setText("Sugar")
                        sugarText.setLayoutParams(
                            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                        )
                        linear.addView(sugarText)

                        var checkBox = CheckBox(applicationContext)
                        checkBox.setLayoutParams(
                            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                        )
                        linear.addView(checkBox)

                        checkBox.setOnClickListener {
                            if (checkBox.isChecked) {
                                prodPrice += product.getInt("sugar")
                            } else {
                                prodPrice -= product.getInt("sugar")
                            }
                        }
                        var sugarPrice = TextView(applicationContext)
                        sugarPrice.setText("+ " + product.getString("sugar"))
                        sugarPrice.setLayoutParams(
                            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                        )
                        linear.addView(sugarPrice)

                        layout.addView(linear)
                    }
                    if (option == "milk") {
                        var linear = LinearLayout(applicationContext)
                        linear.setOrientation(LinearLayout.HORIZONTAL)
                        linear.setLayoutParams(
                            LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                        )

                        var milkText = TextView(applicationContext)
                        milkText.setText("Milk")
                        milkText.setLayoutParams(
                            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                        )
                        linear.addView(milkText)

                        var checkBox = CheckBox(applicationContext)
                        checkBox.setLayoutParams(
                            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                        )
                        linear.addView(checkBox)

                        checkBox.setOnClickListener {
                            if (checkBox.isChecked) {
                                prodPrice += product.getInt("milk")
                            } else
                                prodPrice -= product.getInt("milk")
                        }
                        var milkPrice = TextView(applicationContext)
                        milkPrice.setText("+ " + product.getString("milk"))
                        milkPrice.setLayoutParams(
                            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                        )
                        linear.addView(milkPrice)

                        layout.addView(linear)
                    }
                    if (option == "size") {
                        var linear = LinearLayout(applicationContext)
                        linear.setOrientation(LinearLayout.HORIZONTAL)
                        linear.setLayoutParams(
                            LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                        )

                        var sizeText = TextView(applicationContext)
                        sizeText.setText("Double size")
                        sizeText.setLayoutParams(
                            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                        )
                        linear.addView(sizeText)

                        var checkBox = CheckBox(applicationContext)
                        checkBox.setLayoutParams(
                            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                        )
                        linear.addView(checkBox)

                        checkBox.setOnClickListener {
                            if (checkBox.isChecked) {
                                prodPrice += product.getInt("price")
                            } else {
                                prodPrice -= product.getInt("price")
                            }
                        }
                        var sizePrice = TextView(applicationContext)
                        sizePrice.setText("x 2")
                        sizePrice.setLayoutParams(
                            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                        )
                        linear.addView(sizePrice)

                        layout.addView(linear)
                    }
                }


                var addButton = Button(applicationContext)
                addButton.setLayoutParams(
                    LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                )
                addButton.setText(product.getString("title"))

                addButton.setOnClickListener {
                    listOfProd += product.getString("title")
                    if (prodPrice != product.getInt("price")) {
                        if (prodPrice == product.getInt("price") * 2)
                            listOfProd += " double"
                        else if (prodPrice == product.getInt("price") + product.getInt("sugar"))
                            listOfProd += " with sugar"
                        else if (prodPrice == product.getInt("price") + product.getInt("milk"))
                            listOfProd += " with milk"
                        else if (prodPrice == product.getInt("price") +
                            product.getInt("sugar") +
                            product.getInt("milk")
                        )
                            listOfProd += " with sugar with milk"
                        else if (prodPrice == product.getInt("price") * 2 + product.getInt("sugar"))
                            listOfProd += " double with sugar"
                        else if (prodPrice == product.getInt("price") * 2 + product.getInt("milk"))
                            listOfProd += " double with milk"
                        else if (prodPrice == product.getInt("price") * 2 + product.getInt("sugar")
                            + product.getInt("milk")
                        )
                            listOfProd += " double with sugar with milk"
                    }

                    listOfProd += ":" + prodPrice.toString() + ";"
                    amount.setText((amount.text.toString().toInt() + 1).toString())
                    price.setText((price.text.toString().toInt() + prodPrice).toString())
                }

                layout.addView(addButton)
            }
        }
    }
}