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
import android.widget.EditText;

import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.search.interfaces.NavigateButtonClick;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionSearchFragment extends Fragment {


    private static final String ACTIVITY_TAG = Constant.TAG + ":QuestSearchFragment";
    private static final String ARG_PAGE = "ARG_PAGE";
    private int page;
    private NavigateButtonClick navigateButtonClick;
    private OnFragmentInteractionListener mListener;
    private SharedPreferences settings;

    @BindView(R.id.inputQuestion)
    EditText question;





    public QuestionSearchFragment() {
        // Required empty public constructor
    }

    public static QuestionSearchFragment newInstance(int page){
        Log.d(ACTIVITY_TAG,"NewInstance QuestionSearchFragment:" + page);
        QuestionSearchFragment fragment = new QuestionSearchFragment();
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
            Log.d(ACTIVITY_TAG,"OnCreate QuestionSearchFragment:" + page);

        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(ACTIVITY_TAG,"OnDestroy");

    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(ACTIVITY_TAG,"onStop");
        saveData();
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(ACTIVITY_TAG,"onPause");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_question_search, container, false);
        ButterKnife.bind(this,view);
        settings = getContext().getSharedPreferences(Constant.PREFS_SEARCH, 0);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        question.setText(settings.getString(Constant.QUESTION,""));
    }



    @OnClick(R.id.btnSave)
    public void handleBtnSave(){
        Log.d(ACTIVITY_TAG,"QuestionSearchFragment");
        navigateButtonClick.saveButtonClicked();


    }

    private void saveData(){
        SharedPreferences.Editor editor = settings.edit();

        String text = question.getText().toString();

        if (text != null){
            editor.putString(Constant.QUESTION,text);
        }

        editor.commit();
    }

    @OnClick(R.id.btnPrevPage)
    public void handlePrevPageClick(){
        Log.d(ACTIVITY_TAG,"QuestionSearchFragment");
        if (navigateButtonClick != null){
            navigateButtonClick.prevButtonClicked(page);
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

}
