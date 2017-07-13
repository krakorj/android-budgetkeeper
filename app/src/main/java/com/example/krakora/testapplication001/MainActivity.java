package com.example.krakora.testapplication001;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.krakora.testapplication001.data.TableArtists;
import com.example.krakora.testapplication001.data.TableTracks;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // DBFlow init
        FlowManager.init(this);
        final ModelAdapter<TableTracks> db_tracks = FlowManager.getModelAdapter(TableTracks.class);
        final ModelAdapter<TableArtists> db_artists = FlowManager.getModelAdapter(TableArtists.class);

        // ListView
        ListView lst = (ListView) findViewById(R.id.list_view);
        // ListView data
        //  Create an empty List from String Array elements
        String[] values = new String[] {};
        final List<String> item_list = new ArrayList<String>(Arrays.asList(values));
        //  Get data from DB
        List<TableArtists> ql = new Select().from(TableArtists.class).queryList();
        for (int i = 1; i < ql.size(); i++){
            item_list.add(ql.get(i).Name);
        };
        Collections.sort(item_list);
        //  Create an ArrayAdapter from the ListView
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, item_list);
        //  Bind data
        lst.setAdapter(arrayAdapter);


        // FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add data to DB
                TableArtists a = new TableArtists();
                a.Name = "\"\"\"Eviiii :*:)";
                db_artists.insert(a);
                // Get data from DB to present them to the list
                List<TableArtists> al = new Select().from(TableArtists.class).queryList();
                item_list.clear();
                for (int i = 1; i < al.size(); i++){
                   item_list.add(al.get(i).Name);
                };
                Collections.sort(item_list);
                // Inform
                Snackbar.make(view, "New item `" + a.Name + "` added!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // Present it
                arrayAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
