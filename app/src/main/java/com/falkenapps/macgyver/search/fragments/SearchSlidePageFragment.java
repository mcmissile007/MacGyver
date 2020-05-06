package com.falkenapps.macgyver.search.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchSlidePageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchSlidePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchSlidePageFragment extends Fragment {


    private static final String ACTIVITY_TAG = Constant.TAG + ":SearchPageFragment";
    private static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;



    private OnFragmentInteractionListener mListener;


    public SearchSlidePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param page in wizard
     * @return A new instance of fragment SearchSlidePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchSlidePageFragment newInstance(int page) {
        Log.d(ACTIVITY_TAG,"newInstance:" + page);
        SearchSlidePageFragment fragment = new SearchSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
            Log.d(ACTIVITY_TAG,"OnCreate SearchFragment:" + mPage);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_search, container, false);
        if (mPage == 0){
            view = inflater.inflate(R.layout.fragment_job_search, container, false);

        }if (mPage == 1)
        {
            view = inflater.inflate(R.layout.fragment_location_search, container, false);

        }if(mPage == 2){

            view = inflater.inflate(R.layout.fragment_question_search, container, false);


        }

        return  view;
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
