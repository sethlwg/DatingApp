package com.example.blurdatingapplication.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.blurdatingapplication.MainActivity;
import com.example.blurdatingapplication.R;
import com.example.blurdatingapplication.data.Interest;
import com.example.blurdatingapplication.data.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetUpProfile extends AppCompatActivity {

    Button btnNext;

    Spinner spinnerJob, spinnerBloodType, spinnerChild, spinnerDrinking,
            spinnerSmoking, spinnerWorkOut, spinnerDayOff;
    String job, bloodtype, child, drinking, smoking, workout, dayoff;

    FirebaseAuth auth;
    FirebaseUser user;

    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_profile);

        btnNext = findViewById(R.id.btn_next);
        spinnerJob = findViewById(R.id.spinner_job);
        spinnerBloodType = findViewById(R.id.spinner_bloodtype);
        spinnerChild = findViewById(R.id.spinner_child);
        spinnerDrinking = findViewById(R.id.spinner_drinking);
        spinnerSmoking = findViewById(R.id.spinner_smoking);
        spinnerWorkOut = findViewById(R.id.spinner_workout);
        spinnerDayOff = findViewById(R.id.spinner_dayoff);

        //spinnerFaceType = findViewById(R.id.spinner_other);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job = spinnerJob.getSelectedItem().toString();
                bloodtype = spinnerBloodType.getSelectedItem().toString();
                child = spinnerChild.getSelectedItem().toString();
                drinking = spinnerDrinking.getSelectedItem().toString();
                smoking = spinnerSmoking.getSelectedItem().toString();
                workout = spinnerWorkOut.getSelectedItem().toString();
                dayoff = spinnerDayOff.getSelectedItem().toString();

                setToDataBase();
            }
        });
    }

    void setToDataBase() {
        if (profile != null) {
            profile.setJob(job);
            profile.setBloodType(bloodtype);
            profile.setChild(child);
            profile.setDrinking(drinking);
            profile.setSmoking(smoking);
            profile.setWork_out(workout);
            profile.setDay_off(dayoff);

        }
        else {
            profile = new Profile(job, bloodtype, child, drinking, smoking, workout, dayoff);
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = user.getUid();

        db.collection("profile")
                .document(userId)
                .set(profile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}

