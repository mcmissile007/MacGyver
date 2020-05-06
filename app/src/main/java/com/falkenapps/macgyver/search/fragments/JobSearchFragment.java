package com.falkenapps.macgyver.search.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.search.interfaces.NavigateButtonClick;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobSearchFragment extends Fragment {

    private static final String ACTIVITY_TAG = Constant.TAG + ":JobSearchFragment";
    private static final String ARG_PAGE = "ARG_PAGE";
    private int page;
    private NavigateButtonClick navigateButtonClick;
    private OnFragmentInteractionListener mListener;
    private SharedPreferences settings;
    private Context context;


    public JobSearchFragment() {
        // Required empty public constructor
    }



    public static JobSearchFragment newInstance(int page){
        Log.d(ACTIVITY_TAG,"NewInstance JobSearchFragment:" + page);
        JobSearchFragment fragment = new JobSearchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE,page);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_PAGE);
            Log.d(ACTIVITY_TAG,"OnCreate JobSearchFragment:" + page);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_search, container, false);
        ButterKnife.bind(this,view);
        settings = getContext().getSharedPreferences(Constant.PREFS_SEARCH, 0);
        String category = settings.getString(Constant.JOB_CATEGORY_NAME,"");
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat1))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat1);
            radioButton.setChecked(true);

        }
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat2))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat2);
            radioButton.setChecked(true);

        }
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat3))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat3);
            radioButton.setChecked(true);

        }
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat4))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat4);
            radioButton.setChecked(true);

        }
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat5))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat5);
            radioButton.setChecked(true);

        }
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat6))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat6);
            radioButton.setChecked(true);

        }
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat7))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat7);
            radioButton.setChecked(true);

        }
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat8))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat8);
            radioButton.setChecked(true);

        }
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat9))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat9);
            radioButton.setChecked(true);

        }
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat10))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat10);
            radioButton.setChecked(true);

        }
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat11))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat11);
            radioButton.setChecked(true);

        }
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat12))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat12);
            radioButton.setChecked(true);

        }
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat13))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat13);
            radioButton.setChecked(true);

        }
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat14))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat14);
            radioButton.setChecked(true);

        }
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat15))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat15);
            radioButton.setChecked(true);

        }
        if (category.equals(context.getResources().getString(R.string.search_fragment_job_cat16))){
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.cat16);
            radioButton.setChecked(true);

        }


        /*if (category != -1){
            RadioButton radioButton = (RadioButton) view.findViewById(category);
            radioButton.setChecked(true);

        }*/



        return view;
    }

    @OnClick(R.id.btnNextPage)
    public void handleNextPageClick(){
        Log.d(ACTIVITY_TAG,"JobSearchNextPage");
        if (navigateButtonClick != null){
            navigateButtonClick.nextButtonClicked(page);
        }


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        Activity activity = (Activity) context;
        this.context = context;
        navigateButtonClick = (NavigateButtonClick) activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @OnClick({ R.id.cat1, R.id.cat2, R.id.cat3, R.id.cat4, R.id.cat5, R.id.cat6,
    R.id.cat7,R.id.cat8, R.id.cat9, R.id.cat10,R.id.cat11,R.id.cat12,R.id.cat13,
    R.id.cat14,R.id.cat15,R.id.cat16})
    public void onRadioButtonClicked(RadioButton radioButton) {
        // Is the button now checked?
        boolean checked = radioButton.isChecked();

        SharedPreferences.Editor editor = settings.edit();

        // Check which radio button was clicked
        switch (radioButton.getId()) {
            case R.id.cat1:
                if (checked) {
                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat1);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat1));

                }
                break;
            case R.id.cat2:
                if (checked) {
                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat2);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat2));
                }
                break;
            case R.id.cat3:
                if (checked) {
                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat3);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat3));

                }
                break;
            case R.id.cat4:
                if (checked) {
                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat4);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat4));
                }
                break;
            case R.id.cat5:
                if (checked) {
                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat5);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat5));
                }
                break;
            case R.id.cat6:
                if (checked) {
                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat6);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat6));
                }
                break;
            case R.id.cat7:
                if (checked) {
                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat7);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat7));

                }
                break;
            case R.id.cat8:
                if (checked) {
                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat8);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat8));
                }
                break;
            case R.id.cat9:
                if (checked) {
                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat9);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat9));
                }
                break;
            case R.id.cat10:
                if (checked) {
                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat10);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat10));
                }
                break;
            case R.id.cat11:
                if (checked) {
                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat11);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat11));
                }
                break;
            case R.id.cat12:
                if (checked) {
                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat12);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat12));
                }
                break;
            case R.id.cat13:
                if (checked) {
                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat13);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat13));
                }
                break;
            case R.id.cat14:
                if (checked) {

                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat14);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat14));
                }
                break;
            case R.id.cat15:
                if (checked) {

                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat15);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat15));
                }
                break;
            case R.id.cat16:
                if (checked) {

                    editor.putInt(Constant.JOB_CATEGORY_ID,R.id.cat16);
                    editor.putString(Constant.JOB_CATEGORY_NAME,context.getResources().getString(R.string.search_fragment_job_cat16));
                }
                break;

        }

        editor.commit();

    }



}
