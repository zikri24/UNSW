package au.com.owenwalsh.capabilityconnect.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import au.com.owenwalsh.capabilityconnect.Model.Competency;
import au.com.owenwalsh.capabilityconnect.Model.StudentCompetency;
import au.com.owenwalsh.capabilityconnect.R;

/**
 * Created by owenw on 23/10/2016.
 */

public class StudentCompetencyAdapter extends RecyclerView.Adapter<StudentCompetencyAdapter.ViewHolder> {
    private Context mContext;
    private List<StudentCompetency> studentCompetenciesList;


    private ItemClickCallback itemClickCallback;


    //declaring interface for the on click event
    public interface ItemClickCallback {
        void onItemClick(int p);

        void onEditClick(int p);

        void onAddClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    /**
     * Constructor for Tutorial Adapter
     *
     * @param studentCompetenciesList
     * @param mContext
     */
    public StudentCompetencyAdapter(List<StudentCompetency> studentCompetenciesList, Context mContext) {
        this.studentCompetenciesList = studentCompetenciesList;
        this.mContext = mContext;
    }

    @Override
    public StudentCompetencyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_competency_row, viewGroup, false);
        return null;
    }

    @Override
    public void onBindViewHolder(StudentCompetencyAdapter.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(studentCompetenciesList.get(position).getCompName());
        viewHolder.competencyMark.setText(String.valueOf(studentCompetenciesList.get(position).getRating()));
    }


    public void updateListAdapter(List<StudentCompetency> studentCompetenciesList) {
        this.studentCompetenciesList = studentCompetenciesList;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView competencyMark;
        public RelativeLayout studentCompLayout;
        public View studentCompContainer;
        public ImageButton edit_button;
        public ImageButton add_button;

        public ViewHolder(View view) {
            super(view);
            studentCompLayout = (RelativeLayout) view.findViewById(R.id.student_competency_card);
            name = (TextView) view.findViewById(R.id.competency_name);
            competencyMark = (TextView) view.findViewById(R.id.id_mark);
            studentCompContainer = view.findViewById(R.id.student_competency_cont_item_root);
            studentCompContainer.setOnClickListener(this);
            add_button = (ImageButton) view.findViewById(R.id.add_assessment_mark);
            edit_button = (ImageButton) view.findViewById(R.id.edit_competency_mark_button);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.student_competency_cont_item_root) {
                itemClickCallback.onItemClick(getAdapterPosition());
            } else if (view.getId() == R.id.edit_competency_mark_button) {
                itemClickCallback.onEditClick(getAdapterPosition());
            } else if (view.getId() == R.id.add_assessment_mark) {
                itemClickCallback.onAddClick(getAdapterPosition());
            }
        }
    }
}

