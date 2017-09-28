package campus.sunder.com.a360degreeinfodynamics.printdemo;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.print.pdf.PrintedPdfDocument;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;


public class MainActivity extends FragmentActivity {
    private ImageView image;
    Button btnSaveExternalStorageDirectory;
    private Bitmap bitmap;
    private ProgressBar bar;
    private Context context;
    Runnable r;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);


        //Used for fetch image from gallery
        /*  Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 11);*/
















        btnSaveExternalStorageDirectory = (Button) findViewById(R.id.saveExternalStorageDirectory);


        //Permission for 23+ api
        if (shouldAskPermissions()) {
            askPermissions();
        }


        //Use for store image into internal store
        //always save as
           /*  String fileName = "test.jpg";
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
                File ExternalStorageDirectory = Environment.getExternalStorageDirectory();
                File file = new File(ExternalStorageDirectory + File.separator + fileName);
                FileOutputStream fileOutputStream = null;
                try {
                    file.createNewFile();
                    fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(bytes.toByteArray());
                    Toast.makeText(MainActivity.this,
                            file.getAbsolutePath(),
                            Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }*/



//



        //save image into gallery
       /*btnSaveExternalStorageDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();


               //Use for store image into media
                ContentResolver cr = getContentResolver();
                String title = "myBitmap";
                String description = "My bitmap created by Sunder sharma";
                String savedURL = MediaStore.Images.Media
                        .insertImage(cr, bitmap, title, description);

                Toast.makeText(MainActivity.this,
                        savedURL,
                        Toast.LENGTH_LONG).show();
                            }

        });*/


//share pdf  file
        btnSaveExternalStorageDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*   File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ "test.pdf");
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                image.setImageBitmap(bitmap);*/
           /*   Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(Uri.fromFile(file),"application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent intent = Intent.createChooser(target, "Open File");
                startActivity(intent);*/
            }
        });





//Used thread in android
        RunnableDemo R1 = new RunnableDemo("Thread-1");
        R1.start();


        //print image using bitmap

  /*     PrintHelper photoPrinter = new PrintHelper(this);
                photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), bitmap1.getGenerationId());
                photoPrinter.printBitmap("droids.jpg - test print", bitmap);
*/


    }

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 11) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                image = (ImageView) findViewById(R.id.image);
                Bitmap ProfilePic = extras.getParcelable("data");
                image.setImageBitmap(ProfilePic);
                PrintHelper photoPrinter = new PrintHelper(MainActivity.this);
                photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                if (ProfilePic != null) {
                    // Send it to the print helper
                    photoPrinter.printBitmap("PrintShop", ProfilePic);
                }

            }
        }


    }



    class RunnableDemo implements Runnable {
        private Thread t;
        private String threadName;

        RunnableDemo(String name) {
            threadName = name;
            System.out.println("Creating " + threadName);
        }

        public void run() {
            System.out.println("Running " + threadName);


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    QRCode();
                }
            });
        }

        public void start() {
            System.out.println("Starting " + threadName);
            if (t == null) {
                t = new Thread(this, threadName);
                t.start();
            }
        }
    }

    private void QRCode() {


        }





}



