package com.madokasoftwares.mvvm.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.madokasoftwares.mvvm.Repository.NoteRepository;
import com.madokasoftwares.mvvm.Room.Note;

import java.util.List;

//view model class connects our activity to the repository
//fetches data from respository and send it to the ui activity
public class NoteViewModel extends AndroidViewModel {
   private NoteRepository repository;
   private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository= new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note){
        repository.insert(note);
    }
    public void update(Note note){
        repository.update(note);
    }
    public void delete(Note note){
        repository.delete(note);
    }
    public void deleteAll(){
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){ //returning the live data
        return allNotes;
    }

}
