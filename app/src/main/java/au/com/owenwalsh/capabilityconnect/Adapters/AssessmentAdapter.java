package au.com.owenwalsh.capabilityconnect.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import au.com.owenwalsh.capabilityconnect.Model.Assessment;
import au.com.owenwalsh.capabilityconnect.Model.Competency;
import au.com.owenwalsh.capabilityconnect.Model.Tutorial;
import au.com.owenwalsh.capabilityconnect.R;

/**
 * Created by owenw on 11/10/2016.
 */
public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder> {
    private Context mContext;
    private List<Assessment> assessmentsList;

    private ItemClickCallback itemClickCallback;


    //declaring interface for the on click event
    public interface ItemClickCallback {
        void onItemClick(int p);
        void onDeleteClick(int p);
        void onUpdateClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    /**
     * Constructor for Tutorial Adapter
     *
     * @param assessmentsList
     * @param mContext
     */
    public AssessmentAdapter(List<Assessment> assessmentsList, Context mContext) {
        this.assessmentsList = assessmentsList;
        this.mContext = mContext;
    }

    /**
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public AssessmentAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.assessment_row, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(AssessmentAdapter.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(assessmentsList.get(position).getName());
        viewHolder.dueDate.setText(assessmentsList.get(position).getDueDate());

    }

    public void updateListAdapter(List<Assessment> assessmentsList) {
        this.assessmentsList = assessmentsList;
    }

    @Override
    public int getItemCount() {
        return assessmentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView dueDate;
        public ImageButton removeButton;
        public ImageButton updateButton;
        public RelativeLayout compLayout;
        public View compContainer;

        public ViewHolder(View view) {
            super(view);
            compLayout = (RelativeLayout) view.findViewById(R.id.assessment_card);
            name = (TextView) view.findViewById(R.id.assessment_name);
            dueDate =(TextView) view.findViewById(R.id.assessment_due_date);
            removeButton = (ImageButton) view.findViewById(R.id.btn_remove_assessment);
            updateButton = (ImageButton) view.findViewById(R.id.btn_edit_assessment);
            compContainer = view.findViewById(R.id.assessment_cont_item_root);
            compContainer.setOnClickListener(this);
            updateButton.setOnClickListener(this);
            removeButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.comp_cont_item_root) {
                itemClickCallback.onItemClick(getAdapterPosition());
            } else if (view.getId() == R.id.btn_edit_assessment) {
                itemClickCallback.onUpdateClick(getAdapterPosition());
            } else if (view.getId() == R.id.btn_remove_assessment) {
                itemClickCallback.onDeleteClick(getAdapterPosition());
            }
        }
    }
}
