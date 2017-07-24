package com.example.krakora.budgetkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.krakora.budgetkeeper.data.TableArtists;
import com.example.krakora.budgetkeeper.data.TableTracks;
import com.example.krakora.budgetkeeper.data.TableTracks_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private boolean priceTogle;

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

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
        /*
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
        */

        // Transaction ListView via RecyclerView
        //      http://androidcss.com/android/fetch-json-data-android/
        // Create empty list of transaction objects
        List<TableTracks> data = new Select().
                from(TableTracks.class).
                where(TableTracks_Table.Name.is("Outcome")).
                or(TableTracks_Table.Name.is("Income")).
                orderBy(TableTracks_Table.Name,true).
                queryList();
        // Setup and Handover data to RecyclerView
        RecyclerView rvtl = (RecyclerView) findViewById(R.id.transaction_list);
        final AdapterTransaction transactionAdapter = new AdapterTransaction(MainActivity.this, data);
        rvtl.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvtl.setLayoutManager(mLayoutManager);
        rvtl.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvtl.setItemAnimator(new DefaultItemAnimator());
        rvtl.setAdapter(transactionAdapter);

        // priceTogle init
        priceTogle = false;
        rvtl.addOnItemTouchListener(
            new RecyclerTouchListener(this, rvtl, new ClickListener() {
                @Override
                public void onClick(View view, final int position) {
                    //Values are passing to activity & to fragment as well
                    Snackbar.make(view,
                            "Single Click on position :" + position,
                            Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }

                @Override
                public void onLongClick(View view, int position) {
                    Snackbar.make(view, "Long press on position :" + position,
                            Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
        }));


        // FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // RecyclerView change handling
                // Inset an item
                TableTracks t = new TableTracks();
                if(priceTogle) {
                    t.Name = "Income";
                    t.UnitPrice = 100;
                    priceTogle = false;
                } else {
                    t.Name = "Outcome";
                    t.UnitPrice = -100;
                    priceTogle = true;
                };
                db_tracks.insert(t);
                // Update List
                List<TableTracks> data = new Select().
                    from(TableTracks.class).
                    where(TableTracks_Table.Name.is("Outcome")).
                    or(TableTracks_Table.Name.is("Income")).
                    orderBy(TableTracks_Table.Name,true).
                    queryList();
                transactionAdapter.updateData(data);
                // Inform
                Snackbar.make(view,
                        "Fictive transaction `" + t.Name + "` added!",
                        Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
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
        //
        switch(id) {
            case R.id.action_settings:
                Intent sa = new Intent(this, SettingsActivity.class);
                this.startActivity(sa);
                break;
            case R.id.action_about:
                Intent aa = new Intent(this, AboutActivity.class);
                this.startActivity(aa);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
