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
        Glide.with(context).load(current.image_url).into(holder.teamImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        ImageView teamImage;
        private viewholder(View itemView) {
            super(itemView);
            teamImage=itemView.findViewById(R.id.image_preview);
//            article_desc=(TextView)itemView.findViewById(R.id.article_subtextview);
        }
    }
}
