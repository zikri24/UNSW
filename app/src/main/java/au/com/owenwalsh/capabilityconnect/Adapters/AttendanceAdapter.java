package au.com.owenwalsh.capabilityconnect.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import au.com.owenwalsh.capabilityconnect.Model.Student;
import au.com.owenwalsh.capabilityconnect.R;

/**
 * Created by owenw on 23/10/2016.
 */

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private Context mContext;
    private List<Student> studentList;

    private AttendanceAdapter.ItemClickCallback itemClickCallback;


    //declaring interface for the on click event
    public interface ItemClickCallback {
        void onItemClick(int p);
        /*void onDeleteClick(int p);
        void onUpdateClick(int p);*/
    }

    public void setItemClickCallback(final AttendanceAdapter.ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }


    /**
     * Constructor for Tutorial Adapter
     *
     * @param studentList
     * @param mContext
     */


    public AttendanceAdapter(List<Student> studentList, Context mContext) {
        this.studentList = studentList;
        this.mContext = mContext;
    }


    /**
     * @param viewGroup
     * @param viewType
     * @return
     */

    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attendance_row, viewGroup, false);
        return new AttendanceAdapter.ViewHolder(view);
    }

    /**
     * @param viewHolder
     * @param position
     */

    @Override
    public void onBindViewHolder(AttendanceAdapter.ViewHolder viewHolder, int position) {
        viewHolder.studentID.setText(studentList.get(position).getId());
        viewHolder.studentFirstName.setText(studentList.get(position).getFirsName());
        viewHolder.studentLastName.setText(studentList.get(position).getLastName());

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void updateListAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView studentID;
        public TextView studentFirstName;
        public TextView studentLastName;
        public CheckBox attend;
        public RelativeLayout studentLayout;


        public ViewHolder(View view) {
            super(view);
            studentLayout = (RelativeLayout) view.findViewById(R.id.student_card);
            studentID = (TextView) view.findViewById(R.id.student_id);
            studentFirstName = (TextView) view.findViewById(R.id.student_first_name);
            studentLastName = (TextView) view.findViewById(R.id.student_last_name);
            attend = (CheckBox) view.findViewById(R.id.checkbox_attendance);
            attend.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.checkbox_attendance) {
                itemClickCallback.onItemClick(getAdapterPosition());
            }/*else if(view.getId() == R.id.edit_button) {
                itemClickCallback.onUpdateClick(getAdapterPosition());
            }else if(view.getId() == R.id.remove_button) {
                itemClickCallback.onDeleteClick(getAdapterPosition());
            }*/
        }
    }
}
