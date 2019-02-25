package aakashresearchlab.com.srmlib.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aakashresearchlab.com.srmlib.R;

public class FilterFragment extends Fragment {
    public FilterFragment(){
        // required public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }
}
