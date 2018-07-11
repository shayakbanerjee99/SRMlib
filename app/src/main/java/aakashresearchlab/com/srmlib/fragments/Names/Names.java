package aakashresearchlab.com.srmlib.fragments.Names;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import aakashresearchlab.com.srmlib.R;

import static java.util.Collections.sort;

public class Names extends Fragment {
    private RecyclerView mStudentList;
    private NameAdapter mAdapter;
    private DatabaseReference dataRef;
    public Names() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_names, container, false);
        mStudentList=view.findViewById(R.id.studentlist);
        dataRef= FirebaseDatabase.getInstance().getReference().child("BOOKS");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getAllChild(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
        return view;
    }
    void getAllChild(DataSnapshot snapshot)
    {
        List<ReservedIds> dataList=new ArrayList<>();
        for(DataSnapshot ref:snapshot.getChildren()) {

            ReservedIds data = new ReservedIds();
            data.availability = ref.child("FIELD1").getValue(String.class);
            if(data.availability.equals("Available"))
                continue;
            data.studentName = ref.child("FIELD2").getValue(String.class);
            data.bookName = ref.child("FIELD4").getValue(String.class);
            data.id = ref.child("FIELD6").getValue(String.class);
            dataList.add(data);
        }
        sort(dataList, new Comparator<ReservedIds>() {
            @Override
            public int compare(ReservedIds t,ReservedIds t1) {
                return t.bookName.compareTo(t1.bookName);
            }
        });
        if(getContext()!=null){
            mAdapter = new NameAdapter(getContext(),dataList);
            mStudentList.setAdapter(mAdapter);
            mStudentList.setVerticalScrollBarEnabled(true);
            mStudentList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        }}

    @Override
    public void onPause() {
        super.onDestroy();
    }
}
