package com.ingic.lmslawyer.ui.adapters;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.ingic.lmslawyer.ui.viewbinders.abstracts.RecyclerViewBinder;

import java.util.ArrayList;
import java.util.List;

public class FilterableRecyclerAdapter<T> extends RecyclerListAdapter<T> implements Filterable {

    FilterableRecyclerAdapter.ToStringFilter toStringFilter = new FilterableRecyclerAdapter.ToStringFilter();
    private List<T> originalList;
    private RecyclerViewBinder<T> viewBinder;
    private Context mContext;

    public FilterableRecyclerAdapter(List<T> collections, RecyclerViewBinder<T> viewBinder, Context context, int mLayoutResouceID, Function<T, String> converter) {

        super(context, collections, viewBinder);

        this.originalList = collections;
        this.viewBinder = viewBinder;
        this.mContext = context;
        this.mLayoutResouceID = mLayoutResouceID;

        originalList = new ArrayList<T>(collections);
        if (converter != null)
            toStringFilter = new FilterableRecyclerAdapter.ToStringFilter(converter);

    }

    @Override
    public void add(T entity) {
        originalList.add(entity);
        notifyDataSetChanged();
    }

    @Override
    public void addAll(List<T> entityList) {
        originalList.addAll(entityList);
        super.addAll(entityList);
    }


    @Override
    public Filter getFilter() {
        return toStringFilter;
    }

    public class ToStringFilter extends Filter {

        Function<T, String> converter;
        private CharSequence lastConstrant;

        public ToStringFilter(Function<T, String> converter) {
            this.converter = converter;
        }

        public ToStringFilter() {
        }

        protected void notifyFilter() {
            filter(lastConstrant);
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            this.lastConstrant = constraint;
            FilterResults results = new FilterResults();

            if (Strings.isNullOrEmpty(constraint.toString())) {
                results.count = originalList.size();
                results.values = new ArrayList<T>(originalList);
                return results;
            }

            ArrayList<T> filterList = new ArrayList<T>();
            constraint = constraint.toString().toLowerCase();
            for (int i = 0; i < originalList.size(); i++) {
                if (converter != null) {
                    String apply = converter.apply(originalList.get(i));
                    if (apply.toLowerCase().contains(constraint)) {
                        filterList.add(originalList.get(i));
                    }

                } else if (originalList.get(i).toString().toLowerCase()
                        .contains(constraint.toString())) {
                    filterList.add(originalList.get(i));
                }

            }
            results.count = filterList.size();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList = (List<T>) results.values;
            notifyDataSetChanged();
        }
    }

}
