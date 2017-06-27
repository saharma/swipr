package ie.stepout.swipr;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

TextView mViewOptionText;
Button mLogOut;
Button mViewGallery, mViewMap;
Button mSubmitImg;

    private FirebaseAuth auth;

    //get a connection
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("option");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            finish();
//        }
        setContentView(R.layout.activity_main);


        mViewGallery = (Button) findViewById(R.id.viewGallery);
        mViewMap = (Button) findViewById(R.id.viewMap);
        mLogOut = (Button) findViewById(R.id.logout);
        mSubmitImg = (Button) findViewById(R.id.submitImage);


    }

    @Override
    protected void onStart(){
        super.onStart();

        mConditionRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
               // mViewOptionText.setText(text);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mLogOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bodyandsoul.ie")));
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                signOut();
                }
            });

        mViewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        });

        mViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });


        mSubmitImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });

    }

    public void signOut() {
        auth.signOut();
    }

}
