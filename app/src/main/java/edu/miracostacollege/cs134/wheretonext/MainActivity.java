package edu.miracostacollege.cs134.wheretonext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import edu.miracostacollege.cs134.wheretonext.model.College;
import edu.miracostacollege.cs134.wheretonext.model.JSONLoader;

public class MainActivity extends AppCompatActivity {

    private List<College> collegesList;
    private CollegeListAdapter collegeListAdapter;
    private ListView collegesListView;
    private EditText nameET;
    private EditText populationET;
    private EditText tuitionET;
    private RatingBar userRatingBar;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        collegesListView = findViewById(R.id.collegeListView);
        nameET = findViewById(R.id.nameEditText);
        populationET = findViewById(R.id.populationEditText);
        tuitionET = findViewById(R.id.tuitionEditText);
        userRatingBar = findViewById(R.id.collegeRatingBar);



        // DONE:  Fill the collegesList with all Colleges from the JSON
        try {
            collegesList = JSONLoader.loadJSONFromAsset(this);
        } catch (IOException e) {
            Log.e("Where To Next", "Error loading JSON" + e.getMessage());
        }


        // DONE?:  Connect the list adapter with the list
        collegeListAdapter = new CollegeListAdapter(this, R.layout.college_list_item, collegesList);
        // DONE?:  Set the list view to use the list adapter
        collegesListView.setAdapter(collegeListAdapter);


        collegesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewCollegeDetails(view, position);
            }
        });
    }







    public void viewCollegeDetails(View v, int position) {

        int pos = (int) v.getTag();

        // DONE: Implement the view college details using an Intent
        Intent i = new Intent(this, CollegeDetailsActivity.class);

        College c = collegesList.get(position);
        i.putExtra("name", c.getName());
        i.putExtra("annualEnrollment", c.getPopulation());
        i.putExtra("tuition", c.getTuition());
        i.putExtra("rating", c.getRating());
        i.putExtra("imageName", c.getImageName());


        startActivity(i);
    }

    public void addCollege(View view) {

        // done: Implement the code for when the user clicks on the addCollegeButton

        // get edittexts and create new object then add to collegesList
        String name = nameET.getText().toString();
        int population = Integer.parseInt(populationET.getText().toString());
        double tuition = Double.parseDouble(tuitionET.getText().toString());
        double rating = userRatingBar.getRating();
        String imageName = "college.png";

        College c= new College(name, population, tuition, rating);
        c.setImageName(imageName);

        collegesList.add(c);

        //      then notify data changes
        collegeListAdapter.notifyDataSetChanged();
    }

}
