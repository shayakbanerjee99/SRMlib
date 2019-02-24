package aakashresearchlab.com.srmlib.fragments.Names;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import aakashresearchlab.com.srmlib.MainActivity;
import aakashresearchlab.com.srmlib.R;
import aakashresearchlab.com.srmlib.SignIn;

import static java.util.Collections.sort;

public class Names extends Fragment implements View.OnClickListener{
//    private RecyclerView mStudentList;
    public NameAdapter mAdapter;
//    private DatabaseReference dataRef;
    private MaterialSearchView searchView;

    public String username;

    public Names() {
        // Required empty public constructor
    }

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    Button buttonSignOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_names, container, false);
        setHasOptionsMenu(true);
        Toolbar toolbar=view.findViewById(R.id.toolbar_names);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        buttonSignOut =view.findViewById(R.id.signout);
        buttonSignOut.setOnClickListener(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
//        mStudentList=view.findViewById(R.id.studentlist);
//        dataRef= FirebaseDatabase.getInstance().getReference().child("BOOKS");
//        dataRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                getAllChild(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//
//        });
        searchView = view.findViewById(R.id.search_view_names);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getActivity(), ""+query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mAdapter != null) {
                    mAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        username = getActivity().getIntent().getStringExtra("email");



        return view;
    }
//    void getAllChild(DataSnapshot snapshot)
//    {
//        List<ReservedIds> dataList=new ArrayList<>();
//        for(DataSnapshot ref:snapshot.getChildren()) {
//
//            ReservedIds data = new ReservedIds();
//            data.regNo = ref.child("FIELD1").getValue(String.class);
//            if(data.regNo.equals("Available"))
//                continue;
//            data.studentName = ref.child("FIELD2").getValue(String.class);
//            data.bookName = ref.child("FIELD4").getValue(String.class);
//            data.id = ref.child("FIELD6").getValue(String.class);
//            dataList.add(data);
//        }
//        sort(dataList, new Comparator<ReservedIds>() {
//            @Override
//            public int compare(ReservedIds t,ReservedIds t1) {
//                return t.bookName.compareTo(t1.bookName);
//            }
//        });
//        if(getContext()!=null){
//            mAdapter = new NameAdapter(getContext(),dataList);
//            mStudentList.setAdapter(mAdapter);
//            mStudentList.setVerticalScrollBarEnabled(true);
//            mStudentList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
//        }}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:

                // Not implemented here
                return false;

            default:
                break;
        }

        return false;
    }

    @Override
    public void onPause() {
        super.onDestroy();
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signout)
        {
            mFirebaseAuth.signOut();
            mFirebaseUser = null;


            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getContext(),"Signed out",Toast.LENGTH_LONG).show();

            startActivity(new Intent(getActivity(),SignIn.class));
            getActivity().finish();

        }
    }
}
