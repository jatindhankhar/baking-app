package in.jatindhankhar.bakingapp.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.jatindhankhar.bakingapp.R;
import in.jatindhankhar.bakingapp.model.StepsItem;
import in.jatindhankhar.bakingapp.utils.Constants;

/**
 * Created by jatin on 1/20/18.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private List<StepsItem> stepsItems;
    private Context context;

    public StepsAdapter(Context context, List<StepsItem> stepsItems) {
        this.stepsItems = stepsItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_card, parent, false);
        return new StepsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.stepHeading.setText(stepsItems.get(position).getShortDescription());
        holder.stepDescription.setText(stepsItems.get(position).getDescription());


        holder.exoPlayer.requestFocus();
        if (!stepsItems.get(position).getVideoURL().isEmpty()) {

            holder.exoPlayer.setPlayer(initPlayer(stepsItems.get(position).getVideoURL()));
        }

        //  DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
// Produces DataSource instances through which media data is loaded.

    }

    @Override
    public int getItemCount() {
        return stepsItems.size();
    }

    private SimpleExoPlayer initPlayer(String url) {
        if (!url.isEmpty()) {
            Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            SimpleExoPlayer player =
                    ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, Constants.PREF_FILE), (TransferListener<? super DataSource>) bandwidthMeter);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(url));
            player.prepare(videoSource);
            return player;

        }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_heading)
        TextView stepHeading;
        @BindView(R.id.step_description)
        TextView stepDescription;
        @BindView(R.id.exo_player)
        SimpleExoPlayerView exoPlayer;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
