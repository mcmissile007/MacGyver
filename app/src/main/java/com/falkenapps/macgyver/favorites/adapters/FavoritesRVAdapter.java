package com.falkenapps.macgyver.favorites.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.Haversine;
import com.falkenapps.macgyver.common.Professional;
import com.falkenapps.macgyver.favorites.OnFavoritesRVClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by FalkenApps:falken on 7/5/17.
 */

public class FavoritesRVAdapter extends RecyclerView.Adapter<FavoritesRVAdapter.ProfessionalViewHolder>{

    private List<Professional> professionalList;
    private final Context context;
    private OnFavoritesRVClickListener onFavoritesRVClickListener;

    public FavoritesRVAdapter(List<Professional> professionalList, Context context, OnFavoritesRVClickListener onFavoritesRVClickListener){
        this.professionalList = new ArrayList<>(professionalList); //shadow copy
        this.context = context;
        this.onFavoritesRVClickListener = onFavoritesRVClickListener;

    }
    @Override
    public ProfessionalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new ProfessionalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProfessionalViewHolder professionalViewHolder, int i) {
        final Professional professional = professionalList.get(i);

        professionalViewHolder.name.setText(professional.getName());
        //professionalViewHolder.score.setText(String.format(Locale.getDefault(), "%.1f", professional.getScore()));

        double score = professional.getScore();

        Drawable myDrawable;

        if (score >= 4.5){

            myDrawable = ContextCompat.getDrawable(context,R.drawable.scorefive);


        }else if(score < 4.5 && score >=3.5){
            myDrawable = ContextCompat.getDrawable(context,R.drawable.scorefour);

        }else if(score < 3.5 && score >=2.5){
            myDrawable = ContextCompat.getDrawable(context,R.drawable.scorethree);

        } else if(score < 2.5 && score >=1.5){
            myDrawable = ContextCompat.getDrawable(context,R.drawable.scoretwo);

        }else if(score < 1.5 && score >=0) {
            myDrawable = ContextCompat.getDrawable(context,R.drawable.scoreone);

        }else{
            myDrawable = ContextCompat.getDrawable(context,R.drawable.scorethree);

        }

        if (myDrawable != null){
            professionalViewHolder.imgScore.setImageDrawable(myDrawable);
        }

        if (!professional.isGuard()){
            professionalViewHolder.imgGuard.setVisibility(View.INVISIBLE);

        }


        professionalViewHolder.job.setText(professional.getActivity());
        professionalViewHolder.location.setText(professional.getSmallBusinessAddress() +  " " + professional.getCity());

        professionalViewHolder.title.setText(professional.getTitle());

        professionalViewHolder.price.setText(String.format(Locale.getDefault(), "%.0f " + context.getResources().getString(R.string.main_pricePerHour) , (professional.getPricePerHour()+professional.getDeparturePrice())));
        //professionalViewHolder.price.setText(String.format(Locale.getDefault(), "%.1f", (professional.getPricePerHour())));

        SharedPreferences settings = context.getSharedPreferences(Constant.PREFS_SEARCH, 0);
        Double latitude = Double.longBitsToDouble(settings.getLong(Constant.LOCATION_LATITUDE, 0));
        Double longitude = Double.longBitsToDouble(settings.getLong(Constant.LOCATION_LONGITUDE, 0));

        if (latitude == 0){
            latitude =  Constant.GEO_LOCATION.getLatitude();
        }
        if (longitude == 0){
            longitude =  Constant.GEO_LOCATION.getLongitude();
        }

        double distance =  (Haversine.distance(professional.getFullGeoLocation().getLatitude(),
                professional.getFullGeoLocation().getLongitude(),latitude
                ,longitude)
        );
        //double distance =0;

        professionalViewHolder.distance.setText(String.format(Locale.getDefault(), "%.1f Km", distance));

        professionalViewHolder.photo.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.com_facebook_profile_picture_blank_square));

        if (!professional.getPhotoURL().equals("")) {

            Glide.with(context).load(professional.getPhotoURL()).into(professionalViewHolder.photo);

        }


        professionalViewHolder.setOnFavoriteRVItemClickListener(professional, onFavoritesRVClickListener);

    }

    @Override
    public int getItemCount() {
        return professionalList.size();
    }

    public void addFavorite(Professional professional){
        this.professionalList.add(professional);


    }

    public void setProfessionalList(List<Professional> professionalList){
        this.professionalList = new ArrayList<>(professionalList);
    }

    public static class ProfessionalViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;

        public final TextView price;
        public final TextView job;
        public final TextView distance;
        public final TextView title;
        public final TextView location;
        public final ImageView photo;
        public final ImageView imgScore;
        public final ImageView imgDistance;
        public final ImageView imgEuro;
        public final ImageView imgGuard;






        public ProfessionalViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.txtProfName);

            this.distance = (TextView) itemView.findViewById(R.id.txtProfDistance);
            this.price = (TextView) itemView.findViewById(R.id.txtProfPrice);
            this.job = (TextView) itemView.findViewById(R.id.txtProfActivity);
            this.title = (TextView) itemView.findViewById(R.id.txtProfTitle);
            this.photo = (ImageView) itemView.findViewById(R.id.imgProfessional);
            this.imgScore = (ImageView) itemView.findViewById(R.id.imgProfScore);
            this.imgDistance = (ImageView) itemView.findViewById(R.id.imgProfLocation);
            this.imgEuro = (ImageView) itemView.findViewById(R.id.imgProfEuro);
            this.imgGuard = (ImageView) itemView.findViewById(R.id.imgProfGuard);
            this.location = (TextView) itemView.findViewById(R.id.txtProfLocation);



        }

        public void setOnFavoriteRVItemClickListener(final Professional professional, final OnFavoritesRVClickListener onFavoritesRVClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFavoritesRVClickListener.onFavoritesRVItemClick(professional);

                }
            });

        }
    }
}
