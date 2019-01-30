package com.ingic.lmslawyer.ui.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MultiSpinner extends android.support.v7.widget.AppCompatSpinner implements
        DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {

    private ArrayList<String> names;
    private ArrayList<String> ids;
    private List<CharSequence> titles = new ArrayList<>();
    private boolean[] selected;
    private String defaultText;
    private MultiSpinnerListener listener;
    private int singleItemPosition = -1;

    private boolean isMultiple = false;

    private AlertDialog.Builder builder;

    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

        if (isChecked) {
            selected[which] = true;
        } else {
            selected[which] = false;
        }

    }

    public void setMultiListOption(boolean[] selected) {
        this.selected = selected;
    }

    public void setSingleListOption(int singleItemPosition) {
        this.singleItemPosition = singleItemPosition;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        String itemSelectedText = "";
        String itemSelectedId = "";

        builder = null;

        if (isMultiple) {
            StringBuffer itemNames = new StringBuffer();
            StringBuffer itemIds = new StringBuffer();
            for (int i = 0; i < names.size(); i++) {
                if (selected[i] == true) {
                    itemNames.append(names.get(i));
                    itemNames.append(", ");

                    itemIds.append(ids.get(i));
                    itemIds.append(", ");
                }
            }

            if (itemNames.length() > 0) {
                itemSelectedText = itemNames.toString();
                itemSelectedId = itemIds.toString();

                if (itemSelectedText.length() > 2) {
                    itemSelectedText = itemSelectedText.substring(0, itemSelectedText.length() - 2);
                }
                if (itemSelectedId.length() > 2) {
                    itemSelectedId = itemSelectedId.substring(0, itemSelectedId.length() - 2);
                }
                //listener.onItemsSelected(String.valueOf(this.getTag()), itemSelectedId.trim());
            } else {
                itemSelectedText = defaultText;
            }
        } else {
            if (singleItemPosition != -1) {
                itemSelectedId = ids.get(singleItemPosition);
                itemSelectedText = names.get(singleItemPosition);
            } else {
                itemSelectedText = defaultText;
                itemSelectedId = "";
            }


            //listener.onItemsSelected(String.valueOf(this.getTag()), itemSelectedId.trim());
        }

        listener.onItemsSelected(String.valueOf(this.getTag()), itemSelectedId.trim());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[]{itemSelectedText});
        setAdapter(adapter);

    }


    public boolean performClick(boolean isMultiple) {
        this.isMultiple = isMultiple;
        try {

            if (builder == null) {
                builder = new AlertDialog.Builder(getContext());
                if (!isMultiple) {
                    builder.setSingleChoiceItems(titles.toArray(new CharSequence[titles.size()]), singleItemPosition, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            singleItemPosition = which;
                        }
                    });
                } else {
                    builder.setMultiChoiceItems(
                            titles.toArray(new CharSequence[titles.size()]), selected, this);
                }

                builder.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                builder.setOnCancelListener(this);
                builder.show();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setItems(ArrayList<String> names, ArrayList<String> ids, String defaultText,
                         MultiSpinnerListener listener) {

        this.names = names;
        this.ids = ids;
        this.defaultText = defaultText;
        this.listener = listener;

        for (int i = 0; i < names.size(); i++) {
            titles.add(names.get(i));
        }

        // all un selected by default
        selected = new boolean[names.size()];
        for (int i = 0; i < selected.length; i++) {
            selected[i] = false;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, new String[]{defaultText});
        setAdapter(adapter);

    }

    public interface MultiSpinnerListener {
        public void onItemsSelected(String tag, String ids);
    }
}