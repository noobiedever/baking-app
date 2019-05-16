package com.example.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakingapp.models.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment implements Player.EventListener{
    private static final String TAG = StepDetailFragment.class.getSimpleName();
    private Step[] mSteps;
    private int mPosition;

    @BindView(R.id.pv_exoplayer)
    PlayerView mPlayerView;

    @BindView(R.id.iv_thumbnail)
    ImageView mThumbnailImageView;

    @BindView(R.id.tv_step_instruction)
    TextView mStepInstructionTextView;

    @BindView(R.id.b_next_step)
    Button mNextButton;

    @BindView(R.id.b_previous_step)
    Button mPreviousButton;

    private SimpleExoPlayer mSimpleExoPlayer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        mPosition = intent.getIntExtra(RecipeDetailFragment.EXTRA_POSITION, 0);

        Parcelable[] parcelableExtraArray = intent.getParcelableArrayExtra(RecipeDetailFragment.EXTRA);
        mSteps = Arrays.copyOf(parcelableExtraArray, parcelableExtraArray.length, Step[].class);

        Log.v(TAG, "Steps " + mSteps.length);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_step_detail, container,
                false);

        ButterKnife.bind(this, rootView);

        initialise();

        initialiseButtons();

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition++;
                initialise();
                initialiseButtons();
            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition--;
                initialise();
                initialiseButtons();
            }
        });

        return rootView;
    }

    private void initialiseButtons() {
        if(mPosition <= 0) {
            mPreviousButton.setVisibility(View.GONE);
        } else if(mPosition < (mSteps.length - 1)) {
            mNextButton.setVisibility(View.VISIBLE);
            mPreviousButton.setVisibility(View.VISIBLE);
        }
        if(mPosition >= (mSteps.length - 1)) {
            mNextButton.setVisibility(View.GONE);
        }
    }

    private void initialise() {
        mStepInstructionTextView.setText(mSteps[mPosition].getDesctription());
        if(mSteps[mPosition].getVideoURL().equals("") || mSteps[mPosition].getVideoURL() == null) {
            mPlayerView.setVisibility(View.GONE);
            mThumbnailImageView.setVisibility(View.VISIBLE);

            if(!mSteps[mPosition].getThumbnailURL().equals("") && mSteps[mPosition] != null) {
                Picasso.get().load(Uri.parse(mSteps[mPosition].getThumbnailURL()))
                        .into(mThumbnailImageView);
            } else {
                mThumbnailImageView.setImageResource(R.drawable.ic_recipe_image);
            }
        } else {
            mPlayerView.setVisibility(View.VISIBLE);
            mThumbnailImageView.setVisibility(View.GONE);
            initialiseExoPlayer();
        }
    }

    private void initialiseExoPlayer() {

        if(mSimpleExoPlayer == null) {
            // create player
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
            // attach player
            mPlayerView.setPlayer(mSimpleExoPlayer);
        }

        // prepare player
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), getString(R.string.app_name)));
        Uri videoUri = Uri.parse(mSteps[mPosition].getVideoURL());
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .setExtractorsFactory(new DefaultExtractorsFactory()).createMediaSource(videoUri);
        mSimpleExoPlayer.prepare(videoSource);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if(playWhenReady && playbackState == Player.STATE_READY) {
            // playing
        } else if(playWhenReady) {
            // playback ended, buffering, stopped, or failed
        } else {
            // playback paused
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mSimpleExoPlayer != null) {
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }
}