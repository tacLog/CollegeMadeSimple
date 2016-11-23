package edu.ucsc.makecollegesimple;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private static final String[] editFlags = {"Supplies","Rent","Transportation","Tuition","Personal","Loans","Scholarships","Job","Other","Grants"};
    //index that user is editing, initialized at -1 when user first opens activity
    int currentlyEditing= -1;
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

        final TextView categoryTitle = (TextView) findViewById(R.id.categoryTitle);
        final EditText categoryName = (EditText) findViewById(R.id.newCategory);
        final EditText costAmount = (EditText) findViewById(R.id.newCost);
        final TextView displayCategory = (TextView) findViewById(R.id.displayCategory);
        final TextView displayCost = (TextView) findViewById(R.id.displayCost);
        final TextView totalSupplies = (TextView) findViewById(R.id.totalSupplies);
        final Button okButton = (Button) findViewById(R.id.okButton);
        final Button editButton0 = (Button) findViewById(R.id.editButton0);
        final Button editButton1 = (Button) findViewById(R.id.editButton1);
        final Button editButton2 = (Button) findViewById(R.id.editButton2);
        final Button editButton3 = (Button) findViewById(R.id.editButton3);
        final Button editButton4 = (Button) findViewById(R.id.editButton4);
        editButton0.setVisibility(View.GONE);
        editButton1.setVisibility(View.GONE);
        editButton2.setVisibility(View.GONE);
        editButton3.setVisibility(View.GONE);
        editButton4.setVisibility(View.GONE);


        upDateList(displayCategory, displayCost, totalSupplies);
        upDateEditButton(editButton0, editButton1, editButton2, editButton3, editButton4, categoryName, costAmount);

        categoryTitle.setText(editFlags[flag]);


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
                    int currentIndex;
                    if (currentlyEditing == -1){
                        currentIndex = getCurrentIndex();

                    }else{
                        currentIndex = currentlyEditing;
                    }
                    categories[currentIndex]= name;
                    values[currentIndex]= amount;
                    //reset the forms to blank and print
                    categoryName.setText("");
                    costAmount.setText("");
                    upDateList(displayCategory, displayCost, totalSupplies);
                    upDateEditButton(editButton0, editButton1, editButton2, editButton3, editButton4, categoryName, costAmount);
                    currentlyEditing = -1;
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

    private void upDateEditButton(Button editButton0, Button editButton1, Button editButton2, Button editButton3, Button editButton4, final EditText categoryName, final EditText costAmount){

        if (categories[0] != null){
            editButton0.setVisibility(View.VISIBLE);
            editButton0.setOnClickListener(new View.OnClickListener(){

                public void onClick (View v) {
                    categoryName.setText(categories[0]);
                    costAmount.setText(values[0]);
                    currentlyEditing = 0;
                }

            });
        }

        if (categories[1] != null){
            editButton1.setVisibility(View.VISIBLE);
            editButton1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View v) {
                    categoryName.setText(categories[1]);
                    costAmount.setText(values[1]);
                    currentlyEditing = 1;
                }
            });
        }
        if (categories[2] != null){
            editButton2.setVisibility(View.VISIBLE);
            editButton2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View v) {
                    categoryName.setText(categories[2]);
                    costAmount.setText(values[2]);
                    currentlyEditing = 2;
                }
            });
        }
        if (categories[3] != null){
            editButton3.setVisibility(View.VISIBLE);
            editButton3.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View v) {
                    categoryName.setText(categories[3]);
                    costAmount.setText(values[3]);
                    currentlyEditing = 3;
                }
            });
        }
        if (categories[4] != null){
            editButton4.setVisibility(View.VISIBLE);
            editButton4.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View v) {
                    categoryName.setText(categories[4]);
                    costAmount.setText(values[4]);
                    currentlyEditing = 4;
                }
            });
        }

    }

    private void upDateList(TextView displayCategory, TextView displayCost, TextView totalSupplies) {
        //loop through the categories list, convert each to string and concatenate them
        String currentCategory = "";
        String currentEdit = "";
        for (int i = 0; i < 5; i ++ ) {
            if (categories[i]== null || categories[i].equals("") || categories[i].isEmpty()){
                currentCategory = currentCategory+ "\n";
            }
            else{
                currentCategory = currentCategory + categories[i] + "\n";
            }
        }


        //loop through the cost list, convert each to string and concatenate them
        String currentCost = "";
        double currentcostSum = 0;
        for (int i =0; i< 5; i++) {
            if (values[i] == null || values[i].equals("") || values[i].isEmpty()) {
                currentCost = currentCost + "\n";
            } else {
                currentcostSum = currentcostSum + Float.valueOf(values[i]);
                currentCost = currentCost + "$" + String.format("%.2f", Double.valueOf(values[i])) + "\n";  //add each item in cost list to get the sum
            }
        }

        //display each list on the screen
        displayCategory.setText(currentCategory);
        displayCost.setText(currentCost);
        //display sum on the screen
        totalSupplies.setText("Total " + editFlags[flag] +" Cost: $" + String.format("%.2f",currentcostSum));
        suppliesSum = currentcostSum; //will be passed back to piechart activity
    }
    //when back button is pressed, return to pie chart activity
    @Override
    public void onBackPressed() {

        Intent newUserIntent = new Intent(CategoryEditActivity.this, MainMenu.class);
        newUserIntent.setFlags(flag);
        newUserIntent.putExtra(catIn, categories);
        newUserIntent.putExtra(valuesIn, values);
        CategoryEditActivity.this.startActivity(newUserIntent);

    }
}
