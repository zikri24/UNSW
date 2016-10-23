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

import au.com.owenwalsh.capabilityconnect.Model.Tutorial;
import au.com.owenwalsh.capabilityconnect.R;

/**
 * Created by owenw on 11/10/2016.
 */
public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.ViewHolder> {
    private Context mContext;
    private List<Tutorial> classList;

    private ItemClickCallback itemClickCallback;


    //declaring interface for the on click event
    public interface ItemClickCallback {
        void onItemClick(int p);

        void onDeleteClick(int p);

        void onAttendanceClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    /**
     * Constructor for Tutorial Adapter
     *
     * @param classList
     * @param mContext
     */
    public TutorialAdapter(List<Tutorial> classList, Context mContext) {
        this.classList = classList;
        this.mContext = mContext;
    }

    /**
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public TutorialAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.class_row, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(TutorialAdapter.ViewHolder viewHolder, int position) {
        viewHolder.tutorialDay.setText(classList.get(position).getDay());
        viewHolder.tutorialTime.setText(classList.get(position).getTime());
        viewHolder.numOfStudents.setText(String.valueOf(classList.get(position).getNumberOfStudents()));
    }

    public void updateListAdapter(List<Tutorial> classList) {
        this.classList = classList;
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tutorialDay;
        public TextView tutorialTime;
        public TextView numOfStudents;
        public ImageButton removeButton;
        public ImageButton attandanceButton;
        public RelativeLayout classLayout;
        public View tutContainer;

        public ViewHolder(View view) {
            super(view);
            classLayout = (RelativeLayout) view.findViewById(R.id.tutorial_card);
            tutorialDay = (TextView) view.findViewById(R.id.tutorial_day);
            tutorialTime = (TextView) view.findViewById(R.id.tutorial_time);
            numOfStudents = (TextView) view.findViewById(R.id.num_of_students);
            removeButton = (ImageButton) view.findViewById(R.id.btn_remove_tutorial);
            attandanceButton = (ImageButton) view.findViewById(R.id.btn_add_attendance);
            tutContainer = view.findViewById(R.id.tutorial_cont_item_root);
            tutContainer.setOnClickListener(this);
            attandanceButton.setOnClickListener(this);
            removeButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.tutorial_cont_item_root) {
                itemClickCallback.onItemClick(getAdapterPosition());
            } else if (view.getId() == R.id.btn_add_attendance) {
                itemClickCallback.onAttendanceClick(getAdapterPosition());
            } else if (view.getId() == R.id.btn_remove_tutorial) {
                itemClickCallback.onDeleteClick(getAdapterPosition());
            }
        }
    }
}
