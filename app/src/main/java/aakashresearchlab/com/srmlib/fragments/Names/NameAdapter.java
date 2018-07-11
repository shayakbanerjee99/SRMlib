package aakashresearchlab.com.srmlib.fragments.Names;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.List;

import aakashresearchlab.com.srmlib.R;

public class NameAdapter extends RecyclerView.Adapter<NameAdapter.viewholder> {
    private Context context;
    private LayoutInflater inflater;
    private List<ReservedIds> data = Collections.emptyList();

    public NameAdapter(Context context, List<ReservedIds> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public NameAdapter.viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_names, parent, false);
        NameAdapter.viewholder holder = new NameAdapter.viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NameAdapter.viewholder holder, int position) {
        final ReservedIds current = data.get(position);
        holder.studentName.setText(current.studentName);
        holder.bookName.setText(current.bookName);
        holder.bookId.setText(current.id);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
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
                final TextView textView = dialog.findViewById(R.id.dialog_text);
                textView.setText("Return");
                CardView button = dialog.findViewById(R.id.button);
                dialog.show();
                Toast.makeText(context, "" + current.id, Toast.LENGTH_SHORT).show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference dataRef;
                        dataRef = FirebaseDatabase.getInstance().getReference().child("BOOKS");
                        dataRef.child("" + current.id).child("FIELD1").setValue("Available");
                        dataRef.child("" + current.id).child("FIELD2").setValue("");
                        dialog.dismiss();

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        TextView studentName, bookName, bookId;

        private viewholder(View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.name_name);
            bookName = itemView.findViewById(R.id.book_name);
            bookId = itemView.findViewById(R.id.book_id);
        }
    }
}
