package au.com.owenwalsh.capabilityconnect.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import au.com.owenwalsh.capabilityconnect.R;

public class EditTutorialActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_edit_tutorial, null, false);
        drawerLayout.addView(contentView, 0);
    }
}
