package com.madokasoftwares.mvvm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.madokasoftwares.mvvm.R;
import com.madokasoftwares.mvvm.Room.Note;

import java.util.ArrayList;
import java.util.List;

//to get data from the noteobject to the recyclerviewer
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder>{
    private List<Note> notes = new ArrayList<>();//<Note> is from our room package
    private OnItemClickListener listener;
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.note_item,parent,false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
       Note currentNote = notes.get(position);
       holder.textViewTitle.setText(currentNote.getTitle());
       holder.textViewDescription.setText(currentNote.getDescription());
       holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes){ //getting our lists of notes //will be used in main
       this.notes = notes;
       notifyDataSetChanged();
    }

    public Note getNoteAt(int position){
        return notes.get(position);//we want to get the note from the adapter to the outside
        //to use it in method ItemTouchHelper in mainActivty
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle= itemView.findViewById(R.id.text_view_title);
            textViewDescription= itemView.findViewById(R.id.text_view_description);
            textViewPriority= itemView.findViewById(R.id.text_view_priority);

         itemView.setOnClickListener(new View.OnClickListener() {// //item clicked on recycle view to edit our data
           @Override
           public void onClick(View view) {
               int position = getAdapterPosition();
               if(listener != null && position != RecyclerView.SCREEN_STATE_ON){

                   listener.OnItemClick(notes.get(position));//notes is our arraylist above
               }
           }
       });
        }
    }

    public interface OnItemClickListener{ //we want to provide a listener wen an item in recycler viewer is clik=cked
        void OnItemClick(Note note);
    }

    public void SetOnItemClickListener(OnItemClickListener listener){// //item clicked on recycle view to edit our data
  this.listener = listener;
    }
}
