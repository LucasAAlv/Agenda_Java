package com.pucpr.agenda.controller;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.snackbar.Snackbar;
import com.pucpr.agenda.R;
import com.pucpr.agenda.model.Contact;
import com.pucpr.agenda.model.DataModel;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ContactAdapter adapter = new ContactAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        DataModel.getInstance().createDatabase(getApplicationContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(MainActivity.this)
        );
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
                MainActivity.this,DividerItemDecoration.VERTICAL
        );
        recyclerView.addItemDecoration(itemDecoration);

        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                goToDetailActivity(position);
            }
        });

        adapter.setOnItemLongClickListener(new ContactAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                Contact c = DataModel.getInstance().getContact(position);
                DataModel.getInstance().removeContact(position);
                adapter.notifyItemRemoved(position);

                View contextView = findViewById(android.R.id.content);
                Snackbar.make(contextView,R.string.remove_contact,Snackbar.LENGTH_LONG).setAction(R.string.undo,new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        DataModel.getInstance().insertContact(c,position);
                        adapter.notifyItemInserted(position);
                    }
                });
                return true;
            }
        });
    }

    protected void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    void goToDetailActivity(int index){
        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra("index",index);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add_contact){
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            startActivity(intent);
            goToDetailActivity(-1);
        }
        return super.onOptionsItemSelected(item);
    }
}