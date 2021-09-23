package com.theekshana.onlineshop.Model;

import android.widget.Filter;

import com.theekshana.onlineshop.Adapter.AdapterOrder;

import java.util.ArrayList;
import java.util.List;

public class FilterOrder extends Filter{

    private List<ModelOrderUser> productModelsFilter;
    private AdapterOrder mainProductAdapter;

    public FilterOrder(List<ModelOrderUser> productModelsFilter, AdapterOrder mainProductAdapter) {
        this.productModelsFilter = productModelsFilter;
        this.mainProductAdapter = mainProductAdapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults results = new FilterResults();
        //validation data for search
        if (constraint != null && constraint.length() > 0){
            //change to upper case to make case
            constraint = constraint.toString().toUpperCase();
            //store our filterd list
            ArrayList<ModelOrderUser> filterModels = new ArrayList<>();
            for (int i = 0; i<productModelsFilter.size(); i++){
                if (productModelsFilter.get(i).getOrderStatus().toUpperCase().contains(constraint) ||productModelsFilter.get(i).getDeliveryStatus().toUpperCase().contains(constraint)  ){
                    filterModels.add(productModelsFilter.get(i));
                }
            }
            results.count = filterModels.size();
            results.values = filterModels;
        }
        else {
            results.count = productModelsFilter.size();
            results.values = productModelsFilter;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        mainProductAdapter.modelOrderUserList = (ArrayList<ModelOrderUser>) results.values;
        mainProductAdapter.notifyDataSetChanged();
    }
}
