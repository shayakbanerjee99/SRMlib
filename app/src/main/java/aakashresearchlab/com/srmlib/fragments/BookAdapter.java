package aakashresearchlab.com.srmlib.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import aakashresearchlab.com.srmlib.R;

/**
 * Created by harshit on 02-12-2017.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.viewholder> {
    private Context context;
    private LayoutInflater inflater;
    private List<BooksElement> data = Collections.emptyList();

    public BookAdapter(Context context, List<BooksElement> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_home, parent, false);
        viewholder holder = new viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final viewholder holder, int position) {
        final BooksElement current = data.get(position);
        holder.name.setText(current.name);
        holder.author.setText(current.Author);
        holder.ava.setText(current.availability);
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
