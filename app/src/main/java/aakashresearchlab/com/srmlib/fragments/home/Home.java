package aakashresearchlab.com.srmlib.fragments.home;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import aakashresearchlab.com.srmlib.MainActivity;
import aakashresearchlab.com.srmlib.R;

import static java.util.Collections.sort;

/**
 * Created by harshit on 02-12-2017.
 *
 * @author Harshit Gupta
 * @since 2nd Dec 2017
 */

public class Home extends Fragment {
    private RecyclerView mBookList;
    public BookAdapter mAdapter;
    private DatabaseReference dataRef;
    private MaterialSearchView searchView;

    public String username;

    /**Constructor*/
    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        mBookList = view.findViewById(R.id.bookslist);
        Toolbar toolbar=view.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        dataRef = FirebaseDatabase.getInstance().getReference().child("0").child("books1");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(getContext(), "aya", Toast.LENGTH_SHORT).show();
                getAllChild(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
        searchView = view.findViewById(R.id.search_view);
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
            @Override   // required overriden method
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override   // required overriden method
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        // fetching email from the SignIn activity
        username = getActivity().getIntent().getStringExtra("email");

        return view;
    }

    // retrieves the search result
    void getAllChild(DataSnapshot snapshot) {
        List<BooksElement> dataList = new ArrayList<>();
        for (DataSnapshot ref : snapshot.getChildren()) {

            BooksElement data = new BooksElement();
//            data.bookCount = 3;
            data.availability = ref.child("availibility").getValue(String.class);
            // <b> Below code was removed because all of the available data should be displayed</b>
//            Display book only if the book is available
//            if (!data.availability.equals("Available"))
//                continue;
            data.name = ref.child("subject").getValue(String.class);
            data.Author = ref.child("authors").getValue(String.class);
            data.id = ref.child("FIELD6").getValue(String.class);
            data.dep = ref.child("subDepartment").getValue(String.class);
            //TODO: write code for book count
            data.bookCount=ref.child("bookCount").getValue(int.class);


            dataList.add(data);
        }
        sort(dataList, new Comparator<BooksElement>() {
            @Override
            public int compare(BooksElement t, BooksElement t1) {

                return t.name.compareTo(t1.name);
            }
        });
        if (getContext() != null) {
            mAdapter = new BookAdapter(getContext(), dataList);
            mBookList.setAdapter(mAdapter);
            mBookList.setVerticalScrollBarEnabled(true);
            mBookList.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));
        }
    }

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

            case R.id.action_filter:
                launchFiltersDialog();
                break;

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
        super.onPause();
    }

    public void launchFiltersDialog(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.fragment_filter);
        dialog.show();
    }
}
