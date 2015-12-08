package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.taeyeona.amaizingunicornrecipes.Activity.RecipeShow;
import com.taeyeona.amaizingunicornrecipes.Auth;
import com.taeyeona.amaizingunicornrecipes.JSONRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayerFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener {

    final private String mVideoId = "xhUoBKhPq14";
    private String vid;
    private StringBuilder titleList = new StringBuilder();
    private String st;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        st = getActivity().getIntent().getStringExtra("Title");
        st = st.replaceAll(" ","+");
        st=st+"+tutorial";

        //parse data with passed string
        //Crated JSONrequest
        try {
        final JSONRequest jsonRequest = new JSONRequest();
        //Create response
        jsonRequest.createResponse("https://www.googleapis.com/youtube/v3/search", "key",
                "AIzaSyAgluXYn35S2cVNEhiT07qGwN6B2uz7kyk", null, null, st, "",
                Auth.PART_SNIPPET, "1", null, null, "", "", "", 0.0, 0.0, "", null, null);
        //send jsonrequest
        jsonRequest.sendResponse(getContext(), new JSONRequest.VolleyCallBack() {
            @Override
            public void onSuccess() {
                parseJSON(jsonRequest.getResponse());
                vid = titleList.toString();
            }
            @Override
            public void onFailure(){
                Toast.makeText(getContext(), "We could not load your video.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), RecipeShow.class);
                startActivity(intent);
            }
        });


            initialize(Auth.YOUTUBE_DEV_KEY, this);
        }catch (Exception e){
            Intent notFound = new Intent(getActivity(),RecipeShow.class);
            startActivity(notFound);
        }
    }

    /**
     *
     * @param provider
     * @param youTubePlayer
     * @param restored
     *
     * YouTube player fragment that checks whether a video is found
     * to be loaded by youTubePlayer , restored is a flag to check if the video
     * found had loaded yet to manage when the Youtube video can be played
     *
     */
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer,final boolean restored) {


        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    if (vid != null) {
                        try {
                            if (restored) {
                                youTubePlayer.play();
                            } else {
                                youTubePlayer.loadVideo(vid);

                            }
                        } catch (IllegalStateException e) {
                            Log.d("catch", "2 "+  e.getClass().getSimpleName() + " " + e.getMessage());


                        }
                    }
            }
        }, 3000);
    }

    /**
     *
     * @param provider
     * @param youTubeInitializationResult
     *
     * Runs if YouTube player fails to play a video and displays toasts to show user
     * that no video was found or loaded to be played
     */
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            if (youTubeInitializationResult.isUserRecoverableError()) {
                youTubeInitializationResult.getErrorDialog(getActivity(), 1).show();
            } else {
                //Handle the failure
                Toast.makeText(getActivity(), "BAD", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), RecipeShow.class);
                startActivity(intent);
            }
    }

    /**
     *
     * @param pResponse
     *
     * Checks whether a JSONreponse was found, if there was response
     * then parseJSOn will begin to parse videoId which is used to load
     * the youtube player
     */
    private void parseJSON(JSONObject pResponse){
        try{

            JSONArray items = pResponse.getJSONArray(Auth.endpointRecipe.KEY_items);
            JSONObject id = items.getJSONObject(0);
            //inside first array element
            JSONObject idObj = id.getJSONObject(Auth.endpointRecipe.KEY_id);
            titleList.append(idObj.getString(Auth.endpointRecipe.KEY_VideoId));
            //if no video tutorials found
            if (titleList == null){

                Toast.makeText(getContext(),"No Video Tutorials Found",Toast.LENGTH_LONG).show();
                Intent notFound = new Intent(getActivity(),RecipeShow.class);
                startActivity(notFound);
            }
        }catch(JSONException ex){
            Toast.makeText(getContext(), "Sorry, we could not load your video.", Toast.LENGTH_LONG).show();
        }
    }
}