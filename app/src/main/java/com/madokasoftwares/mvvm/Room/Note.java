package com.madokasoftwares.mvvm.Room;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Entity class
@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate =true)
    private int id;

    private String title;
    private String description;
    private int priority;


    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
       // @ColumnInfo(name="priority_column")
        this.priority = priority;
    }

    public void setId(int id) { //setter
        this.id = id;
    }

    public int getId() { //getters
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
