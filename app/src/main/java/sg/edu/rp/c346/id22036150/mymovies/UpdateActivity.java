package sg.edu.rp.c346.id22036150.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amrdeveloper.lottiedialog.LottieDialog;

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
                Toast.makeText(UpdateActivity.this, "Details updated!", Toast.LENGTH_SHORT).show();
                dbh.close();
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }


        });
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LottieDialog dialog = new LottieDialog(UpdateActivity.this)
                        .setAnimation(R.raw.animation_ll1uujr2)
                        .setAnimationRepeatCount(LottieDialog.INFINITE)
                        .setAutoPlayAnimation(true)
                        .setTitle("Danger")
                        .setTitleColor(Color.BLACK)
                        .setMessage("Are you sure you want to delete the movie " + data.getTitle() + "?")
                        .setMessageColor(Color.BLACK)
                        .setDialogBackground(Color.WHITE)
                        .setCancelable(false);


                // This button is the 'DELETE' button where when user presses it, it deletes the movie from the database
                Button deleteButton = new Button(UpdateActivity.this);
                deleteButton.setText("DELETE");
                deleteButton.setOnClickListener(view -> {
                    DBHelper dbh = new DBHelper(UpdateActivity.this);
                    dbh.deleteMovie(data.getId());
                    Intent intent = new Intent(UpdateActivity.this, DetailsActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                });

                // This button is the 'CANCEL' button where when user presses it, it cancels the deletion process of the movie
                Button cancelButton = new Button(UpdateActivity.this);
                cancelButton.setText("CANCEL");
                cancelButton.setOnClickListener(view -> dialog.dismiss());

                dialog.addActionButton(deleteButton)
                        .addActionButton(cancelButton)
                        .setOnShowListener(dialogInterface -> {
                        })
                        .setOnDismissListener(dialogInterface -> {
                        })
                        .setOnCancelListener(dialogInterface -> {
                        })
                        .show();
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LottieDialog dialog = new LottieDialog(UpdateActivity.this)
                        .setAnimation(R.raw.animation_ll1uktyk)
                        .setAnimationRepeatCount(LottieDialog.INFINITE)
                        .setAutoPlayAnimation(true)
                        .setTitle("Danger")
                        .setTitleColor(Color.BLACK)
                        .setMessage("Are you sure you want to discard the changes?")
                        .setMessageColor(Color.BLACK)
                        .setDialogBackground(Color.WHITE)
                        .setCancelable(false);

                Button doNotDiscardButton = new Button(UpdateActivity.this);
                doNotDiscardButton.setText("DO NOT DISCARD");
                doNotDiscardButton.setOnClickListener(view1 -> dialog.dismiss());

                // This is the 'DISCARD' button, where when user presses it, the changes that the user entered will be cleared and no
                // changes will be made.
                Button discardButton = new Button(UpdateActivity.this);
                discardButton.setText("DISCARD");
                discardButton.setOnClickListener(view1 -> {
                    Intent intent = new Intent(UpdateActivity.this, DetailsActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                });

                // This is the 'DO NOT DISCARD' button, where when user presses it, the user remains on the updateActivity page, with the
                // changes still in the inputs.
                dialog.addActionButton(doNotDiscardButton)
                        .addActionButton(discardButton)
                        .setOnShowListener(dialogInterface -> {
                        })
                        .setOnDismissListener(dialogInterface -> {
                        })
                        .setOnCancelListener(dialogInterface -> {
                        })
                        .show();
            }
        });


    }
}