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
import au.com.owenwalsh.capabilityconnect.Model.Week;
import au.com.owenwalsh.capabilityconnect.R;

/**
 * Created by owenw on 11/10/2016.
 */
public class WeeksAdapter extends RecyclerView.Adapter<WeeksAdapter.ViewHolder> {
    private Context mContext;
    private List<Week> weeksList;

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
     * @param weeksList
     * @param mContext
     */
    public WeeksAdapter(List<Week> weeksList, Context mContext) {
        this.weeksList = weeksList;
        this.mContext = mContext;
    }

    /**
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public WeeksAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weeks_row, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(WeeksAdapter.ViewHolder viewHolder, int position) {
        viewHolder.weekName.setText(weeksList.get(position).getName());
    }

    public void updateListAdapter(List<Week> weeksList) {
        this.weeksList = weeksList;
    }

    @Override
    public int getItemCount() {
        return weeksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView weekName;
        public TextView todo;
        public ImageButton removeButton;
        public ImageButton updateButton;
        public RelativeLayout weekLayout;
        public View weekContainer;

        public ViewHolder(View view) {
            super(view);
            weekLayout = (RelativeLayout) view.findViewById(R.id.weeks_card);
            weekName = (TextView) view.findViewById(R.id.week_name);
            todo = (TextView) view.findViewById(R.id.week_todo);


            removeButton = (ImageButton) view.findViewById(R.id.remove_week_button);
            updateButton = (ImageButton) view.findViewById(R.id.edit_week_button);
            weekContainer = view.findViewById(R.id.week_cont_item_root);
            weekContainer.setOnClickListener(this);
            updateButton.setOnClickListener(this);
            removeButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.week_cont_item_root) {
                itemClickCallback.onItemClick(getAdapterPosition());
            } else if (view.getId() == R.id.edit_week_button) {
                itemClickCallback.onUpdateClick(getAdapterPosition());
            } else if (view.getId() == R.id.remove_week_button) {
                itemClickCallback.onDeleteClick(getAdapterPosition());
            }
        }
    }
}
