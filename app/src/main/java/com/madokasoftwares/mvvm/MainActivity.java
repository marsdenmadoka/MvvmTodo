package com.madokasoftwares.mvvm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.madokasoftwares.mvvm.Adapter.NoteAdapter;
import com.madokasoftwares.mvvm.Room.Note;
import com.madokasoftwares.mvvm.ViewModel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST=1;

    private NoteViewModel noteViewModel; //member variable for our viewmodel
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddNoteActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST); //this is because we want to get the results/input from addnoteactivity back
                //ADD_NOTE_REQUEST s this will help us to distuguish between the requests since we can call start activity for different activity
            }
        });



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
    //getting the results from AddNoteActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && requestCode == RESULT_OK){ //in order t receive te intents well our resultscode should match
          //we now retrive the extras
          String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
          String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
          int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY,1);//we use 1 so that incase the int is missing we assign the 1

            //after we get the results in our main activity we want to save them to the database
            Note note = new Note(title,description,priority);
            noteViewModel.insert(note);
            Toast.makeText(this,"Note Saved!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Note failed  Saved!",Toast.LENGTH_SHORT).show();
        }
    }
}