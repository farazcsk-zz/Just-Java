/*
 * Copyright (C) 2015 The Android Open Source Project
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
package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    // Number of cups of coffee ordered
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1;

        if (quantity > 100) {
            quantity = 100;
            Context context = getApplicationContext();
            CharSequence text = "Sorry! Maximum of 100 cups of coffee per customer!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        quantity = quantity - 1;
        if (quantity < 0) {
            quantity = 0;
            Context context = getApplicationContext();
            CharSequence text = "Sorry! That's cute but we have our own coffee! ";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox checkbox = (CheckBox) findViewById(R.id.notify_me_checkbox);
       boolean hasWhippedCream = checkbox.isChecked();
        CheckBox checkbox1 = (CheckBox) findViewById(R.id.notify_me_checkbox1);
        boolean hasChocolate = checkbox1.isChecked();
        EditText customer = (EditText) findViewById(R.id.name_view);
        String name = customer.getText().toString();

        int price = calulateprice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSumamry(price,hasWhippedCream,hasChocolate, name);
        composeEmail(name,priceMessage);
    }

    private int calulateprice( boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;



       if (addWhippedCream){
           basePrice = basePrice +1;
       }

        if (addChocolate) {
            basePrice = basePrice +2;
        }


        return quantity * basePrice ;
    }

    public String createOrderSumamry (int price,boolean hasWhippedCream, boolean hasChocolate, String name ) {

        String priceMessage = "Name: " + name;
           priceMessage += "\nAdd whipped cream? "+hasWhippedCream;
           priceMessage += "\nAdd chocolate? "+hasChocolate;
           priceMessage += "\nQuantity: " + quantity ;
           priceMessage +=   "\nTotal: $" + price;
           priceMessage += "\nThank you!";

        return priceMessage;
    }
    public void composeEmail(String name,String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for: "+name);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }





}
