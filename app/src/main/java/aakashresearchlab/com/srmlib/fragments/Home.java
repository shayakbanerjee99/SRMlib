package aakashresearchlab.com.srmlib.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aakashresearchlab.com.srmlib.R;

import static java.util.Collections.sort;

/**
 * Created by harshit on 02-12-2017.
 */

public class Home extends Fragment {
    private RecyclerView mBookList;
    private BookAdapter mAdapter;
    private DatabaseReference dataRef;
    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mBookList=view.findViewById(R.id.bookslist);
        dataRef= FirebaseDatabase.getInstance().getReference().child("BOOKS");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(getContext(), "aya", Toast.LENGTH_SHORT).show();
                getAllChild(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });

//        dataRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                getAllChild(dataSnapshot);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                getAllChild(dataSnapshot);
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                getAllChild(dataSnapshot);
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
    //    });
        return view;
    }
    void getAllChild(DataSnapshot snapshot)
    {
        List<BooksElement> dataList=new ArrayList<>();
        for(DataSnapshot ref:snapshot.getChildren()) {

            BooksElement data = new BooksElement();
            data.name = ref.child("FIELD4").getValue(String.class);
            data.Author = ref.child("FIELD5").getValue(String.class);
            data.availability = ref.child("FIELD1").getValue(String.class);
            data.id = ref.child("FIELD7").getValue(String.class);
            data.dep = ref.child("FIELD6").getValue(String.class);
            dataList.add(data);
//            Map<String, String> userData = new HashMap<String, String>();
//            userData.put("FIELD1", "Available");
//            userData.put("FIELD2", "");
//            userData.put("FIELD3", "");
//            userData.put("FIELD4", data.name);
//            userData.put("FIELD5", data.Author);
//            userData.put("FIELD6", data.id);
//            userData.put("FIELD7", data.dep);
//
//            DatabaseReference reference=dataRef.getParent().child("BOOKS");
//            if(data.id.equals("") || data.id.contains(".") || data.id.contains("#"))
//            {
//
//            }
//            else{
//            reference.child(data.id).setValue(userData);
//            }
        }
        sort(dataList, new Comparator<BooksElement>() {
            @Override
            public int compare(BooksElement t, BooksElement t1) {

                return t.name.compareTo(t1.name);
            }
        });
        mAdapter = new BookAdapter(getActivity(),dataList);
        mBookList.setAdapter(mAdapter);
        mBookList.setVerticalScrollBarEnabled(true);
        mBookList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }
}
