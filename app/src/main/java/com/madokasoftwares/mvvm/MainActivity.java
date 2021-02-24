package com.madokasoftwares.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Toast;

import com.madokasoftwares.mvvm.Adapter.NoteAdapter;
import com.madokasoftwares.mvvm.Room.Note;
import com.madokasoftwares.mvvm.ViewModel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel; //member variable for our viewmodel
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      RecyclerView recyclerView = findViewById(R.id.recycler_view);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setHasFixedSize(true);

      NoteAdapter adapter = new NoteAdapter();
      recyclerView.setAdapter(adapter);


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);//the android system will destroy the model when the activity is finished
        //this returns livedata
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
           @Override
           public void onChanged(List<Note> notes) {
         //update our reycleview
               adapter.setNotes(notes);//set notes was our update method we created

           }
       });

    }
}