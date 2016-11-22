package edu.ucsc.makecollegesimple;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryEdit.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryEdit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryEdit extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static String Title = "param1";
   // private static ArrayList<String> catagories = new ArrayList();
    private static String[] catagories = new String[5];
    //private static ArrayList<String> values = new ArrayList();
    private static String[] values = new String[5];

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String header = "param1";
    private static final String catagoriesInString = "param2";
    private static final String valuesString = "param3";

    //sum of all items in cost list, later passed back to the costs piechart activity, might need to be saved
    double suppliesSum;

    private OnFragmentInteractionListener mListener;

    public CategoryEdit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param Title Parameter 1.
     * @param categoriesIn Parameter 2.
     * @return A new instance of fragment CategoryEdit.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryEdit newInstance(String Title, String[] categoriesIn, String[] values) {
        CategoryEdit fragment = new CategoryEdit();
        Bundle args = new Bundle();
        args.putString(header, Title);
        args.putStringArray(catagoriesInString, categoriesIn);
        args.putStringArray(valuesString,values);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Title = getArguments().getString(header);
            catagories = getArguments().getStringArray(catagoriesInString);
            values = getArguments().getStringArray(valuesString);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_edit, container, false);
        //retrieve widgets and assign variables to them
        final EditText categoryName = (EditText) view.findViewById(R.id.newCategory);
        final EditText costAmount = (EditText) view.findViewById(R.id.newCost);
        final TextView displayCategory = (TextView) view.findViewById(R.id.displayCategory);
        final TextView displayCost = (TextView) view.findViewById(R.id.displayCost);

        final TextView totalSupplies = (TextView) view.findViewById(R.id.totalSupplies);
        final Button okButton = (Button) view.findViewById(R.id.okButton);


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
        return view;
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
