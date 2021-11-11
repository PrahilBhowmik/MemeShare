package com.example.memesharebyprahil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;


public class MainActivity extends AppCompatActivity {

    String url1=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load_meme();
    }
    private void load_meme()
    {
        ProgressBar pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        String url ="https://meme-api.herokuapp.com/gimme";
        ImageView img = findViewById(R.id.MemeImg);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    url1 = null;
                    try {
                        url1 = response.getString("url");
                    } catch (JSONException e) {
                        pb.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this,"Can't load meme",Toast.LENGTH_LONG).show();
                    }
                    Glide.with(MainActivity.this).load(url1).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            pb.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this,"Can't load meme",Toast.LENGTH_LONG).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            pb.setVisibility(View.INVISIBLE);
                            return false;
                        }
                    }).into(img);
                }, error -> {Toast.makeText(MainActivity.this,"Can't load meme",Toast.LENGTH_LONG).show();
                    pb.setVisibility(View.INVISIBLE);});
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
    public void share_fun(View view) {
        Intent I = new Intent(Intent.ACTION_SEND);
        String s = "Hey checkout this awesome meme on\n"+url1;
        I.putExtra(Intent.EXTRA_TEXT, s);
        I.setType("text/plain");
        Intent shareIntent = Intent.createChooser(I, null);
        startActivity(shareIntent);
    }

    public void next_fun(View view) {
        load_meme();
    }
}