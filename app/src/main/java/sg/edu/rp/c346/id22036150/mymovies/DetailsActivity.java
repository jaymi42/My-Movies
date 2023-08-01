package sg.edu.rp.c346.id22036150.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    ListView lvMovies;
    Button btnRating;
    //Button btnPG13;
    ArrayList<Movie> movies;
    CustomAdapter adapter;
    Spinner dtSpn;
    ArrayAdapter<String> aaSpn;
    ArrayList<Movie> strList;
    String selectedRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intentSelected = getIntent();
        btnRating = findViewById(R.id.btnRating);
        lvMovies = findViewById(R.id.lvMovies);
        //btnPG13 = findViewById(R.id.btnPG13);
        dtSpn = findViewById(R.id.spinner);

        strList = new ArrayList<>();
        aaSpn = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);

        ArrayList<String> rate = new ArrayList<>();
        DBHelper db = new DBHelper(DetailsActivity.this);
        ArrayList<Movie> movieList = db.getMovies();
        for (Movie movie: movieList){
            String rating = movie.getRating();
            if(!rate.contains(rating)){
                rate.add(rating);
            }
        }

        for(String rating: rate){
            aaSpn.add(rating);
        }

        //dtSpn.setAdapter(aaSpn);

        movies = new ArrayList<Movie>();
        adapter = new CustomAdapter(DetailsActivity.this, R.layout.row, movies);
        lvMovies.setAdapter(adapter);

        DBHelper dbh = new DBHelper(DetailsActivity.this);
        movies.clear();
        movies.addAll(dbh.getMovies());
        adapter.notifyDataSetChanged();

        ArrayList<String> data = dbh.getMovieContent();


        dtSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRating = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        if (!movies.isEmpty()) {
            lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movie data = movies.get(position);

                    Intent intent = new Intent(DetailsActivity.this, UpdateActivity.class);
                    intent.putExtra("data", data);
                    startActivity(intent);
                }
            });
        }

        /**btnPG13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(DetailsActivity.this);

                ArrayList<Movie> PG13List = new ArrayList<>();

                for(Movie movie: movies){
                    if(movie.getRating().equals("PG13")){
                        PG13List.add(movie);
                    }
                }

                adapter = new CustomAdapter(DetailsActivity.this, R.layout.row, PG13List);
                lvMovies.setAdapter(adapter);
            }
        });**/

        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(DetailsActivity.this);

                if (selectedRating != null) {
                        ArrayList<Movie> selectedRatingMovies = db.getMoviesByRating(selectedRating);
                        adapter = new CustomAdapter(DetailsActivity.this, R.layout.row, selectedRatingMovies);
                        lvMovies.setAdapter(adapter);

                } else {
                    movies.clear();
                    movies.addAll(db.getMovies());
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(DetailsActivity.this);

    }
}