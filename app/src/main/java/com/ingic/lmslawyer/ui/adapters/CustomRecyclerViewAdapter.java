package com.ingic.lmslawyer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ingic.lmslawyer.ui.viewbinders.abstracts.CustomRecyclerViewBinder;

import java.util.List;


public class CustomRecyclerViewAdapter<T> extends RecyclerView.Adapter<CustomRecyclerViewBinder.BaseViewHolder> {
    private List<T> collections;
    private CustomRecyclerViewBinder<T> viewBinder;
    private Context mContext;




    public CustomRecyclerViewAdapter(List<T> collections, CustomRecyclerViewBinder<T> viewBinder, Context context) {
        this.collections = collections;
        this.viewBinder = viewBinder;
        this.mContext = context;

    }

    @Override
    public CustomRecyclerViewBinder.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return (CustomRecyclerViewBinder.BaseViewHolder) this.viewBinder.createViewHolder(this.viewBinder.createView(this.mContext));
    }

    @Override
    public void onBindViewHolder(CustomRecyclerViewBinder.BaseViewHolder holder, int position) {
        T entity = (T)this.collections.get(position);
        this.viewBinder.bindView(entity,position,holder,this.mContext);

    }

    @Override
    public int getItemCount() {
        return this.collections.size();
    }
    public T getItemFromList(int index ) {
        return collections.get( index );
    }

    public List<T> getList() {
        return collections;
    }
    /**
     * Clears the internal list
     */
    public void clearList() {
        collections.clear();
        notifyDataSetChanged();
    }

    /**
     * Adds a entity to the list and calls {@link #notifyDataSetChanged()}.
     * Should not be used if lots of NotificationDummy are added.
     *
     * @see #addAll(List)
     */
    public void add( T entity ) {
        collections.add( entity );
        notifyDataSetChanged();
    }

    /**
     * Adds a NotificationDummy to the list and calls
     * {@link #notifyDataSetChanged()}. Can be used {
     * {@link List#subList(int, int)}.
     *
     * @see #addAll(List)
     */
    public void addAll( List<T> entityList ) {
        collections.addAll( entityList );
        notifyDataSetChanged();
    }
}
