package com.falkenapps.macgyver.favorites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.Professional;
import com.falkenapps.macgyver.favorites.adapters.FavoritesRVAdapter;
import com.falkenapps.macgyver.professional.ProfessionalActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by FalkenApps:falken on 7/5/17.
 */

public class FavoritesActivity extends AppCompatActivity implements  OnFavoritesRVClickListener, FavoritesView {

    private static final String ACTIVITY_TAG = Constant.TAG + ":FavoritesActivity";

    ProgressDialog progressDialog;
    Handler closeDialogHandler;

    @BindView(R.id.favoritesToolbar)
    Toolbar toolbar;

    @BindView(R.id.favoritesRV)
    RecyclerView recyclerView;

    @BindString(R.string.favorites_title)
    String favoritesTitle;

    private ActionBar actionBar;
    private FavoritesRVAdapter adapter;
    private FavoritesPresenter favoritesPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        favoritesPresenter = new FavoritesPresenterImpl(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(favoritesTitle);


        }

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new FavoritesRVAdapter(new ArrayList<Professional>(),this,this);
        recyclerView.setAdapter(adapter);

        favoritesPresenter.getMyFavorites();




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFavoritesRVItemClick(Professional professional) {
        Log.d(ACTIVITY_TAG,"onRVItemClick:" + professional.toString());
        Intent activity = new Intent(this,ProfessionalActivity.class);
        activity.putExtra("professionalObject", new Gson().toJson(professional));
        startActivityForResult(activity,Constant.RC_PROFESSIONAL);

    }



    @Override
    public void addProfessional(Professional professional) {
        adapter.addFavorite(professional);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void deleteProfessional(Professional professional) {

    }

    @Override
    public void setProfessionalList(List<Professional> professionalList) {
        adapter.setProfessionalList(professionalList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public int getProfessionalListSize(){
        return adapter.getItemCount();
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("JB_FAV","onResume");




    }

    @Override
    public void hideDialog(){
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void showDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.favorites_searching));
        progressDialog.setCancelable(false);
        progressDialog.show();
        closeDialogHandler = new Handler();
        closeDialogHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }




            }
        }, Constant.MAX_TIME_LOADING_FAVORITES);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.RC_PROFESSIONAL) {
            if (data != null && data.getBooleanExtra(Constant.DELETED,false)) {
                adapter.setProfessionalList(new ArrayList<Professional>());
                adapter.notifyDataSetChanged();
                favoritesPresenter.getMyFavorites();
            }

        }
    }



}
