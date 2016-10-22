package au.com.owenwalsh.capabilityconnect.View;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import au.com.owenwalsh.capabilityconnect.Database.AssessmentLogic;
import au.com.owenwalsh.capabilityconnect.R;

public class AssessmentViewDetailsActivity extends BaseActivity {
    private Boolean isFabOpen = false;
    private FloatingActionButton addActionBar;
    private FloatingActionButton editAssessmentActionBar;
    private FloatingActionButton removeAssessmentActionBar;
    private Animation actionbar_open, actionbar_close, rotate_forward, rotate_backward;
    private AssessmentLogic assessmentLogic;
    private static final String ASSESS_ID = "assessmentID";
    private TextView assessment_name;
    private TextView assessment_weighting;
    private TextView assessment_date;
    private ImageView image_weighting;
    private ImageView image_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_assessment_view_details, null, false);
        drawerLayout.addView(contentView, 0);
    }
}
