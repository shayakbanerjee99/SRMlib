package aakashresearchlab.com.srmlib.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aakashresearchlab.com.srmlib.R;

/**
 * Created by harshit on 02-12-2017.
 */

public class Home extends Fragment {
    private RecyclerView mBookList;
    private BookAdapter mAdapter;
    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mBookList=view.findViewById(R.id.bookslist);
        teams_call();
        return view;
    }
    void teams_call() {
        final List<BooksElement> somedata = new ArrayList<>();
        try {
            String result = "[" +
                    "{\"imageurl\":\"https://images-na.ssl-images-amazon.com/images/I/51Qe7b2ukKL._SX323_BO1,204,203,200_.jpg\"," +
                    " \"name\":\"AARUUSH\"," +
                    " \"description\":\"" +"90&_embed=true" + "\"" +
                    "}," +
                    "{\"imageurl\":\"https://images-na.ssl-images-amazon.com/images/I/51SsYBXLlNL._SX323_BO1,204,203,200_.jpg\"," +
                    " \"name\":\"MILAN\"," +
                    " \"description\":\"" + "99&_embed=true" + "\"" +
                    "}," +
                    "{\"imageurl\":\"https://images-na.ssl-images-amazon.com/images/I/51seQrAPCCL._SX323_BO1,204,203,200_.jpg\"," +
                    " \"name\":\"DSA\"," +
                    " \"description\":\"" +"100&_embed=true" + "\"" +
                    "}," +
                    "{\"imageurl\":\"https://images-na.ssl-images-amazon.com/images/I/51OGeF9Rl7L._SX323_BO1,204,203,200_.jpg\"," +
                    " \"name\":\"Team 1.618\"," +
                    " \"description\":\"" +  "96&_embed=true" + "\"" +
                    "}," +
                    "{\"imageurl\":\"https://images-na.ssl-images-amazon.com/images/I/6101K3ABv8L._SX323_BO1,204,203,200_.jpg\"," +
                    " \"name\":\"4ze Racing\"," +
                    " \"description\":\"" +  "92&_embed=true" + "\"" +
                    "}," +
                    "{\"imageurl\":\"https://images-eu.ssl-images-amazon.com/images/I/41Nf+yyQq5L._AC_SR150,150_.jpg\"," +
                    " \"name\":\"Auto Creed Racing\"," +
                    " \"description\":\"" +  "128&_embed=true" + "\"" +
                    "}," +
                    "{\"imageurl\":\"https://images-eu.ssl-images-amazon.com/images/I/51A4RKbwjqL._AC_SR150,150_.jpg\"," +
                    " \"name\":\"Chicane Racing India\"," +
                    " \"description\":\"" +  "93&_embed=true" + "\"" +
                    "}," +
                    "{\"imageurl\":\"https://images-eu.ssl-images-amazon.com/images/I/41PJ-SfsVBL._AC_SR150,150_.jpg\"," +
                    " \"name\":\"Team D.I.R.T\"," +
                    " \"description\":\"" +  "129&_embed=true" + "\"" +
                    "}," +
                    "{\"imageurl\":\"https://images-eu.ssl-images-amazon.com/images/I/41lCWTjm4NL._AC_SR150,150_.jpg\"," +
                    " \"name\":\"The Elecruisers\"," +
                    " \"description\":\"" +  "130&_embed=true" + "\"" +
                    "}," +
                    "{\"imageurl\":\"https://images-eu.ssl-images-amazon.com/images/I/51ph-za81oL._AC_SR150,150_.jpg\"," +
                    " \"name\":\"Team ETROS\"," +
                    " \"description\":\"" +  "97&_embed=true" + "\"" +
                    "}," +
                    "{\"imageurl\":\"https://images-eu.ssl-images-amazon.com/images/I/51pgpN6PgiL._AC_SR150,150_.jpg\"," +
                    " \"name\":\"Team Full Throttle\"," +
                    " \"description\":\"" +  "98&_embed=true" + "\"" +
                    "}," +
                    "{\"imageurl\":\"https://images-eu.ssl-images-amazon.com/images/I/51KQmkuE-4L._AC_SR150,150_.jpg\"," +
                    " \"name\":\"Gen Hybrid Labs\"," +
                    " \"description\":\"" +  "94&_embed=true" + "\"" +
                    "}," +
                    "{\"imageurl\":\"https://images-eu.ssl-images-amazon.com/images/I/51qpW1aGkoL._AC_SR150,150_.jpg\"," +
                    " \"name\":\"Infeon Supermilage \"," +
                    " \"description\":\"" +  "131&_embed=true" + "\"" +
                    "}," +
                    "{\"imageurl\":\"https://images-eu.ssl-images-amazon.com/images/I/51b5g3fUIiL._AC_SR150,150_.jpg\"," +
                    " \"name\":\"SRMKZILLA\"," +
                    " \"description\":\"" +  "132&_embed=true" + "\"" +
                    "}," +
                    "{\"imageurl\":\"https://images-eu.ssl-images-amazon.com/images/I/41W2PavF1zL._AC_SR150,150_.jpg\"," +
                    " \"name\":\"SRM SAE\"," +
                    " \"description\":\"" +  "95&_embed=true" + "\"" +
                    "}]";
            JSONArray finalarray = new JSONArray(result);
            for (int a = 0; a < finalarray.length(); a++) {
                BooksElement xyz = new BooksElement();
                JSONObject finalobject = finalarray.getJSONObject(a);
                xyz.image_url = finalobject.getString("imageurl");
//                xyz.name = finalobject.getString("articletitle");
//                xyz.description = finalobject.getString("articledescription");
                somedata.add(xyz);
            }
            mAdapter = new BookAdapter(getActivity(), somedata);
            mBookList.setAdapter(mAdapter);
            mBookList.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        } catch (Exception e) {
            Toast.makeText(getActivity(), "No response " + e, Toast.LENGTH_SHORT).show();
            Log.d("errror",""+e);
        }
    }

}
