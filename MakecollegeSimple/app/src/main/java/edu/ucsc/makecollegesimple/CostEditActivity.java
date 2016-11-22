package edu.ucsc.makecollegesimple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Admin on 11/15/2016.
 */

public class CostEditActivity extends Activity {


    double suppliesSum;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static String Title = "param1";
    // private static ArrayList<String> catagories = new ArrayList();
    private static String[] catagories = new String[5];
    //private static ArrayList<String> values = new ArrayList();
    private static String[] values = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        int flag = getIntent().getFlags();
        final EditText categoryName = (EditText) findViewById(R.id.newCategory);
        final EditText costAmount = (EditText) findViewById(R.id.newCost);
        final TextView displayCategory = (TextView) findViewById(R.id.displayCategory);
        final TextView displayCost = (TextView) findViewById(R.id.displayCost);

        final TextView totalSupplies = (TextView) findViewById(R.id.totalSupplies);
        final Button okButton = (Button) findViewById(R.id.okButton);


        //upDateList(displayCategory, displayCost, totalSupplies);



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
                    // catagories.add(name);
                    // values.add(amount);
                    int currentIndex = -1;
                    for(int i =0; i < catagories.length; i++) {
                        if (catagories[i] != null) {
                            if (catagories[i].length() > 0) {
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

                        currentIndex=0;
                    }

                    catagories[currentIndex]= name;
                    values[currentIndex]= amount;



                    //reset the forms to blank
                    categoryName.setText("");
                    costAmount.setText("");
                    upDateList(displayCategory, displayCost, totalSupplies);


                }

            }

        });
    }
    private void upDateList(TextView displayCategory, TextView displayCost, TextView totalSupplies) {
        //loop through the categories list, convert each to string and concatenate them
        String currentCategory = "";
        String currentEdit = "";
        for (int i = 0; i < 5; i ++ ) {
            if (catagories[i]!= null ){
                currentCategory = currentCategory + catagories[i].toString() + "\n";


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
        Intent newUserIntent = new Intent(CostEditActivity.this, MainMenu.class);
        //Log.i("sum", String.valueOf(suppliesSum));
        newUserIntent.setFlags(0);
        newUserIntent.putExtra("suppliesCost", suppliesSum);//pass the suppliesSum variable along w/ the intent
        CostEditActivity.this.startActivity(newUserIntent);
    }
}
