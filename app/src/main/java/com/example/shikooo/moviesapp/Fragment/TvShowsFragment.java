package com.example.shikooo.moviesapp.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shikooo.moviesapp.Interface.Service;
import com.example.shikooo.moviesapp.R;
import com.example.shikooo.moviesapp.adapter.TvAdapter;
import com.example.shikooo.moviesapp.api.Client;
import com.example.shikooo.moviesapp.model.Tv;
import com.example.shikooo.moviesapp.model.TvResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView tvRecyclerView;
    private TvAdapter adapter;
    private List<Tv> tvList;
    View view;
    ProgressDialog pd;


    public static TvShowsFragment newInstance(String param1, String param2) {
        TvShowsFragment fragment = new TvShowsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_tv_shows, container, false);

        initview();
        loadJson();

        return view;
    }

    private void initview()
    {

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Fetching  Tv Shows");
        pd.setCancelable(false);
        pd.show();

        tvRecyclerView = (RecyclerView) view.findViewById(R.id.tv_recycler_view);
        tvList = new ArrayList<>();
        adapter = new TvAdapter(getActivity(),tvList);
        tvRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        tvRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tvRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadJson ()
    {
        Service apiServce = Client.getClient().create(Service.class);
        Call<TvResponse> call = apiServce.getTvShows("2a012958dc19d5c620aec582aa127912");
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                List<Tv> tvList = response.body().getResults();
                tvRecyclerView.setAdapter(new TvAdapter(getActivity(),tvList));
                tvRecyclerView.smoothScrollToPosition(0);

                pd.dismiss();

            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "Error to fetch data!!", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
