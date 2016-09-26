package com.playbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.playbook.SupportClass.Config;
import com.playbook.SupportClass.FontCache;
import org.json.JSONArray;
import org.json.JSONObject;

public class PlaybookRecyclerAdapter extends RecyclerView.Adapter {
    private final Context context;
    public JSONArray jArrPlaybook;

    public PlaybookRecyclerAdapter(Context context, JSONArray jArrPlaybook) {
        this.context = context;
        this.jArrPlaybook = jArrPlaybook;
    }

    @Override
    public int getItemCount() {
        return jArrPlaybook.length();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder vh, final int position) {
        if (vh instanceof PlaybookViewHolder) {
            final JSONObject jObjPlaybook = jArrPlaybook.optJSONObject(position);

            // Set data
            Glide.with(context).load(Config.URL_ICON + jObjPlaybook.optString("foto_sampul")).placeholder(R.drawable.loader)
                    .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).override(300, 300)
                    .dontAnimate().into(((PlaybookViewHolder) vh).imgCover);
            ((PlaybookViewHolder) vh).lblName.setText(jObjPlaybook.optString("nama_buku"));

            ((PlaybookViewHolder) vh).cardPlaybookCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("nama_buku", jObjPlaybook.optString("nama_buku"));
                    intent.putExtra("nama_file", jObjPlaybook.optString("nama_file"));
                    intent.putExtra("url_preview", jObjPlaybook.optString("url_preview"));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final RecyclerView.ViewHolder vh;

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.playbook_cell, viewGroup, false);
        vh = new PlaybookViewHolder(viewGroup.getContext(), itemView);

        return vh;
    }

    public static class PlaybookViewHolder extends RecyclerView.ViewHolder {
        protected CardView cardPlaybookCell;
        protected ImageView imgCover;
        protected TextView lblName;
        protected Typeface fontLatoRegular;

        public PlaybookViewHolder(Context ctx, View view) {
            super(view);

            cardPlaybookCell = (CardView) view.findViewById(R.id.cardPlaybookCell);
            imgCover         = (ImageView) view.findViewById(R.id.imgCover);
            lblName          = (TextView) view.findViewById(R.id.lblName);
            fontLatoRegular  = FontCache.get(ctx, "Lato-Regular");

            lblName.setTypeface(fontLatoRegular);
        }
    }
}
