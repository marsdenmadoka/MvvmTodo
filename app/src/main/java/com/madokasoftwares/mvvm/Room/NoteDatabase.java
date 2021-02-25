package com.madokasoftwares.mvvm.Room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

//Room database
@Database(entities = {Note.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;//we create this since we have to turn the class in a singleton-using the same instance over our app

    public abstract  NoteDao noteDao();

//our database
    //synchronised means only one thread at a time can access this method
    public static synchronized NoteDatabase getInstance(Context context){ //singleton
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
            NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()//this line used when migrating our app version
                    .addCallback(roomCallbak)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallbak = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //executing our poulateDBasycnTask in oncreate
            new populateDBAsyncTask(instance).execute();
        }



    };

    private static class populateDBAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;
        private populateDBAsyncTask(NoteDatabase db){//constructor
            noteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title1","Description 1",1));
            noteDao.insert(new Note("Title2","Description 2",2));
            noteDao.insert(new Note("Title3","Description 3",1));
            return null;
        }
    }



}




