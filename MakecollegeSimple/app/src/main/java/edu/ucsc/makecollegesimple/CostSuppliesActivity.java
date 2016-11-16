package edu.ucsc.makecollegesimple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Admin on 11/15/2016.
 */

public class CostSuppliesActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_supplies);

        final EditText categoryName = (EditText) findViewById(R.id.newCategory);
        final EditText costAmount = (EditText) findViewById(R.id.newCost);
        final TextView displayCategory = (TextView) findViewById(R.id.displayCategory);
        final TextView displayCost = (TextView) findViewById(R.id.displayCost);
        final TextView totalSupplies = (TextView) findViewById(R.id.totalSupplies);
        final Button okButton = (Button) findViewById(R.id.okButton);
        final Button confirmButton = (Button) findViewById(R.id.confirmButton);
        final ArrayList allCategoriesCosts = new ArrayList();
        final ArrayList<Double> costsTotal= new ArrayList<>();

        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                if (categoryName.length() == 0 || costAmount.length() == 0){
                    return;
                }else if(categoryName.length() > 0) {


                    String name = categoryName.getText().toString();
                    String amount = costAmount.getText().toString();


                    allCategoriesCosts.add(name);
                    //allCategoriesCosts.add(amount);
                    costsTotal.add(Double.parseDouble(amount));
                    categoryName.setText("");
                    costAmount.setText("");

                    String currentCategory = "";
                    for (int i = 0; i < allCategoriesCosts.size(); i ++ ) {
                        currentCategory = currentCategory + allCategoriesCosts.get(i).toString() + "\n";
                    }
                    Log.i("allcategorycosts", String.valueOf(allCategoriesCosts));

                    String currentCost = "";
                    double currentcostSum = 0;
                    for (int i =0; i< costsTotal.size(); i++){
                        currentcostSum = currentcostSum + costsTotal.get(i);
                        currentCost = currentCost + costsTotal.get(i).toString()+"\n";
                    }

                    Log.i("costsupplies", String.valueOf(currentCategory));
                    displayCategory.setText(currentCategory);
                    displayCost.setText(currentCost);
                    totalSupplies.setText("Total Supplies Cost: $" + currentcostSum);

                }

            }

        });

        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                Intent newUserIntent = new Intent(CostSuppliesActivity.this, MainMenu.class);
                //newUserIntent.putExtra("suppliesCost", currentcostSum);
                // telling LoginActivity to perform registerIntent
                CostSuppliesActivity.this.startActivity(newUserIntent);
            }
        });


    }
}
