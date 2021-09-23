package com.theekshana.onlineshop.Model;

import android.widget.Filter;

import com.theekshana.onlineshop.Adapter.MainProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class FilterProducts extends Filter {

    private List<ProductModel> productModelsFilter;
    private MainProductAdapter mainProductAdapter;

    public FilterProducts(List<ProductModel> productModelsFilter, MainProductAdapter mainProductAdapter) {
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
            ArrayList<ProductModel> filterModels = new ArrayList<>();
            for (int i = 0; i<productModelsFilter.size(); i++){
                if (productModelsFilter.get(i).getProductTitle().toUpperCase().contains(constraint) || productModelsFilter.get(i).getProductMaincategory().toUpperCase().contains(constraint) || productModelsFilter.get(i).getProductsubCategory().toUpperCase().contains(constraint)){
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
    protected void publishResults(CharSequence constraint, FilterResults results) {
        mainProductAdapter.productModelList = (ArrayList<ProductModel>) results.values;
        mainProductAdapter.notifyDataSetChanged();
    }
}

