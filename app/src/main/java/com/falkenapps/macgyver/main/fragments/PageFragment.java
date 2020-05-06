package com.falkenapps.macgyver.main.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import 	android.os.Handler;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.Professional;
import com.falkenapps.macgyver.main.OnRVItemClickListener;
import com.falkenapps.macgyver.main.adapters.RVAdapter;
import com.falkenapps.macgyver.professional.ProfessionalActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;

//http://stackoverflow.com/questions/28929637/difference-and-uses-of-oncreate-oncreateview-and-onactivitycreated-in-fra



public class PageFragment extends Fragment implements OnRVItemClickListener,PageView{

    private static final String ACTIVITY_TAG = Constant.TAG + ":PageFragment";

    private static final String ARG_PAGE = "ARG_PAGE";
    //private List<Professional> professionalList;

    private RVAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mPage;
    private PagePresenter pagePresenter;
    private Comparator comparator;
    private ProgressDialog progress;
    Handler handler;
    Runnable runnable;
    //private boolean doReverse;

    public static PageFragment newInstance(int page) {
        Log.d(ACTIVITY_TAG,"newInstance:" + page);
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    public PageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //just the first time this function is called
        //The onCreate() is called first, for doing any non-graphical initialisations.
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mPage = getArguments().getInt(ARG_PAGE);
            Log.d(ACTIVITY_TAG,"OnCreate:" + mPage);

        }

        pagePresenter= new PagePresenterImpl(this);

        runnable = new Runnable() {
            @Override
            public void run() {
                if (hideSearchProgressDialog()) {
                    Log.d("JB_progress", "showSearchProgressDialog OFF RUN:" + mPage);
                    if (mAdapter.getItemCount() < 1) {
                        Log.d("JB_progress", "LIST VOID:" + mPage);
                        Toast.makeText(getContext(), getContext().getResources().getString(R.string.pageFragment_noResults), Toast.LENGTH_LONG).show();

                    }
                }


            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //very time you click on a tab this function is called
        //After the onCreate() is called (in the Fragment), the Fragment's onCreateView() is called.
        // You can assign your View variables and do any graphical initialisations.
        // You are expected to return a View from this method,
        // and this is the main UI view,
        // but if your Fragment does not use any layouts or graphics, you can return null

        View view = inflater.inflate(R.layout.fragment_page, container, false);

        Log.d(ACTIVITY_TAG, "onCreateView:" + mPage);
        //doReverse = false;

        if (mPage == 0){
            comparator = new Comparator<Professional>(){

                @Override
                public int compare(Professional professional, Professional t1) {

                    if (professional.getDistance() < t1.getDistance() ){
                        return -1;
                    }
                    else if (professional.getDistance() > t1.getDistance() )
                    {
                        return 1;
                    }
                    else{
                        return 0;
                    }

                    //return (int) (professional.getDistance() - t1.getDistance());
                }
            };


        }
        if (mPage == 1){
            comparator = new Comparator<Professional>(){

                @Override
                public int compare(Professional professional, Professional t1) {


                    if (professional.getScore() < t1.getScore() ){
                        return 1;
                    }
                    else if (professional.getScore() > t1.getScore() )
                    {
                        return -1;
                    }
                    else{
                        return 0;
                    }

                    //return (int) (10*professional.getScore() - 10*t1.getScore());
                }
            };

            //doReverse = true;

        }

        if(mPage == 2){
            comparator = new Comparator<Professional>(){

                @Override
                public int compare(Professional professional, Professional t1) {

                    double priceA = professional.getPricePerHour() + professional.getDeparturePrice();
                    double priceB = t1.getPricePerHour() + t1.getDeparturePrice();

                    if (priceA < priceB ){
                        return -1;
                    }
                    else if (priceA > priceB )
                    {
                        return 1;
                    }
                    else{
                        return 0;
                    }



                    /*return (int) (10*(professional.getPricePerHour() + professional.getDeparturePrice())
                            - 10*(t1.getPricePerHour()+t1.getDeparturePrice()));*/
                }
            };

        }


        // Inflate the layout for this fragment
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setHasFixedSize(true);

        Log.d(ACTIVITY_TAG, "mRecyclerView:" + mRecyclerView.toString());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        // Every time you click on a tab the list is loaded
        //could be better made in onCreate function??
        //professionalList = new ArrayList<>();

        mAdapter = new RVAdapter(new ArrayList<Professional>(), getContext(), this);
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                     @Override
                                                     public void onRefresh() {

                                                         Log.d("JB_REFRESH","ON FINGER");
                                                         refreshData();
                                                     }


                                                 }
        );
        //(new AsyncRecyclerViewLoader(true)).execute(Integer.toString(mPage));

        Log.d(ACTIVITY_TAG,"ON CREATE VIEW" + mPage);

