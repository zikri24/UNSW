package au.com.owenwalsh.capabilityconnect.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import au.com.owenwalsh.capabilityconnect.Model.Competency;
import au.com.owenwalsh.capabilityconnect.Model.StudentAssessment;
import au.com.owenwalsh.capabilityconnect.R;

/**
 * Created by owenw on 23/10/2016.
 */

public class StudentAssessmentAdapter extends RecyclerView.Adapter<StudentAssessmentAdapter.ViewHolder>{
    private Context mContext;
    private List<StudentAssessment> studentAssessmentList;

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

    public StudentAssessmentAdapter(List<StudentAssessment> studentAssessmentList, Context mContext) {
        this.studentAssessmentList = studentAssessmentList;
        this.mContext = mContext;
    }

    @Override
    public StudentAssessmentAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_assessment_row, viewGroup, false);
        return null;
    }

    @Override
    public void onBindViewHolder(StudentAssessmentAdapter.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(studentAssessmentList.get(position).getAssessmentName());
        viewHolder.assessmentMark.setText(String.valueOf(studentAssessmentList.get(position).getStudentMark()));

    }


    public void updateListAdapter(List<StudentAssessment> studentAssessmentList) {
        this.studentAssessmentList = studentAssessmentList;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView dueDate;
        public TextView assessmentMark;
        public RelativeLayout studentAssessmentLayout;
        public View studentAssessmentContainer;
        public ImageButton edit_button;
        public ImageButton add_button;

        public ViewHolder(View view) {
            super(view);
            studentAssessmentLayout = (RelativeLayout) view.findViewById(R.id.assessment_card);
            name = (TextView) view.findViewById(R.id.assessment_name);
            dueDate = (TextView) view.findViewById(R.id.assessment_due_date);
            studentAssessmentContainer = view.findViewById(R.id.student_assess_cont_item_root);
            studentAssessmentContainer.setOnClickListener(this);
            assessmentMark = (TextView) view.findViewById(R.id.student_assessment_mark);
            add_button = (ImageButton) view.findViewById(R.id.add_button);
            edit_button = (ImageButton) view.findViewById(R.id.edit_button);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.comp_cont_item_root) {
                itemClickCallback.onItemClick(getAdapterPosition());
            } else if (view.getId() == R.id.edit_button) {
                itemClickCallback.onEditClick(getAdapterPosition());
            } else if (view.getId() == R.id.add_button) {
                itemClickCallback.onAddClick(getAdapterPosition());
            }
        }
    }
}

