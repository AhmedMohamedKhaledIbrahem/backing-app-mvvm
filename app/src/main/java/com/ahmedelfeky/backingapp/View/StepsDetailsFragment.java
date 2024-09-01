package com.ahmedelfeky.backingapp.View;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedelfeky.backingapp.Model.RecipeResults;
import com.ahmedelfeky.backingapp.Model.StepsItem;
import com.ahmedelfeky.backingapp.Utails.StepsDetailAdapter;
import com.ahmedelfeky.backingapp.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSource;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StepsDetailsFragment extends Fragment implements SendMessage {
   List<StepsItem>  stepsItem;
   int position;
   @BindView(R.id.rv_steps)
   RecyclerView rvStepsList;
   @BindView(R.id.button_next)
    Button nextButton;
    @BindView(R.id.button_back)
    Button backButton;
   @BindView(R.id.exo_playerView)
   PlayerView playerView;
   SimpleExoPlayer simpleExoPlayer=null;
   Task task;
   int screenWidth;


    public StepsDetailsFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        task=(Task)getActivity();
       // task.fullScreen();





        screenWidth=task.checkScreenWidth();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_steps_details, container, false);
        ButterKnife.bind(this,view);
        Task actionBar=(Task)getActivity();
        RecipeResults recipeResults=getArguments().getParcelable("RecipeResultsObject");
        if(savedInstanceState==null){
        position =getArguments().getInt("StepPosition");
        }else{
            position=savedInstanceState.getInt("position");
        }
        stepsItem=recipeResults.getSteps();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE &&screenWidth<600) {
            actionBar.hideActionBar();
            hideView();


                playVideo(position,false);



        } else {

            playVideo(position,false);
            initRecyclerView( stepsItem);
            
        }



        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position",position);
    }

    private void hideView() {
        nextButton.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);
        rvStepsList.setVisibility(View.GONE);
    }

    private void fullScreen() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if(screenWidth<600) {
                getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                        , WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }


    }




    private void initRecyclerView(List<StepsItem> stepsItem) {
        rvStepsList.setLayoutManager(new LinearLayoutManager(getContext()));
        StepsDetailAdapter stepsDetailAdapter=new StepsDetailAdapter(stepsItem);

        stepsDetailAdapter.setClickListener((int position) -> {
            playVideo(position,true);
            this.position =position;
        });
        rvStepsList.setAdapter(stepsDetailAdapter);

    }

    private void playVideo(int position, boolean isExoPlayerRelease) {
        if (isExoPlayerRelease) {
            if (simpleExoPlayer != null) {
                simpleExoPlayer.release();
            }
        }

        String videoUrl = stepsItem.get(position).getVideoURL();
        if (videoUrl != null && !videoUrl.isEmpty()) {
            Uri uri = Uri.parse(videoUrl);
            DefaultDataSource.Factory dataSourceFactory = new DefaultDataSource.Factory(Objects.requireNonNull(getContext())
            );
            MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(uri));

            // Create a new SimpleExoPlayer instance
            simpleExoPlayer = new SimpleExoPlayer.Builder(getContext()).build();
            playerView.setPlayer(simpleExoPlayer);

            // Prepare and play the video
            simpleExoPlayer.setMediaSource(videoSource);
            simpleExoPlayer.prepare();
            simpleExoPlayer.setPlayWhenReady(true);
        }
    }


    @OnClick(R.id.button_next)
    public void onClickNext(){
        if(position <stepsItem.size()-1){
            position++;
            playVideo(position,true);
        }



    }

    @OnClick(R.id.button_back)
    public void onClickBack(){
        if(position!=0){
            position--;
            playVideo(position,true);
        }

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }

    @Override
    public void SendMessage(String message) {
        playVideo(Integer.parseInt(message),true);
    }
}
