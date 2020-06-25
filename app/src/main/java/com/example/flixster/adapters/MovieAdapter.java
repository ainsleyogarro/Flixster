package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.ItemMovieBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    List<Movie> movies;
    Context context;
    ItemMovieBinding binding;

    public MovieAdapter(Context context, List<Movie> movies){
        this.movies = movies;
        this.context = context;
    }
    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use layout inflator to inflate a view
        binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()));
        View movieView = binding.getRoot();

        // wrap it inside a View Holder and return
        return new ViewHolder(movieView);
    }
    // Responsible for binding data to a particular View Holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the movie passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the VM
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = binding.tvTitle;
            tvOverview = binding.tvOverview;
            ivPoster = binding.ivPoster;
            itemView.setOnClickListener(this);
        }

        // Update the view inside the View Holder with this data
        public void bind(Movie movie){
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            // Landscape mode sets to backdrop
            // Else is poster
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            }
            else{
                imageUrl = movie.getPosterPath();
            }
            //Glide.with(context).load(imageUrl).into(ivPoster);
            Glide.with(context).load(imageUrl).transform(new RoundedCornersTransformation(30,0)).into(ivPoster);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION){
                // get the movie at the position
                Movie movie = movies.get(position);

                // Intent to display MovieDetailsActivity
                Intent intent = new Intent(context, MovieDetailsActivity.class);

                intent.putExtra(Movie.class.getSimpleName() ,Parcels.wrap(movie));

                context.startActivity(intent);

            }
        }
    }
}
