package com.example.user.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity=0;
    int price = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TextView orderTextView = (TextView) findViewById(R.id.Order_Imformation);
        //orderTextView.setVisibility(View.INVISIBLE);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        quantity++;
        display(quantity);
        displayPrice();
        //TextView orderTextView = (TextView) findViewById(R.id.Order_Imformation);
        //orderTextView.setVisibility(View.INVISIBLE);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if(quantity!=0) quantity--;
        display(quantity);
        displayPrice();
        //TextView orderTextView = (TextView) findViewById(R.id.Order_Imformation);
        //orderTextView.setVisibility(View.INVISIBLE);
    }

    /**
     * This method calculates the price
     */
    public void calPrice(int number, boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;
        if (hasWhippedCream) basePrice += 1;
        if (hasChocolate) basePrice += 2;
        price = basePrice * number;
    }

    /**
     * This method checks the checkboxes statement
     */
    public void onclickCheckbox(View view) {
        displayPrice();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        calPrice(quantity, hasWhippedCream, hasChocolate);
        displayPrice();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order list for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, displayOrderSummary(name, hasWhippedCream, hasChocolate));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //displayMessage(displayOrderSummary(name,hasWhippedCream,hasChocolate));
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
    private void displayPrice() {
        TextView priceTextView = (TextView) findViewById(R.id.order_text_view);
        CheckBox wippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = wippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        calPrice(quantity, hasWhippedCream, hasChocolate);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(price));
    }

    /**
     * This method displays the Order Summary on the screen.
     */
    private String displayOrderSummary(String name, boolean cheakcream, boolean cheakchocolate) {
        Resources res = getResources();
        String orderSummary = String.format(res.getString(R.string.clientName), name);
        orderSummary += "\n" + getString(R.string.addWhippedCream) + cheakcream;
        orderSummary += "\n" + getString(R.string.addChocolate) + cheakchocolate;
        orderSummary += "\n" + getString(R.string.Quantity) + quantity;
        orderSummary += "\n" + getString(R.string.total) + price;
        orderSummary += "\n" + getString(R.string.thankYou);
        return orderSummary;
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderTextView = (TextView) findViewById(R.id.Order_Imformation);
//        orderTextView.setText(message);
//        orderTextView.setVisibility(View.VISIBLE);
//    }

}