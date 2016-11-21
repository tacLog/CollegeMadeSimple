package edu.ucsc.makecollegesimple;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PieChartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PieChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PieChartFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String categoriesTotals = "param1";
    private static final String totalPie = "param2";

    private float[] cats = new float[5];
    private float totalCost;
    private PieChart pieChart;
    private OnFragmentInteractionListener mListener;

    public PieChartFragment() {
        // Required empty public constructor
    }


    public static PieChartFragment newInstance(float[] in, float Total) {
        PieChartFragment fragment = new PieChartFragment();
        Bundle args = new Bundle();
        args.putFloatArray(categoriesTotals, in);
        args.putFloat(totalPie, Total);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cats = getArguments().getFloatArray(categoriesTotals);
            totalCost = getArguments().getFloat(totalPie);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        //piechart slices are initialized at 10 for now
        //totalCost = suppliesCost + rentCost + transportationCost + tuitionCost + personalCost;

        //Pie chart
        //I used a library to create the pie chart, please refer to https://github.com/PhilJay/MPAndroidChart for documentation.
        pieChart = (PieChart) view.findViewById(R.id.CostsPieChart);
        //this is the y-axis
        // first parameter of Entry represents the amount in the category and second parameter is index numbered 0-4
        final ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(cats[0] , 0));  //supplies
        entries.add(new Entry (cats[1], 1));   //rent
        entries.add(new Entry(cats[2], 2));   //transportation
        entries.add(new Entry(cats[3], 3));  //tuition
        entries.add(new Entry(cats[4], 4));   //personal

        PieDataSet dataset = new PieDataSet(entries, "");

        //this is the x-axis
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Supplies");
        labels.add("Rent");
        labels.add("Transportation");
        labels.add("Tuition");
        labels.add("Personal");

        PieData data = new PieData(labels, dataset);

        pieChart.setCenterText("Costs: $"  + totalCost);
        pieChart.setCenterTextSize(15f);

        //appearance
        data.setValueTextSize(11f);
        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pieChart.setDescription(null);
        pieChart.setData(data);
        pieChart.animateY(2500);

        return view;
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