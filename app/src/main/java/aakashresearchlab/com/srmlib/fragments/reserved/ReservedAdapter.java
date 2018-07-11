package aakashresearchlab.com.srmlib.fragments.reserved;

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
import aakashresearchlab.com.srmlib.fragments.Names.ReservedIds;


/**
 * Created by harshit on 16-02-2018.
 */

public class ReservedAdapter  extends RecyclerView.Adapter<ReservedAdapter.viewholder> implements Filterable {
    private Context context;
    private LayoutInflater inflater;
    private List<ReservedElements> data = Collections.emptyList();
    private List<ReservedElements> filteredList;

    public ReservedAdapter(Context context, List<ReservedElements> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ReservedAdapter.viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_home, parent, false);
        ReservedAdapter.viewholder holder = new ReservedAdapter.viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ReservedAdapter.viewholder holder, int position) {
        final ReservedElements current = data.get(position);
        holder.name.setText(current.name);
        holder.author.setText(current.Author);
        holder.ava.setText(current.availability);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(context);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialoge);
                final EditText editText = dialog.findViewById(R.id.dialog_edit);
                final EditText editName = dialog.findViewById(R.id.dialog_edit_name);
                final TextView regnoText = dialog.findViewById(R.id.RegNoText);
                final TextView nameText = dialog.findViewById(R.id.nameText);
                editText.setVisibility(View.GONE);
                editName.setVisibility(View.GONE);
                nameText.setVisibility(View.GONE);
                regnoText.setVisibility(View.GONE);
                final TextView textView=dialog.findViewById(R.id.dialog_text);
                textView.setText("Return");
                CardView button=dialog.findViewById(R.id.button);
                dialog.show();
                Toast.makeText(context, ""+current.id, Toast.LENGTH_SHORT).show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference dataRef;
                            dataRef= FirebaseDatabase.getInstance().getReference().child("BOOKS");
                            dataRef.child(""+current.id).child("FIELD1").setValue("Available");
                            dialog.dismiss();

                    }
                });
            }
        });
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
                    List<ReservedElements> filter = new ArrayList<>();
                    for (ReservedElements row : data) {
                        if (row.name.toLowerCase().contains(charString.toLowerCase()) || row.id.contains(charSequence)) {
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
                filteredList = (ArrayList<ReservedElements>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        TextView name,author,ava;
        private viewholder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_id);
            author=itemView.findViewById(R.id.author_id);
            ava=itemView.findViewById(R.id.availability);
//            article_desc=(TextView)itemView.findViewById(R.id.article_subtextview);
        }
    }
}
