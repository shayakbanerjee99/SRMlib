package aakashresearchlab.com.srmlib.fragments.Names;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import aakashresearchlab.com.srmlib.MainActivity;
import aakashresearchlab.com.srmlib.R;
import aakashresearchlab.com.srmlib.SignIn;
import aakashresearchlab.com.srmlib.Signup_user;

import static java.util.Collections.sort;

public class Names extends Fragment implements View.OnClickListener{
//    private RecyclerView mStudentList;
    public NameAdapter mAdapter;
//    private DatabaseReference dataRef;
    private MaterialSearchView searchView;

    public String username;
    Context context;

    public Names() {
        // Required empty public constructor
    }

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference databaseReference;
    private TextView branch,regno,name;
    private TextView note1;
    private TextView note2;

    Button buttonSignOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_names, container, false);
        setHasOptionsMenu(true);
        Toolbar toolbar=view.findViewById(R.id.toolbar_names);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        buttonSignOut =view.findViewById(R.id.signout);
        buttonSignOut.setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

        note1=view.findViewById(R.id.note1);
        note2=view.findViewById(R.id.note2);
        branch=view.findViewById(R.id.Department);
        regno=view.findViewById(R.id.RegNo);
        name=view.findViewById(R.id.textViewName);


        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("MYLOG",dataSnapshot.toString());
                getprofile(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(context,"Couldn't get profile data",Toast.LENGTH_LONG).show();
            }
        });


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

    private void getprofile(DataSnapshot dataSnapshot)
    {
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        final String uid=mFirebaseUser.getUid();

        if(dataSnapshot.exists())
        {
            for(DataSnapshot ds:dataSnapshot.getChildren())
            {
                    Signup_user user=new Signup_user();
                    String s= Long.toString(ds.child("users").child(uid).getChildrenCount());
              //      Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();


                    user.setName(ds.child(uid).child("name").getValue(String.class));
                    //user.setName(ds.child(uid).getValue(Signup_user.class).getName());
                    user.setRegno(ds.child(uid).child("regno").getValue(String.class));
                    user.setBranch(ds.child(uid).child("branch").getValue(String.class));


                    Log.d("MYTAG",user.getName()+" "+user.getBranch());



                    name.setText("Name : "+user.getName());
                    regno.setText("Reg no : "+user.getRegno());
                    branch.setText("Branch : "+user.getBranch());


            }

        }
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

            Intent i=new Intent(getActivity(),SignIn.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }
    }
}
