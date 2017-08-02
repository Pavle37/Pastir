package com.pastir.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pastir.BR;
import com.pastir.model.OnListItemClickListener;


/**
 * General class for Adapters and RecyclerView
 */

public abstract class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BasicAbstractViewHolder> {

    /**
     * Here we initialize the Binding and set it to the ViewHolder
     */
    @Override
    public BasicAbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        //Wire presenter up
        binding.setVariable(BR.listener,getListener());
        binding.executePendingBindings();

        return new BasicAbstractViewHolder(binding);
    }

    /**
     * This method is used to bind specific objects to specific holders.
     * This method is also where we handle OnClick events
     */
    @Override
    public void onBindViewHolder(BasicAbstractViewHolder holder, int position) {
        Object obj = getItemForPosition(position);
        holder.bind(obj);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    /**
     * Gets the object on that position
     */
    protected abstract Object getItemForPosition(int position);

    /**
     * Gets the presenter for that object if the rv has a presenter
     */
    protected abstract OnListItemClickListener getListener();

    /**
     * Layout id for the object at specified position
     */
    protected abstract int getLayoutIdForPosition(int position);

    class BasicAbstractViewHolder extends RecyclerView.ViewHolder{
        private final ViewDataBinding binding;

        BasicAbstractViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Object obj) {
            binding.setVariable(BR.rvItem, obj);
            binding.executePendingBindings();
        }

    }
}
