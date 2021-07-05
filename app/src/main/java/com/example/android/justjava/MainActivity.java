package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    //SETS THE QUANTITY TO 2
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
        //If quantity is 100 it shows an error message
        if (quantity == 100) {
            //Show an error message
            Toast.makeText(this, "You cannot order more than 100 coffees", Toast.LENGTH_SHORT).show();
            //Exit this method
            return;
        }
        //The quantity increments one.
        quantity= quantity + 1;
        displayQuantity(quantity);
    }


    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        //If quantity is 0 it shows an error message
        if (quantity == 1) {
            //Show an error message
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //Exit this method
            return;
        }
        //The quantity decreases one.
        quantity = quantity - 1;
        displayQuantity(quantity);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //Cheecks if the whippedCreamBox is clicked
        CheckBox whippedCreamCheckBox =  findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //Cheecks if the ChocolateBox is clicked
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //Gets the name that has beeen written in the nameField
        EditText nameField = (EditText) findViewById(R.id.name_text_input);
        String name_text = nameField.getEditableText().toString();

        //Calculates the price
        int price = calculatePrice(hasWhippedCream, hasChocolate)  ;
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name_text);


        //Puts the information in a mail
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); //only email apps should handle this
        intent.putExtra(intent.EXTRA_SUBJECT, "Just Java order for: " + name_text);
        intent.putExtra(intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) !=null); {
            startActivity(intent);
        }

    }


    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        //Price of 1 cup of coffee
        int basePrice = 5;

        //adds 1$ if the user wants whipped cream
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        //adds 2$ if the user wants chocolate
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;

    }


    /**
     * Creates a summary of the order
     *
     * @param price of the order
     *
     * @param addWhippedCream is whether or not the user wants Whipped Cream topping
     *
     * @param addChocolate is wether or not the user want chocolate topping
     *
     * @return total price
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name_text){
       String priceMessage = "Name: " + name_text;
       priceMessage += "\nAdd whipped cream? " + addWhippedCream;
       priceMessage += "\nAdd chocolate? " + addChocolate;
       priceMessage += "\nQuantity= " + quantity;
       priceMessage += "\nTotal: " + price + "€";
       priceMessage += "\nThank you!";
       return priceMessage;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    


}


