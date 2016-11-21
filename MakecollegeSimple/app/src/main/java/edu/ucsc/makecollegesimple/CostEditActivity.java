package edu.ucsc.makecollegesimple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 11/15/2016.
 */

public class CostEditActivity extends Activity {

    //lists for categories and costs entered by user, needs to be saved
    final ArrayList categoriesTotal = new ArrayList();
    final ArrayList<Double> costsTotal= new ArrayList<>();
    //sum of all items in cost list, later passed back to the costs piechart activity, might need to be saved
    double suppliesSum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_supplies);
        int flag = getIntent().getFlags();
        //retrieve widgets and assign variables to them
        final EditText categoryName = (EditText) findViewById(R.id.newCategory);
        final EditText costAmount = (EditText) findViewById(R.id.newCost);
        final TextView displayCategory = (TextView) findViewById(R.id.displayCategory);
        final TextView displayCost = (TextView) findViewById(R.id.displayCost);
        final TextView totalSupplies = (TextView) findViewById(R.id.totalSupplies);
        final Button okButton = (Button) findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                //only continue if both forms are not blank.
                if (categoryName.length() == 0 || costAmount.length() == 0){
                    return;
                }else if(categoryName.length() > 0) {
                    String name = categoryName.getText().toString();
                    String amount = costAmount.getText().toString();

                    //add the entered values into the lists
                    categoriesTotal.add(name);
                    costsTotal.add(Double.parseDouble(amount));

                    //reset the forms to blank
                    categoryName.setText("");
                    costAmount.setText("");

                    //loop through the categories list, convert each to string and concatenate them
                    String currentCategory = "";
                    for (int i = 0; i < categoriesTotal.size(); i ++ ) {
                        currentCategory = currentCategory + categoriesTotal.get(i).toString() + "\n";
                    }

                    //loop through the cost list, convert each to string and concatenate them
                    String currentCost = "";
                    double currentcostSum = 0;
                    for (int i =0; i< costsTotal.size(); i++){
                        currentcostSum = currentcostSum + costsTotal.get(i);
                        currentCost = currentCost + "$" + String.format("%.2f", costsTotal.get(i))+"\n";  //add each item in cost list to get the sum
                    }

                    //display each list on the screen
                    displayCategory.setText(currentCategory);
                    displayCost.setText(currentCost);
                    //display sum on the screen
                    totalSupplies.setText("Total Supplies Cost: $" + String.format("%.2f",currentcostSum));
                    suppliesSum = currentcostSum; //will be passed back to piechart activity

                }

            }

        });
    }
    //when back button is pressed, return to pie chart activity
    @Override
    public void onBackPressed() {
        Intent newUserIntent = new Intent(CostEditActivity.this, MainMenu.class);
        //Log.i("sum", String.valueOf(suppliesSum));
        newUserIntent.setFlags(0);
        newUserIntent.putExtra("suppliesCost", suppliesSum);//pass the suppliesSum variable along w/ the intent
        CostEditActivity.this.startActivity(newUserIntent);
    }
}
