package com.example.project20.ui.nextphase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project20.R;

import com.example.project20.putPDF;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataDisplayActivity extends AppCompatActivity {

    protected String selection,year, subject;
    static int i;
    String path;
    TextView pathText;

    ListView listView;
    List<putPDF> uploadedPDF;
    private ArrayAdapter<String> adapter;
    private List<String> pdfNames;
    private List<String> pdfUrls;

    FloatingActionButton addPDFButton;
    StorageReference storageReference,storageReferenceReceive;
    DatabaseReference databaseReference,databaseReferenceReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_database);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            initialize();
            pathText.setText(path);
            action();





            return insets;

        });
    }




    private void initialize()
    {
        System.out.println("Entered phase 1");
        i=0;
        addPDFButton=findViewById(R.id.addPDF);
        storageReference= FirebaseStorage.getInstance().getReference();
        pathInfo();
        path=selection+"/"+year+"/"+subject;
        pathText=findViewById(R.id.pathText);
        databaseReference= FirebaseDatabase.getInstance().getReference(path);

        listView=findViewById(R.id.listview);
        retrievePDFFiles();
        if(listView==null)
            System.out.println("Yes it's null!");
        System.out.println("Exited phase 1");
    }

    private void action() {
        System.out.println("Entered phase 2");
        //addPDFButton.setEnabled(true);
        //addPDFButton.setVisibility(View.VISIBLE);
        addPDFButton.setOnClickListener(v -> selectPDF()
                );
        System.out.println("Exited phase 2");
    }

    private void selectPDF() {
        System.out.println("Entered phase 3");
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF File Select"),12);
        System.out.println("Exited phase 3");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        System.out.println("Entered phase 4");
        super.onActivityResult(requestCode, resultCode, data);
        String filename;

        if(requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            Uri pdfUri=data.getData();
            filename=null;
            try(Cursor cursor=getContentResolver().query(pdfUri,null,null,null,null)){
                if(cursor!=null && cursor.moveToFirst())
                {
                    int index=cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    filename=cursor.getString(index);
                }

            }
            catch (Exception e) {
                e.printStackTrace();}

            uploadPDFFileFirebase(pdfUri,filename);
            // uploadPDFFileFirebase(data.getData());
        }
        System.out.println("Exited phase 4");
    }

    private void uploadPDFFileFirebase(Uri data,String filename) {
        System.out.println("Entered phase 5");
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("File is loading...");
        progressDialog.show();
        StorageReference reference=storageReference.child(filename);
        reference.putFile(data)
                .addOnSuccessListener(taskSnapshot -> {

                    Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();

                    while(!uriTask.isComplete());
                    Uri uri=uriTask.getResult();

                    putPDF putpdf=new putPDF(selection+i,uri.toString());
                    i++;
                    databaseReference.child(Objects.requireNonNull(databaseReference.push().getKey())).setValue(putpdf);
                    Toast.makeText(DataDisplayActivity.this,"File Uploaded",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

                }).addOnProgressListener(snapshot -> {
                    double progress=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                    progressDialog.setMessage("File Uploaded... "+(int)progress+"%");
                });
        System.out.println("Exited phase 5");
    }



    private void pathInfo() {
        System.out.println("Entered phase 6");
        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        if (extras == null) throw new AssertionError();
        selection= extras.getString("Selection");
        year=extras.getString("Year");
        subject=extras.getString("subject");

    }



    private void retrievePDFFiles() {
        pdfNames=new ArrayList<>();
        pdfUrls = new ArrayList<>();
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, pdfNames);
        listView.setAdapter(adapter);

        uploadedPDF=new ArrayList<>();
        fetchFilesFromFirebase();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                // Get the URL of the clicked file
                String filename=pdfNames.get(position);
                String url = pdfUrls.get(position);
                // Download the file
                downloadFile(getApplicationContext(),url,filename);
            }
        });

// Fetch PDF file URLs from Firebase Storage



    }

    private void fetchFilesFromFirebase() {

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference pdfsRef = storageRef.child(path);

        pdfsRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                    String fileName = item.getName();
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String fileUrl = uri.toString();
                            pdfNames.add(fileName);
                            pdfUrls.add(fileUrl);
                            adapter.notifyDataSetChanged();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Error", "Failed to get download URL for " + fileName, e);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Error","oops",e);  // Handle any errors
            }
        });
    }

    private void downloadFile(Context context, String url, String fileName) {
        Log.d("downloadfile","WE are here!");
        // Create a local file to store the downloaded PDF
        File localFile = new File(context.getExternalFilesDir(null), fileName);

        Log.d("URL",url);
        // Download the PDF file from Firebase Storage
        FirebaseStorage.getInstance().getReferenceFromUrl(url)
                .getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // File downloaded successfully
                        Toast.makeText(context, "PDF downloaded: " + localFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle errors while downloading the file
                        Toast.makeText(context, "Failed to download PDF", Toast.LENGTH_LONG).show();
                    }
                });
    }

}