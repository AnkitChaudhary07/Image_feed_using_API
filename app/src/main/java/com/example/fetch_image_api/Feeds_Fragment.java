package com.example.fetch_image_api;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

public class Feeds_Fragment extends Fragment {
    ImageView memeImageView;
    Button nextButton, shareButton;
    ProgressBar progressBar;
    String currentImageUrl = "";
    public Feeds_Fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feeds, container, false);
        memeImageView = view.findViewById(R.id.memeImageView);
        nextButton = view.findViewById(R.id.nextButton);
        shareButton = view.findViewById(R.id.shareButton);
        progressBar = view.findViewById(R.id.progressBar);


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareMeme();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMeme();
            }
        });
        loadMeme();


        return view;
    }

    private void loadMeme() {
        progressBar.setVisibility(View.VISIBLE); // Show the ProgressBar before making the request
        String url = "https://meme-api.com/gimme";
//        String url = "https://www.google.com";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            currentImageUrl = response.getString("url");

                            Glide.with(requireContext()).load(currentImageUrl).listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            }).centerInside().into(memeImageView);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest);

        // Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void shareMeme() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"Have a look at this Meme... \n"+currentImageUrl);
        Intent chooser = Intent.createChooser(intent,"Share via");
        // Check if there are any apps that can handle the share intent
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivity(chooser);
        }
    }
}