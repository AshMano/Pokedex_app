package org.esiea.manoranjithan_papail.pokdex;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private CardView pokedexCard, listeCard, infoCard, aideCard, quitterCard;
    private Long downloadId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pokedexCard = (CardView) findViewById(R.id.pokedex);
        listeCard = (CardView) findViewById(R.id.liste);
        infoCard = (CardView) findViewById(R.id.info);
        aideCard = (CardView) findViewById(R.id.aide);
        quitterCard = (CardView) findViewById(R.id.quitter);

        pokedexCard.setOnClickListener(this);
        listeCard.setOnClickListener(this);
        infoCard.setOnClickListener(this);
        aideCard.setOnClickListener(this);
        quitterCard.setOnClickListener(this);
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long broadcastedDownloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (broadcastedDownloadID == downloadId){
                    if (getDownloadStatus() == DownloadManager.STATUS_SUCCESSFUL){
                        Toast.makeText(MainActivity.this, "Download complete.", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, "Download not complete.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, filter);



    }

    private int getDownloadStatus(){
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Cursor cursor = downloadManager.query(query);

        if (cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            int status = cursor.getInt(columnIndex);

            return status;
        }

        return DownloadManager.ERROR_UNKNOWN;

    }



    public void startDownload(View view){
        Uri uri = Uri.parse("https://www.google.fr/search?q=pokemon&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiTqMT2uqrbAhXJIsAKHTlRB_oQ_AUICygC#imgrc=G7kL5QLjRVBW7M:");
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("Image download");
        request.setDescription("Download Image");
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "Large Image.jpg");
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(request);

    }

    public void cancelDownload(View view){
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadManager.remove(downloadId);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(MainActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {

        Intent i;

        switch (v.getId()) {
            case R.id.pokedex:
                i = new Intent(this, RealPokedex.class);
                startActivity(i);
                break;
            case R.id.liste:
                i = new Intent(this, Liste.class);
                startActivity(i);
                break;
            case R.id.info:
                i = new Intent(this, Info.class);
                startActivity(i);
                break;
            case R.id.aide:
                i = new Intent(this, Aide.class);
                startActivity(i);
                break;
            case R.id.quitter:
                i = new Intent(this, Quitter.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }
}