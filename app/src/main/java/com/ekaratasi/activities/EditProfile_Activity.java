package com.ekaratasi.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ekaratasi.FilePath;
import com.ekaratasi.MainActivity;
import com.ekaratasi.POJO.EditProfile;
import com.ekaratasi.R;
import com.ekaratasi.SingleUploadBroadcastReceiver;
import com.ekaratasi.helper.SQLiteHandler;
import com.ekaratasi.helper.SessionManager;
import com.ekaratasi.model.PPItem;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EditProfile_Activity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener, SingleUploadBroadcastReceiver.Delegate {
    private Uri filePath;
    private Bitmap bitmap;
    private String pdfFileName;
    private PDFView pdfView;
    private String pdfPath;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private int pageNumber = 0;
    CardView pick;
    TextView flname,loadingtext,percentageimg;
    ImageView retry,swipeimage,back;
    Button UploadButton;
    View loading;
    EditText txtName,txtPhone,txtEmail;
    private String mediaPath;
    private String postPath;

    // Define strings to hold given pdf name, path and ID.
    String ImageNameHolder, ImagePathHolder, ImageID;

    // Server URL.
    public static final String IMAGE_UPLOAD_HTTP_URL = "http://ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/server_upload_pdf.php";

    // Pdf upload request code.
    public int PDF_REQ_CODE = 1;

    // Define strings to hold given pdf name, path and ID.
    String PdfNameHolder, PdfPathHolder, PdfID;
    ImageView profile_image;
    ImageButton pickphoto;
    TextView saveprofile,imagenjia;
    private SQLiteHandler db;
    private SessionManager session;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final String TAG = "AndroidUploadService";

    private final SingleUploadBroadcastReceiver uploadReceiver =
            new SingleUploadBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        pickphoto=findViewById(R.id.pickphoto);
        profile_image=findViewById(R.id.profile_image);
        imagenjia=findViewById(R.id.imagenjia);


        back=findViewById(R.id.back);
        saveprofile=findViewById(R.id.saveprofile);

        txtEmail=findViewById(R.id.txtEmail);
        txtPhone=findViewById(R.id.txtPhone);
        txtName=findViewById(R.id.txtName);
        pickphoto=findViewById(R.id.pickphoto);
        profile_image=findViewById(R.id.profile_image);
        imagenjia=findViewById(R.id.imagenjia);
        percentageimg=findViewById(R.id.percentageimg);

        //get the user_id
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("username");
        String phone = user.get("phone");
        String email = user.get("created_at");


        txtPhone.setText(phone);
        txtName.setText(name);
        txtEmail.setText(email);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(EditProfile_Activity.this, Settings_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);

                finish();
            }
        });


        //make notification statusbar dark
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

         //load profile image
        loadProfilePhoto();

        // Method to enable runtime permission.
        RequestRunTimePermission();

        //pick profile photo
        pickphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);

            }
        });


        saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveprofile.setTextColor(Color.LTGRAY);

                EditProfile();

                //end session
                session.setLogin(false);
                //delete user
                db.deleteUsers();

                Intent it = new Intent(EditProfile_Activity.this, Activity_Login.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
                finish();
            }
        });


    }


    /**loading profile picture**/

    private void loadProfilePhoto(){

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");
        String URL_DATA="http://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_profilephoto.php?user_id="+user_id+"";


        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                URL_DATA,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        try {
                            JSONObject jsonObject=new JSONObject(s);

                            JSONArray array=jsonObject.getJSONArray("heroes");

                            for(int i=0; i<array.length();i++){
                                JSONObject o=array.getJSONObject(i);
                                PPItem item=new PPItem(
                                        o.getString("profilephoto")
                                );


                                String ImgUrl=o.getString("profilephoto");

                                Picasso.with(getApplicationContext()).load(ImgUrl).fit().into(profile_image);


                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyerror) {





                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    public void EditProfile(){

        //get the user_id
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");

        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ekaratasikenya.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        com.ekaratasi.ApiServiceEditProfile service = retrofit.create(com.ekaratasi.ApiServiceEditProfile.class);
        EditProfile ep = new EditProfile();
        ep.setId(user_id);
        ep.setName(txtName.getText().toString());
        ep.setPhone(txtPhone.getText().toString());
        ep.setEmail(txtEmail.getText().toString());

        Call<EditProfile> call = service.insertProfileData(ep.getId(),ep.getName(), ep.getPhone(),ep.getEmail());

        call.enqueue(new Callback<EditProfile>() {
            @Override
            public void onResponse(Call<EditProfile> call, Response<EditProfile> response) {
                EditProfile tuongee=response.body();
                String ongeleshwa=tuongee.getError_msg();

                Integer num =Integer.parseInt(tuongee.getError());


                if(num==1){

                    Toast.makeText(EditProfile_Activity.this, ongeleshwa, Toast.LENGTH_LONG).show();

                }
                else{

                    Toast.makeText(EditProfile_Activity.this, ongeleshwa, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<EditProfile> call, Throwable t) {

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

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    // Set the Image in ImageView for Previewing the Media
                    profile_image.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    cursor.close();


                    postPath = mediaPath;

                    //set image path to the textview
                    imagenjia.setText(postPath);


                    //upload the profile photo
                   ImageUploadFunction();
                }


            }

        }
        else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show();
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


    //Upload Image.
    public void ImageUploadFunction() {


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String user_id = user.get("uid");

        // Getting pdf name from EditText.
        ImageNameHolder = "Image Document";//PdfNameEditText.getText().toString().trim();

        // Getting file path using Filepath class.
        //PdfPathHolder =  FilePath.getPath(this, filePath);


        /** GETTING FILE PATH FROM THE TEXTVIEW**/

        ImagePathHolder = imagenjia.getText().toString();


        // If file path object is null then showing toast message to move file into internal storage.
        if (ImagePathHolder == null) {

            Toast.makeText(this, "Please move your PDF file to internal storage & try again.", Toast.LENGTH_LONG).show();

        }
        // If file path is not null then PDF uploading file process will starts.
        else {

            try {

                // progressDialog.dismiss();

                ImageID = user_id;
                uploadReceiver.setDelegate(this);
                uploadReceiver.setUploadID(ImageID);
                new MultipartUploadRequest(this, ImageID, IMAGE_UPLOAD_HTTP_URL)
                        .addFileToUpload(ImagePathHolder, "pdf")
                        .addParameter("name", ImageID)
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

        percentageimg.setText(prog+"%");

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


    }

    @Override
    public void onCancelled() {
        //your implementation
    }

    // Requesting run time permission method starts from here.
    public void RequestRunTimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(EditProfile_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {

            Toast.makeText(EditProfile_Activity.this,"READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(EditProfile_Activity.this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

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

        Intent it = new Intent(EditProfile_Activity.this, MainActivity.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left,R.anim.nothing);
        finish();



    }




    //Pick your document from Phone Internal Storage Dialog

    public void showDialogPickInternal() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_pick_internal);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        ((Button) dialog.findViewById(R.id.btnCrossC)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }




}
