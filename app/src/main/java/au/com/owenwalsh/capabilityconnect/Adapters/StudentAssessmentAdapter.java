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
        return new StudentAssessmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentAssessmentAdapter.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(studentAssessmentList.get(position).getAssessmentName());
        viewHolder.assessmentMark.setText(String.valueOf(studentAssessmentList.get(position).getAssessmentMark()));
        viewHolder.studentMark.setText(String.valueOf(studentAssessmentList.get(position).getStudentMark()));
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
        public TextView studentMark;
        public TextView assessmentMark;
        public RelativeLayout studentAssessmentLayout;
        public View studentAssessmentContainer;
        public ImageButton edit_mark_button;
        public ImageButton add_mark_button;

        public ViewHolder(View view) {
            super(view);
            studentAssessmentLayout = (RelativeLayout) view.findViewById(R.id.student_assessment_card);
            name = (TextView) view.findViewById(R.id.assessment_name);
            studentMark = (TextView) view.findViewById(R.id.assessment_student_mark);
            assessmentMark = (TextView) view.findViewById(R.id.assessment_weighting);
            studentAssessmentContainer = view.findViewById(R.id.student_assess_cont_item_root);
            studentAssessmentContainer.setOnClickListener(this);
            add_mark_button = (ImageButton) view.findViewById(R.id.add_assessment_mark);
            edit_mark_button = (ImageButton) view.findViewById(R.id.edit_assessment_mark_button);
            add_mark_button.setOnClickListener(this);
            edit_mark_button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.add_assessment_mark) {
                itemClickCallback.onAddClick(getAdapterPosition());
            } else if (view.getId() == R.id.edit_assessment_mark_button) {
                itemClickCallback.onEditClick(getAdapterPosition());
            }
        }
    }
}

