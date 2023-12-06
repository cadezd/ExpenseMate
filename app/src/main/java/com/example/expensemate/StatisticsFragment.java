package com.example.expensemate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemate.adapters.TransactionViewAdapter;
import com.example.expensemate.constants.Constants;
import com.example.expensemate.databse.entities.User;
import com.example.expensemate.model.UserTransactionModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;


public class StatisticsFragment extends Fragment {

    public StatisticsFragment() {
        // Required empty public constructor
    }

    // DECLARE COMPONENTS
    BarChart graph;
    RadioGroup radioGroup;
    ImageView imgVSort;
    TextView txtvSortTitle;
    RecyclerView rvTransactionsSorted;


    // DECLARE VARIABLES
    boolean isDescending = false;
    UserTransactionModel transactionModel;
    User user;
    TransactionViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        // DECLARING COMPONENTS
        graph = view.findViewById(R.id.graph);
        radioGroup = view.findViewById(R.id.radioGroup);
        imgVSort = view.findViewById(R.id.imgVSort);
        txtvSortTitle = view.findViewById(R.id.txtvSortTitle);
        rvTransactionsSorted = view.findViewById(R.id.rvTransactionsSorted);

        // DECLARING VARIABLES
        adapter = new TransactionViewAdapter();
        rvTransactionsSorted.setAdapter(adapter);
        // setting layout manager to our adapter class.
        rvTransactionsSorted.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTransactionsSorted.setHasFixedSize(true);

        // Get the user from the intent
        Intent intent = getActivity().getIntent();
        user = (User) intent.getSerializableExtra(Constants.USER_TAG);

        // Initialize the transaction model
        transactionModel = new UserTransactionModel(getActivity().getApplication(), user.getId());

        // DEFAULT: Display the top spending
        txtvSortTitle.setText("Top spending");
        transactionModel.getUserTopSpending().observe(getViewLifecycleOwner(), userTransactions -> {
            adapter.submitList(userTransactions);
        });

        // DEFAULT: Get the daily analysis for the current week and add it to the graph
        transactionModel.getWeeklyIncomeAndExpenses().observe(getViewLifecycleOwner(), dailyAnalyses -> {

            Log.d("TEST", dailyAnalyses.toString());

            // Graph entries
            ArrayList<BarEntry> barEntriesIncomes = new ArrayList<>();
            ArrayList<BarEntry> barEntriesExpenses = new ArrayList<>();
            // Graph labels
            ArrayList<String> labels = new ArrayList<>();
            int last = dailyAnalyses.size() - 1;
            for (int i = 0; i < dailyAnalyses.size() - 1; i++) {
                labels.add(dailyAnalyses.get(i).getDay());
                barEntriesIncomes.add(new BarEntry(i, dailyAnalyses.get(i).getIncome()));
                barEntriesExpenses.add(new BarEntry(i, dailyAnalyses.get(i).getExpense()));
            }

            if (dailyAnalyses.size() > 0) {
                labels.add(0, dailyAnalyses.get(last).getDay());
                barEntriesIncomes.add(0, new BarEntry(last, dailyAnalyses.get(last).getIncome()));
                barEntriesExpenses.add(0, new BarEntry(last, dailyAnalyses.get(last).getExpense()));
            }

            updateGraph(graph, barEntriesIncomes, barEntriesExpenses, labels);
        });


        // SETTING CLICK LISTENERS
        radioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            // Graph entries
            ArrayList<BarEntry> barEntriesIncomes = new ArrayList<>();
            ArrayList<BarEntry> barEntriesExpenses = new ArrayList<>();
            // Graph labels
            ArrayList<String> labels = new ArrayList<>();

