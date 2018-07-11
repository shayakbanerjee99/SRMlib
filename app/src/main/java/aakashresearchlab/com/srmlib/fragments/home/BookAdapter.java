package aakashresearchlab.com.srmlib.fragments.home;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aakashresearchlab.com.srmlib.R;

/**
 * Created by harshit on 02-12-2017.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.viewholder> implements Filterable {
    private Context context;
    private LayoutInflater inflater;
    private List<BooksElement> data = Collections.emptyList();
    private List<BooksElement> filteredList;


    public BookAdapter(Context context, List<BooksElement> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.context = context;
        filteredList=data;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_home, parent, false);
        viewholder holder = new viewholder(view);
        return holder;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredList = data;
                } else {
                    List<BooksElement> filter = new ArrayList<>();
                    for (BooksElement row : data) {
                        if (row.name.toLowerCase().contains(charString.toLowerCase()) || row.Author.contains(charSequence)) {
                            filter.add(row);
                        }
                    }

                    filteredList = filter;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<BooksElement>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(final viewholder holder, int position) {
        final BooksElement current = filteredList.get(position);
        holder.name.setText(current.name);
        holder.author.setText(current.Author);
        holder.ava.setText(current.availability);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialoge);
                final EditText editTextRegNo = dialog.findViewById(R.id.dialog_edit);
                final EditText editTextName = dialog.findViewById(R.id.dialog_edit_name);
                CardView button = dialog.findViewById(R.id.button);
                dialog.show();
                Toast.makeText(context, "" + current.id, Toast.LENGTH_SHORT).show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference dataRef;

                        if (!editTextRegNo.getText().toString().equals("") && !editTextName.getText().toString().equals("")) {
                            dataRef = FirebaseDatabase.getInstance().getReference().child("BOOKS");
                            dataRef.child("" + current.id).child("FIELD1").setValue("" + editTextRegNo.getText().toString());
                            dataRef.child("" + current.id).child("FIELD2").setValue("" + editTextName.getText().toString());
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "empty field", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        TextView name, author, ava;

        private viewholder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_id);
            author = itemView.findViewById(R.id.author_id);
            ava = itemView.findViewById(R.id.availability);
//            article_desc=(TextView)itemView.findViewById(R.id.article_subtextview);
        }
    }
}
