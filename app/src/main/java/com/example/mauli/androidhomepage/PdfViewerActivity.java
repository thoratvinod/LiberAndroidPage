package com.example.mauli.androidhomepage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class PdfViewerActivity extends AppCompatActivity {


    private StorageReference eBookReference;
    private String eBookId;
    private ProgressDialog progressDialog;
    private PDFView pdfView;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarInpdfActivity);
        setSupportActionBar(toolbar);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitleTextColor(Color.WHITE);


        progressDialog = new ProgressDialog(this);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        progressBar = (ProgressBar) findViewById(R.id.progressbarInPdfViewer);
        progressBar.setVisibility(View.VISIBLE);


        Intent intent = getIntent();
        EBook eBook = intent.getParcelableExtra("E-Book");

        eBookId = eBook.geteBookId();
        try {
            downloadPdfFromStorage();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Integer id = item.getItemId();

        if (id == 16908332){


            onBackPressed();
            return true;
            //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        }


        return super.onOptionsItemSelected(item);
    }

    private void downloadPdfFromStorage() throws IOException {

        eBookReference = FirebaseStorage.getInstance().getReference("Documents/"+eBookId);

        if (eBookReference!=null){

            /*progressDialog.setTitle("Downloading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgress(0);
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);*/


            final long ONE_MEGABYTE = 20 * 1024 * 1024;



            eBookReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    progressBar.setVisibility(View.GONE);
                    pdfView.fromBytes(bytes).load();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(PdfViewerActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    // Handle any errors
                }
            });
        }


    }
}
