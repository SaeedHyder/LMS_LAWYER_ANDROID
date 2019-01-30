package com.ingic.lmslawyer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ingic.lmslawyer.ui.viewbinders.abstracts.RecyclerViewBinder;

import java.util.List;

public class RecyclerListAdapter<T> extends RecyclerView.Adapter<RecyclerViewBinder.BaseViewHolder> {

    protected List<T> arrayList;
    protected RecyclerViewBinder<T> viewBinder;
    protected Context mContext;
    protected int mLayoutResouceID;

    public RecyclerListAdapter(Context context, List<T> arrayList,
                               RecyclerViewBinder<T> viewBinder) {
        mContext = context;
        this.arrayList = arrayList;
        this.viewBinder = viewBinder;
    }


    @Override
    public RecyclerViewBinder.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return (RecyclerViewBinder.BaseViewHolder) this.viewBinder.createViewHolder(this.viewBinder.createView(this.mContext, this.mLayoutResouceID));
    }

    @Override
    public void onBindViewHolder(RecyclerViewBinder.BaseViewHolder holder, int position) {
        T entity = (T) this.arrayList.get(position);
        this.viewBinder.bindView(entity, position, holder, this.mContext);
    }

    @Override
    public int getItemCount() {
        return this.arrayList.size();
    }

    public T getItemFromList(int index) {
        return arrayList.get(index);
    }

    public List<T> getList() {
        return arrayList;
    }

    /**
     * Clears the internal list
     */
    public void clearList() {
        arrayList.clear();
        notifyDataSetChanged();
    }

    /**
     * Adds a entity to the list and calls {@link #notifyDataSetChanged()}.
     * Should not be used if lots of NotificationDummy are added.
     *
     * @see #addAll(List)
     */
    public void add(T entity) {
        arrayList.add(entity);
        notifyDataSetChanged();
    }

    /**
     * Adds a NotificationDummy to the list and calls
     * {@link #notifyDataSetChanged()}. Can be used {
     * {@link List#subList(int, int)}.
     *
     * @see #addAll(List)
     */
    public void addAll(List<T> entityList) {
        arrayList.addAll(entityList);
        notifyDataSetChanged();
    }
}
