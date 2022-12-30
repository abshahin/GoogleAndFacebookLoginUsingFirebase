package com.google.firebase.quickstart.auth;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    // Initialize variable
    ImageView ivImage;
    TextView tvName, tvId, tvEmail;
    Button btLogout;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Assign variable
        ivImage=findViewById(R.id.iv_image);
        tvName=findViewById(R.id.tv_name);
        tvId=findViewById(R.id.tv_id);
        tvEmail=findViewById(R.id.tv_email);
        btLogout=findViewById(R.id.bt_logout);

        // Initialize firebase auth
        firebaseAuth=FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        // Check condition
        if(firebaseUser!=null)
        {
            // When firebase user is not equal to null
            // Set image on image view
            Glide.with(ProfileActivity.this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(ivImage);
            // set name on text view
            tvName.setText(firebaseUser.getDisplayName());
            tvId.setText(firebaseUser.getUid());
            tvEmail.setText(firebaseUser.getEmail());
        }

        // Initialize sign in client
        googleSignInClient= GoogleSignIn.getClient(ProfileActivity.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out from google
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Check condition
                        if(task.isSuccessful())
                        {
                            // When task is successful
                            // Sign out from firebase
                            firebaseAuth.signOut();

                            // Display Toast
                            Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();

                            // Finish activity
                            finish();
                        }
                    }
                });
            }
        });
    }
}