            if (checkedId == R.id.rbWeek) {
                // Get the daily analysis for the current week and displaying it on the graph
                transactionModel.getWeeklyIncomeAndExpenses().observe(getViewLifecycleOwner(), dailyAnalyses -> {

                    Log.d("TEST", dailyAnalyses.toString());

                    int last = dailyAnalyses.size() - 1;
                    for (int i = 0; i < dailyAnalyses.size() - 1; i++) {
                        labels.add(dailyAnalyses.get(i).getDay());
                        barEntriesIncomes.add(new BarEntry(i, dailyAnalyses.get(i).getIncome()));
                        barEntriesExpenses.add(new BarEntry(i, dailyAnalyses.get(i).getExpense()));
                    }
                    if (dailyAnalyses.size() > 0) {
                        labels.add(0, dailyAnalyses.get(last).getDay());
                        barEntriesIncomes.add(0, new BarEntry(last, dailyAnalyses.get(last).getIncome()));
                        barEntriesExpenses.add(0, new BarEntry(last, dailyAnalyses.get(last).getExpense()));
                    }

                    updateGraph(graph, barEntriesIncomes, barEntriesExpenses, labels);
                });
            } else if (checkedId == R.id.rbMonth) {
                // Get the weekly analysis for the current month and displaying it on the graph
                transactionModel.getMonthlyIncomeAndExpenses().observe(getViewLifecycleOwner(), weeklyAnalyses -> {

                    Log.d("TEST", weeklyAnalyses.toString());


                    for (int i = 0; i < weeklyAnalyses.size(); i++) {
                        labels.add(weeklyAnalyses.get(i).getWeek());
                        barEntriesIncomes.add(new BarEntry(i, weeklyAnalyses.get(i).getIncome()));
                        barEntriesExpenses.add(new BarEntry(i, weeklyAnalyses.get(i).getExpense()));
                    }
                    updateGraph(graph, barEntriesIncomes, barEntriesExpenses, labels);
                });
            } else if (checkedId == R.id.rbYear) {
                // Get the monthly analysis for the current year and displaying it on the graph
                transactionModel.getYearlyIncomeAndExpenses().observe(getViewLifecycleOwner(), monthlyAnalyses -> {
                    for (int i = 0; i < monthlyAnalyses.size(); i++) {
                        labels.add(monthlyAnalyses.get(i).getMonth());
                        barEntriesIncomes.add(new BarEntry(i, monthlyAnalyses.get(i).getIncome()));
                        barEntriesExpenses.add(new BarEntry(i, monthlyAnalyses.get(i).getExpense()));
                    }
                    updateGraph(graph, barEntriesIncomes, barEntriesExpenses, labels);
                });
            } else {
                // Get the yearly analysis for the current decade and displaying it on the graph
                transactionModel.getDecadeIncomeAndExpenses().observe(getViewLifecycleOwner(), yearlyAnalyses -> {

                    Log.d("TEST", yearlyAnalyses.toString());

                    for (int i = 0; i < yearlyAnalyses.size(); i++) {
                        labels.add(yearlyAnalyses.get(i).getYear());
                        barEntriesIncomes.add(new BarEntry(i, yearlyAnalyses.get(i).getIncome()));
                        barEntriesExpenses.add(new BarEntry(i, yearlyAnalyses.get(i).getExpense()));
                    }
                    updateGraph(graph, barEntriesIncomes, barEntriesExpenses, labels);
                });
            }

        });

        imgVSort.setOnClickListener(v -> {
            if (isDescending) {
                txtvSortTitle.setText("Top spending");
                transactionModel.getUserTopSpending().observe(getViewLifecycleOwner(), userTransactions -> {
                    adapter.submitList(userTransactions);
                });
                isDescending = !isDescending;
            } else {
                txtvSortTitle.setText("Top income");
                transactionModel.getUserTopIncome().observe(getViewLifecycleOwner(), userTransactions -> {
                    adapter.submitList(userTransactions);
                });
                isDescending = !isDescending;
            }
        });

        return view;
    }

    private void updateGraph(BarChart barChart, ArrayList<BarEntry> barEntriesIncomes, ArrayList<BarEntry> barEntriesExpenses, ArrayList<String> labels) {
        // START SOURCE : https://www.youtube.com/watch?v=Bd76zMHdrDE

        BarDataSet income;
        BarDataSet expense;

        if (barEntriesIncomes.size() == 0 && barEntriesExpenses.size() == 0) {
            barEntriesIncomes.add(new BarEntry(0, 0));
            barEntriesExpenses.add(new BarEntry(0, 0));

            // Graph data sets for incomes
            income = new BarDataSet(barEntriesIncomes, "Income");
            income.setColor(Color.parseColor("#25A969"));
            income.setValueTextSize(0f);

            // Graph data sets for expenses
            expense = new BarDataSet(barEntriesExpenses, "Expense");
            expense.setColor(Color.parseColor("#F95B51"));
            expense.setValueTextSize(0f);

        } else {
            // Graph data sets for incomes
            income = new BarDataSet(barEntriesIncomes, "Income");
            income.setColor(Color.parseColor("#25A969"));
            income.setValueTextSize(10f);

            // Graph data sets for expenses
            expense = new BarDataSet(barEntriesExpenses, "Expense");
            expense.setColor(Color.parseColor("#F95B51"));
            expense.setValueTextSize(10f);
        }

        // Add the data sets to the graph
        BarData data = new BarData(income, expense);
        barChart.setData(data);
        // Make the graph scrollable
        barChart.setDragEnabled(true);
        // Display 3 bars at a time
        barChart.setVisibleXRangeMaximum(4);
        // Make the graph font size bigger
        barChart.getXAxis().setTextSize(14f);
        barChart.getAxisLeft().setTextSize(14f);
        barChart.getAxisRight().setTextSize(14f);
        barChart.getLegend().setTextSize(14f);

        // Adding labels to the graph (X axis)
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        xAxis.setAxisMinimum(100);

        // Adding space between the bars and the groups
        float barSpace = 0.08f;
        float groupSpace = 0.44f;
        data.setBarWidth(0.2f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * 5);
        barChart.getAxisLeft().setAxisMinimum(0);
        // Display the bars in groups
        barChart.groupBars(0, groupSpace, barSpace);

        // Remove label description
        barChart.setDescription(null);

        // Add more margin to the legend
        barChart.setExtraBottomOffset(20f);

        // Move the graph to the left
        barChart.moveViewToX(0);

        // Deisplay the graph
        barChart.invalidate();

        // END SOURCE : https://www.youtube.com/watch?v=Bd76zMHdrDE
    }
}