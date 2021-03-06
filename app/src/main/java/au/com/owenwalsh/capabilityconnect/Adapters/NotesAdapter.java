package au.com.owenwalsh.capabilityconnect.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import au.com.owenwalsh.capabilityconnect.Model.Note;
import au.com.owenwalsh.capabilityconnect.Model.Student;
import au.com.owenwalsh.capabilityconnect.R;

/**
 * Created by owenw on 24/10/2016.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>  {
    private Context mContext;
    private List<Note> notes;

    private NotesAdapter.ItemClickCallback itemClickCallback;


    //declaring interface for the on click event
    public interface ItemClickCallback {
        //void onItemClick(int p);
        void onDeleteClick(int p);
       // void onUpdateClick(int p);
    }

    public void setItemClickCallback(final NotesAdapter.ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    /**
     * Constructor for Tutorial Adapter
     *
     * @param notesList
     * @param mContext
     */


    public NotesAdapter (List<Note> notesList, Context mContext){
        this.notes = notesList;
        this.mContext = mContext;
    }


    /**
     *
     * @param viewGroup
     * @param viewType
     * @return
     */

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notes_row, viewGroup, false);
        return new NotesAdapter.ViewHolder(view);
    }

    /**
     *
     * @param viewHolder
     * @param position
     */

    @Override
    public void onBindViewHolder(NotesAdapter.ViewHolder viewHolder, int position) {
        viewHolder.notesContent.setText(notes.get(position).getContent());
        viewHolder.notesContent.setText(notes.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void updateListAdapter(List<Note> notesList) {
        this.notes = notesList;
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder  implements View.OnClickListener {
        public TextView notesContent;
        public ImageButton deleteNoteButton;
        public LinearLayout notesLayout;
       /// public View container;

        public ViewHolder(View view) {
            super(view);
            notesLayout = (LinearLayout) view.findViewById(R.id.note_card);
            notesContent = (TextView) view.findViewById(R.id.note_content);
            deleteNoteButton = (ImageButton) view.findViewById(R.id.delete_note_button);

            deleteNoteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.delete_note_button) {
                itemClickCallback.onDeleteClick(getAdapterPosition());
            }/*else if(view.getId() == R.id.edit_button) {
                itemClickCallback.onUpdateClick(getAdapterPosition());
            }else if(view.getId() == R.id.remove_button) {
                itemClickCallback.onDeleteClick(getAdapterPosition());
            }*/
        }
    }
}
