// How to Load Image From URL (Internet) in Android ImageView

package ie.stepout.swipr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.InputStream;

public class GalleryActivity extends AppCompatActivity {

    Integer pictnumber = 1;

    String url = "http://www.stepout.ie/"+pictnumber+".jpg";

//    Button button = (Button) findViewById(R.id.nxtImg);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        new DownloadImageFromInternet((ImageView) findViewById(R.id.image_view)).execute(url);
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;


        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }


        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
                pictnumber = 1;
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

    }



      public void nxtImg(View v){
          pictnumber = pictnumber + 1;
          url = "http://www.stepout.ie/"+pictnumber+".jpg";
          new DownloadImageFromInternet((ImageView) findViewById(R.id.image_view)).execute(url);
      }


        }