        //refreshData();


        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState){
        //As the name states, this is called after the Activity's onCreate() has completed.
        // It is called after onCreateView(),
        // and is mainly used for final initialisations (for example, modifying UI elements).
        super.onActivityCreated(savedInstanceState);
        Log.d("JB_progress","onActivityCreated:" + mPage);




        //refreshData();

    }

    @Override
    public void onDestroyView (){
        super.onDestroyView();
        //pagePresenter.onStop();
        Log.d(ACTIVITY_TAG,"onDestroyView:" + mPage);

    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(ACTIVITY_TAG,"OnStop:" + mPage);
        pagePresenter.onStop();

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(ACTIVITY_TAG,"OnResume:" + mPage);
        refreshData();

    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(ACTIVITY_TAG,"OnPause:" + mPage);
        if (handler != null){
            handler.removeCallbacks(runnable);
        }

        hideSearchProgressDialog();

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(ACTIVITY_TAG,"onStart:" + mPage);


    }

    /*
    private void hideSearchProgressDialog(){
        Log.d("JB_progress","hideSearchProgressDialog:" + mPage);
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
            progress = null;
        }

    }*/

    private boolean  hideSearchProgressDialog(){
        Log.d("JB_progress","hideSearchProgressDialog:" + mPage);
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
            progress = null;
            return true;
        }
        return false;

    }

    private void showSearchProgressDialog() {
        Log.d("JB_progress", "showSearchProgressDialog:" +mPage);
        if (this.isVisible()) {
            if (progress == null || (progress != null && !progress.isShowing())) {
                Log.d("JB_progress", "showSearchProgressDialog YES:" + mPage);
                progress = new ProgressDialog(getContext());
                progress.setMessage(getContext().getResources().getString(R.string.pageFragment_searching));
                progress.setCancelable(false);
                progress.show();
                handler = new Handler();
                handler.postDelayed(runnable, Constant.LONG_TIME_LOADING_IN_MS);
            }
        }


    }




    private void refreshData() {
        Log.d(ACTIVITY_TAG,"refreshData");

        mSwipeRefreshLayout.setRefreshing(true);

        /*professionalList = new ArrayList<>();
        (new AsyncRecyclerViewLoader(Boolean.FALSE)).execute("refresh");*/
        /*switch (mPage) {
            case 0:
                pagePresenter.getCloserProfessionals();

                break;
            case 1:

                pagePresenter.getBestRatedProfessionals();


                break;
            case 2:
                pagePresenter.get24hProfessionals();


                break;
            case 3:
                pagePresenter.getCheaperProfessionals();


                break;



        }*/
        pagePresenter.getProfessionals();


        //Toast.makeText(getContext(), "Buscando: " , Toast.LENGTH_LONG).show();

        //mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onRVItemClick(final Professional professional) {
        Log.d(ACTIVITY_TAG,"onRVItemClick:" + professional.toString());
        Intent activity = new Intent(getActivity(),ProfessionalActivity.class);
        activity.putExtra("professionalObject", new Gson().toJson(professional));
        //OJO FOR ALVAREZ
        //startActivity(activity);



    }

    @Override
    public void updateProfessionalList(List<Professional> professionalList) {
        //this.professionalList = professionalList;
        Log.d("JB_progress","updateProfessionalList");
        mAdapter.setItemList(professionalList);
        Log.d(ACTIVITY_TAG,"updateProfessionalList");
        mAdapter.notifyDataSetChanged();
        hideSearchProgressDialog();





    }

    @Override
    public void addProfessional(Professional professional) {
        //mAdapter.addProfessional(professional,comparator,doReverse);
        Log.d("JB_progress","addProfessional");
        mAdapter.addProfessional(professional,comparator);
        if (mAdapter.getItemCount() == 1) {
            hideSearchProgressDialog();
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("JB_REFRESH", "OFF ADD");
            }
        }

    }

    @Override
    public void updateProfessional(Professional professional) {
        Log.d("JB_progress","updateProfessional");
        mAdapter.updateProfessional(professional);


    }

    @Override
    public void removeProfessional(String key) {
        Log.d("JB_progress","removeProfessional");
        mAdapter.removeProfessional(key);



    }

    @Override
    public void removeAllProfessionals() {
        Log.d("JB_progress","removeAllProfessionals");
        mAdapter.removeAllProfessionals();


    }

    @Override
    public void setProfessionalList(List<Professional> professionalList) {
        //mAdapter.updateProfessionalList(professionalList,comparator,doReverse);
        Log.d("JB_progress","setProfessionalList");
        mAdapter.updateProfessionalList(professionalList,comparator);

        int timeShowingRefresh = Constant.SHORT_TIME_LOADING_IN_MS;

        if (professionalList.isEmpty()){
            //timeShowingRefresh = Constant.LONG_TIME_LOADING_IN_MS;
            showSearchProgressDialog();
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("JB_REFRESH","EMPTY LIST");
            }
        }else{
            hideSearchProgressDialog();
        }

        Log.d("JB_REFRESH","DELAY:" + timeShowingRefresh);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Log.d("JB_REFRESH","OFF DELAYED:");
                }


            }
        }, timeShowingRefresh);


    }




    /*private class AsyncRecyclerViewLoader extends AsyncTask<String, Void, List<Professional>> {

        private final ProgressDialog dialog = new ProgressDialog(getContext());
        private final Boolean use_dialog;

        public AsyncRecyclerViewLoader(Boolean use_dialog) {

            this.use_dialog = use_dialog;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(ACTIVITY_TAG,"onPreExecute");

            if (this.use_dialog) {
                Log.d(ACTIVITY_TAG,"use dialog");
                dialog.setMessage(getString(R.string.login_message_success));
                dialog.show();

            }

        }

        protected List<Professional> doInBackground(String... params) {


            Log.d(ACTIVITY_TAG,"doInBackground:" + mPage);
            List<Professional> result = new ArrayList<>();



            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return result;


        }

        @Override
        protected void onPostExecute(List<Professional> result) {
            super.onPostExecute(result);

            Log.d(ACTIVITY_TAG,"onPostExecute:" + result.toString());

            //mAdapter.setItemList(result);
            //mAdapter.notifyDataSetChanged();
            if (this.use_dialog) {


                dialog.dismiss();


            } else {
                mSwipeRefreshLayout.setRefreshing(false);
            }


        }




    }*/


}
