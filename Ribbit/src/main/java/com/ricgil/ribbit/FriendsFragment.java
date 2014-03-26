package com.ricgil.ribbit;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by rgomes on 24-03-2014.
 */
public class FriendsFragment extends ListFragment {
    private static String TAG = FriendsFragment.class.getSimpleName();

    protected List<ParseUser> mFriends = null;
    protected ParseRelation<ParseUser> mFriendsRelation = null;
    protected ParseUser mCurrentUser = null;

    protected FriendsFragment(){}

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FriendsFragment newInstance() {
        FriendsFragment fragment = new FriendsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCurrentUser = ParseUser.getCurrentUser();
        mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);

        getActivity().setProgressBarIndeterminateVisibility(true);
        ParseQuery<ParseUser> query = mFriendsRelation.getQuery();
        query.addAscendingOrder(ParseConstants.KEY_USERNAME);
        query.whereNotEqualTo(ParseConstants.KEY_OBJECTID, mCurrentUser.getObjectId());

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> friends, ParseException e) {
                getActivity().setProgressBarIndeterminateVisibility(false);
                if(e==null){
                mFriends = friends;

                String[] usernames = new String[mFriends.size()];
                int i = 0;
                for (ParseUser user : mFriends) {
                    usernames[i] = user.getUsername();
                    i++;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, usernames);

                setListAdapter(adapter);
                }else{
                    //Error
                    Log.e(TAG, e.getMessage());
                    AlertDialog.Builder alert = new AlertDialog.Builder(getListView().getContext());
                    alert.setMessage(e.getMessage())
                            .setTitle(getString(R.string.error_title))
                            .setPositiveButton(android.R.string.ok, null);

                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            }
        });
    }


}
