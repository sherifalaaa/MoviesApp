package com.example.shikooo.moviesapp.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shikooo.moviesapp.Interface.Service;
import com.example.shikooo.moviesapp.R;
import com.example.shikooo.moviesapp.adapter.MoviesAdapter;
import com.example.shikooo.moviesapp.api.Client;
import com.example.shikooo.moviesapp.model.Movie;
import com.example.shikooo.moviesapp.model.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movieList;
    View view;
    Context context;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_movies, container, false);

        initview();
        loadjson();

        return view;
    }

    private void initview()
    {

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Fetching  Movies");
        pd.setCancelable(false);
        pd.show();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        movieList = new ArrayList<>();
        adapter = new MoviesAdapter(movieList,getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadjson()
    {
            Service apiservice = Client.getClient().create(Service.class);

            Call<MoviesResponse> call = apiservice.getPopularMovies("2a012958dc19d5c620aec582aa127912");

            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();

                    recyclerView.setAdapter(new MoviesAdapter(movies,getActivity()));
                    recyclerView.smoothScrollToPosition(0);

                    pd.dismiss();

                }

                @Override
                public void onFailure(retrofit2.Call<MoviesResponse> call, Throwable t) {

                    Log.d("Error" ,t.getMessage());
                    Toast.makeText(getActivity(), "Error fetch data!", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });

        }
}
