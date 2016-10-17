package au.com.owenwalsh.capabilityconnect.Adapters;

import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.util.List;

import au.com.owenwalsh.capabilityconnect.Model.Student;
import au.com.owenwalsh.capabilityconnect.Model.Tutorial;
import au.com.owenwalsh.capabilityconnect.R;

/**
 * Created by owenw on 11/10/2016.
 */


public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>  {
    private Context mContext;
    private List<Student> studentList;

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
     * @param studentList
     * @param mContext
     */


    public StudentAdapter (List<Student> studentList, Context mContext){
        this.studentList = studentList;
        this.mContext = mContext;
    }


    /**
     *
     * @param viewGroup
     * @param viewType
     * @return
     */

    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_row, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     *
     * @param viewHolder
     * @param position
     */

    @Override
    public void onBindViewHolder(StudentAdapter.ViewHolder viewHolder, int position) {
        viewHolder.studentID.setText(studentList.get(position).getId());
        viewHolder.studentFirstName.setText(studentList.get(position).getFirsName());
        viewHolder.studentLastName.setText(studentList.get(position).getLastName());
        /*viewHolder.studentLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.d("Student Clicked: ","a student has been clicked");
                //code for whatever happens when  class is clicked
            }
        });
        viewHolder.editStudentButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.d("Edit Clicked: ","edit student button has been clicked");
                //code for whatever happens when  class is clicked
            }
        });
        viewHolder.removeStudentButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.d("Remove Clicked: ","remove student button has been clicked");
                //code for whatever happens when  class is clicked
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void updateListAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public TextView studentID;
        public TextView studentFirstName;
        public TextView studentLastName;
        public ImageButton removeStudentButton;
        public ImageButton editStudentButton;
        public RelativeLayout studentLayout;
        public View container;

        public ViewHolder(View view) {
            super(view);
            studentLayout = (RelativeLayout) view.findViewById(R.id.student_card);
            studentID = (TextView) view.findViewById(R.id.student_id);
            studentFirstName = (TextView) view.findViewById(R.id.student_first_name);
            studentLastName = (TextView) view.findViewById(R.id.student_last_name);
            removeStudentButton = (ImageButton) view.findViewById(R.id.remove_button);
            editStudentButton = (ImageButton) view.findViewById(R.id.edit_button);
            container = view.findViewById(R.id.cont_item_root);
            container.setOnClickListener(this);
            editStudentButton.setOnClickListener(this);
            removeStudentButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.cont_item_root) {
                itemClickCallback.onItemClick(getAdapterPosition());
            }else if(view.getId() == R.id.edit_button) {
                itemClickCallback.onUpdateClick(getAdapterPosition());
            }else if(view.getId() == R.id.remove_button) {
                itemClickCallback.onDeleteClick(getAdapterPosition());
            }
        }
    }
}
