package com.sunzxy.frescoplus;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.imagepipeline.image.ImageInfo;
import com.sunzxy.frescopluslib.core.FrescoPriority;
import com.sunzxy.frescopluslib.request.callback.FPFetchCallback;
import com.sunzxy.frescopluslib.request.callback.FPLoadCallback;
import com.sunzxy.frescopluslib.request.impl.FrescoPlusFetcher;
import com.sunzxy.frescopluslib.widget.FrescoPlusView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FrescoPlusView mFrescoPlusView = (FrescoPlusView) findViewById(R.id.fresco_view);
        Uri uri = Uri.parse("http://pic23.nipic.com/20120812/4277683_204018483000_2.jpg");
        //Use
        //——————————————————————————————————————————————————————————————————————————————————————————
        //1.
        mFrescoPlusView.showImage(uri);
        //2.
        mFrescoPlusView.showImage(R.mipmap.ic_launcher);
        //3.
        FrescoPlusFetcher.newFetcher()
                .withFadeDuration(350)
                .withDefaultDrawable(getResources().getDrawable(R.mipmap.ic_launcher))
                .build()
                .fetch(mFrescoPlusView,uri);
        //4.
        FrescoPlusFetcher.newFetcher()
                .withFadeDuration(350)
                .withDefaultDrawable(getResources().getDrawable(R.mipmap.ic_launcher))
                .build()
                .fetch(mFrescoPlusView, uri, new FPFetchCallback<ImageInfo>() {
                    @Override
                    public void onSuccess(ImageInfo result) {
                        // do something
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
        //5.
        FrescoPlusFetcher.newFetcher()
                .withFadeDuration(350)
                .withRequestPriority(FrescoPriority.HIGH)
                .withAutoRotateEnabled(true)
                .build()
                .fetch(uri, new FPLoadCallback<Bitmap>() {
                    @Override
                    public void onSuccess(Uri uri, Bitmap result) {
                        // do something
                    }

                    @Override
                    public void onFailure(Uri uri, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Uri uri) {

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
