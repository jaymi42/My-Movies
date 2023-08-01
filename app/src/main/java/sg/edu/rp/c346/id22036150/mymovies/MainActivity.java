package sg.edu.rp.c346.id22036150.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnShow;
    EditText etTitle, etGenre, etYear;
    Spinner mvSpn;
    String[] ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnShow = findViewById(R.id.btnShow);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        mvSpn = findViewById(R.id.spinnerMv);

        ratings = getResources().getStringArray(R.array.spinnerItems);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ratings);
        mvSpn.setAdapter(adapter);

        mvSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rating = ratings[position];

                btnInsert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBHelper dbh = new DBHelper(MainActivity.this);

                        String title = etTitle.getText().toString();
                        String genre = etGenre.getText().toString();
                        int year = Integer.parseInt(etYear.getText().toString());

                        dbh.insertMovie(title, genre, year, rating);
                        Toast.makeText(MainActivity.this,"Movie added!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}