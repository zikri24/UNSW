package au.com.owenwalsh.capabilityconnect.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import au.com.owenwalsh.capabilityconnect.Model.Competency;
import au.com.owenwalsh.capabilityconnect.R;

/**
 * Created by owenw on 23/10/2016.
 */

public class StudentCompetencyAdapter  extends RecyclerView.Adapter<CompetencyAdapter.ViewHolder> {
    private Context mContext;
    private List<Competency> competencyList;

    /**
     * Constructor for Tutorial Adapter
     *
     * @param competencyList
     * @param mContext
     */
    public StudentCompetencyAdapter(List<Competency> competencyList, Context mContext) {
        this.competencyList = competencyList;
        this.mContext = mContext;
    }

    @Override
    public CompetencyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_competency_row, viewGroup, false);
        return null;
    }

    @Override
    public void onBindViewHolder(CompetencyAdapter.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(competencyList.get(position).getName());

    }


    public void updateListAdapter(List<Competency> competencyList) {
        this.competencyList = competencyList;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        //public TextView description;
        public SeekBar competencyBar;
        public RelativeLayout compLayout;
        public View compContainer;

        public ViewHolder(View view) {
            super(view);
            compLayout = (RelativeLayout) view.findViewById(R.id.competency_card);
            name = (TextView) view.findViewById(R.id.competency_name);
            compContainer = view.findViewById(R.id.comp_cont_item_root);
            compContainer.setOnClickListener(this);
            competencyBar = (SeekBar) view.findViewById(R.id.competency_slider);
        }

        @Override
        public void onClick(View view) {


        }
    }
}

