package com.falkenapps.macgyver.main.adapters;
import com.bumptech.glide.Glide;
import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.Haversine;
import com.falkenapps.macgyver.main.OnRVItemClickListener;


/*
Created by FalkenApps: Jorge Bareas on May 20016
*/

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.falkenapps.macgyver.common.Professional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ProfessionalViewHolder> {

    private List<Professional> professionalList;
    private final Context context;
    private OnRVItemClickListener onRVItemClickListener;

    private static final String ACTIVITY_TAG = Constant.TAG + ":RVAdapter";


    // Provide a reference to the views for each data item
    public static class ProfessionalViewHolder extends RecyclerView.ViewHolder {

        public final TextView name;
        //public final TextView email;
        //public final TextView score;
        //public final TextView guard;
        //public final TextView cp;
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
            //this.score = (TextView) itemView.findViewById(R.id.txtProfScore);
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

            //Log.d(ACTIVITY_TAG,"ProfessionalViewHolder");



        }

        public void setOnRVItemClickListener(final Professional professional, final OnRVItemClickListener onRVItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRVItemClickListener.onRVItemClick(professional);

                }
            });

        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RVAdapter(List<Professional> professionalList, Context context, OnRVItemClickListener onRVItemClickListener) {
        this.professionalList = Collections.synchronizedList(professionalList);
        this.context = context;
        this.onRVItemClickListener = onRVItemClickListener;
        Log.d(ACTIVITY_TAG,"RVAdapter constructor");


    }

    /*public void removeProfessional(Professional professional) {
        int position = professionalList.indexOf(professional);
        professionalList.remove(professional);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, professionalList.size());
    }*/

    public void removeProfessional(String key) {
       Log.d(Constant.TAG,"Adapter Remove Professional" + key);
       Professional deleteProfessional = new Professional();
        deleteProfessional.setKey(key);
        int position = professionalList.indexOf(deleteProfessional);
        if (position != -1) {
            professionalList.remove(deleteProfessional);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, professionalList.size());
        }
    }

    public void updateProfessionalList(List<Professional> professionalList, Comparator comparator){
        /*for (Professional professional: professionalList){
            addProfessional(professional);
        }*/
        Log.d("JB_ORDER","updateProfessionalList");
        this.professionalList = Collections.synchronizedList( new ArrayList<>(professionalList));
        if (comparator != null){
            Collections.sort(this.professionalList,comparator);

        }
        notifyDataSetChanged();
    }

    public void sortList(Comparator comparator){
        if (comparator != null){
            Collections.sort(this.professionalList,comparator);

        }
        notifyDataSetChanged();

    }

    public void addProfessional(Professional professional,Comparator comparator){

        Log.d("JB_ORDER","Adapter Add Professional" + professional.toString());

        /*Log.d("JB_DEBUG","Add_professional_in_adapter:" + professional.toString());
        Log.d("JB_DEBUG",this.toString());

        Log.d("JB_DEBUG","ADD LIST");
        for (Professional pro : professionalList){
            Log.d("JB_DEBUG",pro.toString());
        }
        Log.d("JB_DEBUG","END LIST");*/

        if (!professionalList.contains(professional)) {
            Log.d("JB_ORDER","not exists add:" + professional.toString());
            /*int lastPosition = professionalList.size() - 1;
            professionalList.add(professional);
            this.notifyItemInserted(lastPosition);
            notifyItemRangeChanged(lastPosition, professionalList.size());*/

            professionalList.add(professional);
            Log.d("JB_ORDER","comparator:" + comparator.toString() );
            if (comparator != null){
                Collections.sort(this.professionalList,comparator);

            }
            notifyDataSetChanged();



        }
        else{
            Log.d("JB_ORDER","YA EXISTE EN LA LISTA");
            Log.d("JB_ORDER","position:" + professionalList.indexOf(professional));
        }


    }

    public void updateProfessional(Professional professional){
        Log.d(Constant.TAG,"Adapter Update Professional" + professional.toString());
        int position = professionalList.indexOf(professional);
        if (position != -1) {
            professionalList.set(position, professional);
            this.notifyItemChanged(position);
        }

    }
    public void removeAllProfessionals(){
        professionalList.clear();
        this.notifyDataSetChanged();

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return professionalList.size();
    }

    //it is called when ProfessionalViewHolder need to be initialized
    @Override
    // Create new views (invoked by the layout manager)
    public ProfessionalViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.professional_item, parent, false);
        return new ProfessionalViewHolder(v);
    }

    //similar to getView in Array Adapter
    // Replace the contents of a view (invoked by the layout manager)
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


        professionalViewHolder.setOnRVItemClickListener(professional, onRVItemClickListener);


    }


    public void setItemList(List<Professional> itemList) {
        Log.d("JB_ORDER","setItemList:" + itemList.size());
         //EL BUG_esta aqui SE CREA UNA REFERENCIA A UN OBJETO MUTABLE QUE CUANDO CAMBIA EL OBJECTO
        //CAMBIA ESTE TAMBIEN.
        Log.d("JB_DEBUG_REFE","ItemList:" + itemList.toString());
        Log.d("JB_DEBUG_REFE","this.professionalList:" + this.professionalList.toString());
        this.professionalList = itemList;
        Log.d("JB_DEBUG_REFE","this.professionalList:" + Integer.toHexString(System.identityHashCode(this.professionalList)));
    }


}
