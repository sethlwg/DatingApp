package com.example.blurdatingapplication.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.blurdatingapplication.R;
import com.example.blurdatingapplication.data.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetUpUserInfo2 extends AppCompatActivity {
    Button buttonNext;

    TextView editTextLocation, editTextPhoneNumber,editTextViewBirthday;
    Spinner spinnerGender, spinnerPreferredGender;
    String username, phoneNumber, birthday, gender, preferredGender, email;

    int location;
    FirebaseAuth auth;
    FirebaseUser user;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_user_info2);

        buttonNext = findViewById(R.id.btn_next);

        editTextLocation = findViewById(R.id.location);
        editTextPhoneNumber = findViewById(R.id.phone_number);
        editTextViewBirthday = findViewById(R.id.birthday);
        spinnerGender = findViewById(R.id.spinner_gender);
        spinnerPreferredGender = findViewById(R.id.spinner_preferred_gender);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        email = user.getEmail();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location = Integer.parseInt(editTextLocation.getText().toString());
                phoneNumber = editTextPhoneNumber.getText().toString();
                birthday = editTextViewBirthday.getText().toString();
                gender = spinnerGender.getSelectedItem().toString();
                preferredGender = spinnerPreferredGender.getSelectedItem().toString();
                set();
            }
        });
    }

    void set() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = user.getUid();

        if (userData != null) {
            userData.setEmail(email);
            userData.setUsername(username);
            userData.setPhoneNumber(phoneNumber);
            userData.setLocation(location);
            userData.setGender(gender);
            userData.setPreferredGender(preferredGender);
            userData.setCreatedTimestamp(Timestamp.now());
        }
        else {
            userData = new UserData(email, userId, username, phoneNumber, Timestamp.now(), location, gender, preferredGender, "-1","-1","-1", 0);
        }

        db.collection("users")
                .document(userId)
                .set(userData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), SetUpUserInfo3.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}