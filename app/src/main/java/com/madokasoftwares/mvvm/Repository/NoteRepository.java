package com.madokasoftwares.mvvm.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;

import com.madokasoftwares.mvvm.Room.Note;
import com.madokasoftwares.mvvm.Room.NoteDao;
import com.madokasoftwares.mvvm.Room.NoteDatabase;

import java.util.List;

//repository class provides an absraction layer btween different data sources and the rest of te app
//we will call this methods to the viewmodel
public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){ // our constructor
        NoteDatabase database = NoteDatabase.getInstance(application);//
         noteDao=database.noteDao();//absract method in our database class
        allNotes = noteDao.getAllNotes();
    }

    //methods that the API exposes to the outside
    public void insert(Note note){
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }
    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNoteAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes(){

    return allNotes;
    }

    //executing the codein background using th asyncTask
    private static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void>{
       private NoteDao noteDao;
       private InsertNoteAsyncTask(NoteDao noteDao){
           this.noteDao = noteDao;
       }
        @Override
        protected Void doInBackground(Note... notes){

           noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        private DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes){

            noteDao.delete(notes[0]);
            return null;
        }
    }


    private static class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes){

            noteDao.update(notes[0]);
            return null;
        }
    }


    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;
        private DeleteAllNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids){
            noteDao.deleteAllNotes();
            return null;
        }
    }





}
