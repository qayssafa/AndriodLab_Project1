package com.example.andriodlab_project1.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.List;

public class MultiSelectSpinner {
    private AlertDialog.Builder builder;
    private String[] list;
    private boolean[] checkedItems;
    private ArrayList<Integer> selectedItems = new ArrayList<>();

    public MultiSelectSpinner(Context context, String[] list) {
        this.list = list;
        checkedItems = new boolean[list.length];

        builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Courses");
        builder.setMultiChoiceItems(list, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(which);
                } else {
                    selectedItems.remove((Integer.valueOf(which)));
                }
            }
        });
    }

    public void showDialog() {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // handle selected items
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public List<String> getSelectedItems() {
        List<String> selectedCourses = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            selectedCourses.add(list[selectedItems.get(i)]);
        }
        return selectedCourses;
    }
}
