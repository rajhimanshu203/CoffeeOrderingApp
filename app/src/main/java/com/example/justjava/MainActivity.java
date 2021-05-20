package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox  whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox) ;
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox) ;
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream,hasChocolate);
       String priceMessage = createOrderSummary(name,price,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, "JUST JAVA APP FOR " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
       displayMessage(priceMessage);

    }

    private int calculatePrice(boolean addWhippedCream , boolean addChocolate){
        int basePrice = 5;
        if(addWhippedCream)
        {
            basePrice = basePrice + 1;
        }

        if(addChocolate){
            basePrice =basePrice + 2;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if(quantity == 100){
            Toast.makeText(this, "You cannot have more than 100 cups of Coffees.", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity + 1;
        display(quantity);

    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity == 1){
            Toast.makeText(this, "You cannot have less than 1 cup of Coffee.", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);

    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private String createOrderSummary(String name ,int price,boolean addWhippedCream,boolean addChocolate)
    {
        String priceMessage = "Name : " + name;
        priceMessage = priceMessage + "\nAdd Whipped Cream ? " + addWhippedCream;
        priceMessage = priceMessage + "\nAdd Chocolate ? " + addChocolate;
        priceMessage = priceMessage + "\nQuantity :" + quantity;
        priceMessage = priceMessage + "\nTotal: $" + price;
        priceMessage = priceMessage + " \nThank You!!";
        return priceMessage;




    }


}