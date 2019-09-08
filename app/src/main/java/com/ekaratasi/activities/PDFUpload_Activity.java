package com.ekaratasi.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ekaratasi.FilePath;
import com.ekaratasi.MainActivity;
import com.ekaratasi.R;
import com.ekaratasi.SingleUploadBroadcastReceiver;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class PDFUpload_Activity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener, SingleUploadBroadcastReceiver.Delegate {
    private Uri filePath;
    private Bitmap bitmap;
    private String pdfFileName;
    private PDFView pdfView;
    private String pdfPath;
    private int pageNumber = 0;
    CardView pick;
    TextView flname,loadingtext,percentage;
    ImageView retry,swipeimage;
    Button UploadButton;
    View loading;
    // Server URL.
    public static final String PDF_UPLOAD_HTTP_URL = "https://ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/server_upload_pdf.php";

    // Pdf upload request code.
    public int PDF_REQ_CODE = 1;

    // Define strings to hold given pdf name, path and ID.
    String PdfNameHolder, PdfPathHolder, PdfID;

    private static final String TAG = "AndroidUploadService";

    private final SingleUploadBroadcastReceiver uploadReceiver =
            new SingleUploadBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfupload);

        //make notification statusbar dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);



        // Method to enable runtime permission.
        RequestRunTimePermission();

       pick =  findViewById(R.id.cardView);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        UploadButton = (Button) findViewById(R.id.btnUpload);
        loading=findViewById(R.id.loadingdots);
        loadingtext=findViewById(R.id.loadingtext);

        percentage=findViewById(R.id.percentage);
        retry=findViewById(R.id.retry);

        // Adding click listener to Button.
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // PDF selection code start from here .
                // Creating intent object.
                Intent intent = new Intent();

                // Setting up default file pickup time as PDF.
                intent.setType("application/pdf");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_REQ_CODE);


            }
        });

        // Adding click listener to Upload PDF button.
        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//show loading dots text
                loading.setVisibility(View.VISIBLE);
                loadingtext.setVisibility(View.VISIBLE);

                //hide cardView
                pick.setVisibility(View.GONE);

                //show progress
                percentage.setVisibility(View.VISIBLE);

                retry.setVisibility(View.VISIBLE);

                // Calling method to upload PDF on server.
                PdfUploadFunction();

            }
        });

        // Refresh page
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(PDFUpload_Activity.this,PDFUpload_Activity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                    finish();


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        uploadReceiver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        uploadReceiver.unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PDF_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            /*HAPA*/
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

               // flname.setText("Path: ". concat(FilePath.getPath(this,filePath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String path= FilePath.getPath(this,filePath);
            File file = new File(path);
            displayFromFile(file);
        }
    }






    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    @Override
    public void loadComplete(int nbPages) {
//        PdfDocument.Meta meta = pdfView.getDocumentMeta();
//
//        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));

    }

    @Override
    public void onPageError(int page, Throwable t) {

    }
    private void displayFromFile(File file) {

        Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
        pdfFileName = getFileName(uri);

        pdfView.fromFile(file)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .load();
    }


    // PDF upload function starts from here.
    public void PdfUploadFunction() {
        // Getting pdf name from EditText.
        PdfNameHolder = "PDF Document";//PdfNameEditText.getText().toString().trim();

        // Getting file path using Filepath class.
        PdfPathHolder = FilePath.getPath(this, filePath);

        // If file path object is null then showing toast message to move file into internal storage.
        if (PdfPathHolder == null) {

            Toast.makeText(this, "Please move your PDF file to internal storage & try again.", Toast.LENGTH_LONG).show();

        }
        // If file path is not null then PDF uploading file process will starts.
        else {

            try {

                // progressDialog.dismiss();

                PdfID = "DOC-"+UUID.randomUUID().toString();
                uploadReceiver.setDelegate(this);
                uploadReceiver.setUploadID(PdfID);
                new MultipartUploadRequest(this, PdfID, PDF_UPLOAD_HTTP_URL)
                        .addFileToUpload(PdfPathHolder, "pdf")
                        .addParameter("name", PdfID)
                        //.setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload();



            } catch (Exception exception) {

                Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public void onProgress(int progress) {

        String prog= Integer.toString(progress);

        percentage.setText(prog+"%");

    }

    @Override
    public void onProgress(long uploadedBytes, long totalBytes) {

    }

    @Override
    public void onError(Exception exception) {
        Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onCompleted(int serverResponseCode, byte[] serverResponseBody) {
        // progressDialog.dismiss();
        loading.setVisibility(View.INVISIBLE);
        loadingtext.setVisibility(View.VISIBLE);
        Intent it = new Intent(PDFUpload_Activity.this, TransactionDetails_Activity.class);
        it.putExtra("PDF_REFNO", PdfID);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_right,R.anim.nothing);
        finish();

    }

    @Override
    public void onCancelled() {
        //your implementation
    }

    // Requesting run time permission method starts from here.
    public void RequestRunTimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(PDFUpload_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {

            Toast.makeText(PDFUpload_Activity.this,"READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(PDFUpload_Activity.this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {

        switch (RC) {

            case 1:

                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {

                    //Toast.makeText(PDFUpload_Activity.this,"Permission Granted", Toast.LENGTH_LONG).show();

                } else {

                    //Toast.makeText(PDFUpload_Activity.this,"Permission Canceled", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    @Override
    public void onBackPressed() {

        Intent it = new Intent(PDFUpload_Activity.this, MainActivity.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();



    }

}
