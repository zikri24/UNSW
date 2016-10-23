package au.com.owenwalsh.capabilityconnect.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import au.com.owenwalsh.capabilityconnect.Model.Competency;
import au.com.owenwalsh.capabilityconnect.R;

/**
 * Created by owenw on 23/10/2016.
 */

public class StudentAssessmentAdapter {
    private Context mContext;
    private List<Competency> assessmentList;

    public StudentAssessmentAdapter(List<Competency> competencyList, Context mContext) {
        this.assessmentList = competencyList;
        this.mContext = mContext;
    }

    @Override
    public StudentAssessmentAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_assessment_row, viewGroup, false);
        return null;
    }

    @Override
    public void onBindViewHolder(StudentAssessmentAdapter.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(assessmentList.get(position).getName());

    }


    public void updateListAdapter(List<Competency> competencyList) {
        this.assessmentList = assessmentList;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView date;
        public SeekBar competencyBar;
        public RelativeLayout compLayout;
        public View compContainer;

        public ViewHolder(View view) {
            super(view);
            compLayout = (RelativeLayout) view.findViewById(R.id.competency_card);
            name = (TextView) view.findViewById(R.id.assessment_name);
            date = (TextView) view.findViewById(R.id.assessment_due_date);
            compContainer = view.findViewById(R.id.comp_cont_item_root);
            compContainer.setOnClickListener(this);
            competencyBar = (SeekBar) view.findViewById(R.id.assessment_slider);
        }

        @Override
        public void onClick(View view) {


        }
    }
}

