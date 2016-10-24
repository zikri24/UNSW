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

import au.com.owenwalsh.capabilityconnect.Model.Competency;
import au.com.owenwalsh.capabilityconnect.Model.Tutorial;
import au.com.owenwalsh.capabilityconnect.R;

/**
 * Created by owenw on 11/10/2016.
 */
public class CompetencyAdapter extends RecyclerView.Adapter<CompetencyAdapter.ViewHolder> {
    private Context mContext;
    private List<Competency> competencyList;

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
     * @param competencyList
     * @param mContext
     */
    public CompetencyAdapter(List<Competency> competencyList, Context mContext) {
        this.competencyList = competencyList;
        this.mContext = mContext;
    }

    /**
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public CompetencyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.competency_row, viewGroup, false);
        return new  CompetencyAdapter.ViewHolder(view);
    }

    /**
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(CompetencyAdapter.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(competencyList.get(position).getName());
        //viewHolder.desctiption.setText(competencyList.get(position).getDescription());

    }

    public void updateListAdapter(List<Competency> competencyList) {
        this.competencyList = competencyList;
    }

    @Override
    public int getItemCount() {
        return competencyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        //public TextView description;
        public ImageButton removeButton;
        public ImageButton updateButton;
        public RelativeLayout compLayout;
        public View compContainer;

        public ViewHolder(View view) {
            super(view);
            compLayout = (RelativeLayout) view.findViewById(R.id.competency_card);
            name = (TextView) view.findViewById(R.id.competency_name);
            //description = (TextView) view.findViewById(R.id.com);

            removeButton = (ImageButton) view.findViewById(R.id.remove_comp_button);
            updateButton = (ImageButton) view.findViewById(R.id.edit_comp_button);
            compContainer = view.findViewById(R.id.comp_cont_item_root);
            compContainer.setOnClickListener(this);
            updateButton.setOnClickListener(this);
            removeButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.comp_cont_item_root) {
                itemClickCallback.onItemClick(getAdapterPosition());
            } else if (view.getId() == R.id.edit_comp_button) {
                itemClickCallback.onUpdateClick(getAdapterPosition());
            } else if (view.getId() == R.id.remove_comp_button) {
                itemClickCallback.onDeleteClick(getAdapterPosition());
            }
        }
    }
}
