package edu.ucsc.makecollegesimple;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Admin on 11/15/2016.
 */

public class CategoryEditActivity extends Activity {
    //input strings:
    private static final String catIn = "categories";
    private static final String valuesIn = "values";
    //local private varables
    double suppliesSum;
    private static String[] categories = new String[5];
    private static String[] values = new String[5];
    private static int flag;
    //header
    private static final String[] editFlags = {"Supplies","Rent","Transportation","Tution","Personal","Loans","Scholarships","Job","Other","Grants"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //load setup data
        flag = getIntent().getFlags();
        if(getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            categories = bundle.getStringArray(catIn);
            values = bundle.getStringArray(valuesIn);
        }


        final EditText categoryName = (EditText) findViewById(R.id.newCategory);
        final EditText costAmount = (EditText) findViewById(R.id.newCost);
        final TextView displayCategory = (TextView) findViewById(R.id.displayCategory);
        final TextView displayCost = (TextView) findViewById(R.id.displayCost);
        final TextView totalSupplies = (TextView) findViewById(R.id.totalSupplies);
        final Button okButton = (Button) findViewById(R.id.okButton);


        upDateList(displayCategory, displayCost, totalSupplies);



        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                //only continue if both forms are not blank.
                if (categoryName.length() == 0 || costAmount.length() == 0){
                    Context context = getApplicationContext();
                    CharSequence text = "Please fill both fields";
                    Toast toast1 = Toast.makeText(context, text,Toast.LENGTH_SHORT);
                    toast1.show();
                }else if(categoryName.length() > 0) {
                    String name = categoryName.getText().toString();
                    String amount = costAmount.getText().toString();
                    //get the index and update the arrays
                    int currentIndex = getCurrentIndex();
                    categories[currentIndex]= name;
                    values[currentIndex]= amount;
                    //reset the forms to blank and print
                    categoryName.setText("");
                    costAmount.setText("");
                    upDateList(displayCategory, displayCost, totalSupplies);
                }

            }

        });
    }
    private int getCurrentIndex() {
        int currentIndex = -1;
        for(int i = 0; i < categories.length; i++) {
            if (categories[i] != null) {
                if (categories[i].length() > 0) {
                    continue;
                } else {
                    currentIndex = i;
                    break;
                }
            }
            else {

                currentIndex =i;
                break;
            }
        }
        if (currentIndex == -1){
            Context context = getApplicationContext();
            CharSequence text = "Warning only 5 categories possible, Overwiting Catagory 1";
            Toast toast = Toast.makeText(context, text,Toast.LENGTH_SHORT);
            toast.show();
            currentIndex=0;
        }
        return currentIndex;
    }
    private void upDateList(TextView displayCategory, TextView displayCost, TextView totalSupplies) {
        //loop through the categories list, convert each to string and concatenate them
        String currentCategory = "";
        String currentEdit = "";
        for (int i = 0; i < 5; i ++ ) {
            if (categories[i]!= null ){
                currentCategory = currentCategory + categories[i].toString() + "\n";


            }
            else{
                currentCategory = currentCategory+ "\n";
            }
        }


        //loop through the cost list, convert each to string and concatenate them
        String currentCost = "";
        double currentcostSum = 0;
        for (int i =0; i< 5; i++) {
            if (values[i] != null) {
                currentcostSum = currentcostSum + Float.valueOf(values[i]);
                currentCost = currentCost + "$" + String.format("%.2f", Double.valueOf(values[i])) + "\n";  //add each item in cost list to get the sum
            } else {
                currentCost = currentCost + "\n";
            }
        }

        //display each list on the screen
        displayCategory.setText(currentCategory);
        displayCost.setText(currentCost);
        //display sum on the screen
        totalSupplies.setText("Total Supplies Cost: $" + String.format("%.2f",currentcostSum));
        suppliesSum = currentcostSum; //will be passed back to piechart activity
    }
    //when back button is pressed, return to pie chart activity
    @Override
    public void onBackPressed() {
        Intent newUserIntent = new Intent(CategoryEditActivity.this, MainMenu.class);
        //Log.i("sum", String.valueOf(suppliesSum));
        newUserIntent.setFlags(flag);
        newUserIntent.putExtra(catIn, categories);
        newUserIntent.putExtra(valuesIn, values);
        CategoryEditActivity.this.startActivity(newUserIntent);
    }
}
