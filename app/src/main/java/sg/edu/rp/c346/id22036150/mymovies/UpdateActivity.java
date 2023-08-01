package sg.edu.rp.c346.id22036150.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText etTitle, etGenre, etYear, etID;
    Spinner rating;
    Button Update;
    Button Delete;
    Button Cancel;
    Movie data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etID = findViewById(R.id.etID);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        Update = findViewById(R.id.update);
        Delete = findViewById(R.id.Delete);
        Cancel = findViewById(R.id.Cancel);
        rating = findViewById(R.id.spinnerRating);

        Intent intentSelected = getIntent();
        data = (Movie) intentSelected.getSerializableExtra("data");

        etID.setText("" + data.getId());
        etID.setEnabled(false);
        etTitle.setText(data.getTitle());
        etGenre.setText(data.getGenre());
        etYear.setText("" + data.getYear());


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerItems, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rating.setAdapter(adapter);
        int position = adapter.getPosition(data.getRating());
        rating.setSelection(position);


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbh = new DBHelper(UpdateActivity.this);
                data.setTitle(etTitle.getText().toString());
                data.setGenre(etGenre.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));
                data.setRating(rating.getSelectedItem().toString());

                dbh.updateMovie(data);
                Toast.makeText(UpdateActivity.this,"Details updated!",Toast.LENGTH_SHORT).show();
                dbh.close();
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }


        });
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(UpdateActivity.this);
                dbh.deleteMovie(data.getId());
                Intent intent = new Intent(UpdateActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });
    }


}