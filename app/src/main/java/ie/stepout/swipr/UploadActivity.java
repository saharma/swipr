package ie.stepout.swipr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

//reference http://www.thecrazyprogrammer.com/2017/02/android-upload-image-firebase-storage-tutorial.html and the firebase documentation
public class UploadActivity extends AppCompatActivity {

    //declare variables
    private ImageView imgView;
    private TextView overlayText;
    private Button uploadImg, chooseImg;
    private Integer pictNo = 1;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    ProgressDialog pd;
    private FirebaseStorage storage = FirebaseStorage.getInstance();


    StorageReference storageRef = storage.getReferenceFromUrl("gs://swipr-749e6.appspot.com");    //change the url according to your firebase app


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        chooseImg = (Button)findViewById(R.id.chooseImg);
        uploadImg = (Button)findViewById(R.id.uploadImg);
        imgView = (ImageView)findViewById(R.id.imgView);
        overlayText = (TextView) findViewById(R.id.overlay_text);
        overlayText.setText("");
        overlayText.setVisibility(View.INVISIBLE);
        EditText textInput = (EditText) findViewById(R.id.text_input);
        textInput.addTextChangedListener(new InputTextWatcher());

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");


        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filePath != null) {
                    pd.show();

                    //UUID.randomUUID()
                    final StorageReference childRef = storageRef.child("image/" + pictNo + ".jpg");

                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(UploadActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();

                            StorageMetadata metadata = new StorageMetadata.Builder()
                                    .setCustomMetadata("text", overlayText.getText().toString())
                                    .build();

                            childRef.updateMetadata(metadata);
                            pictNo = pictNo + 1;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(UploadActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(UploadActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
                }






            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                imgView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private class InputTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int count, int after){

        }

        @Override
        public void afterTextChanged(Editable s){
            overlayText.setVisibility(s.length()>0 ? View.VISIBLE : View.INVISIBLE);
            overlayText.setText(s.toString());
        }
    }






}