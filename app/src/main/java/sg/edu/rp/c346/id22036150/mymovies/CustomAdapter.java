package sg.edu.rp.c346.id22036150.mymovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Movie> movieList;
    public CustomAdapter( Context context, int resource,  ArrayList<Movie> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        movieList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvTitle = rowView.findViewById(R.id.textViewTitle);
        TextView tvGenre = rowView.findViewById(R.id.textViewGenre);
        TextView tvYear = rowView.findViewById(R.id.textViewYear);
        ImageView ivRating = rowView.findViewById(R.id.imageViewRating);

        Movie movie = movieList.get(position);

        tvTitle.setText(movie.getTitle());
        tvGenre.setText(movie.getGenre());
        tvYear.setText(""+ movie.getYear());

        String image = getRatingImageUrl(movie.getRating());
        Picasso.with(parent_context).load(image).resize(100,100).into(ivRating);


        return rowView;
    }

    private String getRatingImageUrl(String rating) {
        if (rating.equals("G"))
            return "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16277-28797ce.jpg?quality=90&webp=true&fit=584,471";
        else if (rating.equals("PG"))
            return "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16278-28797ce.jpg?quality=90&webp=true&fit=584,471";
        else if (rating.equals("PG13"))
            return "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16279-8d5bdb7.jpg?quality=90&webp=true&fit=490,490";
        else if (rating.equals("NC16"))
            return "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16281-8d5bdb7.jpg?quality=90&webp=true&fit=490,490";
        else if (rating.equals("M18"))
            return "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16282-05127b2.jpg?quality=90&webp=true&fit=300,300";
        else if (rating.equals("R21"))
            return "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16283-05127b2.jpg?quality=90&webp=true&fit=515,424";
        else
            return "";
    }
}
