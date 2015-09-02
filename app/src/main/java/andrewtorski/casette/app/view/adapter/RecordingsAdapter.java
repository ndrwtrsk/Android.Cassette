package andrewtorski.casette.app.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import andrewtorski.casette.R;
import andrewtorski.casette.app.model.RecordingModel;
import andrewtorski.casette.app.model.processor.UserReadableProcessor;
import butterknife.Bind;
import butterknife.ButterKnife;

public class RecordingsAdapter extends RecyclerView.Adapter<RecordingsAdapter.RecordingViewHolder> {

    public interface OnItemClickListener {
        void onRecordingItemClicked(RecordingModel recordingModel);
    }

    //region Private fields

    /**
     * Adapted list.
     */
    private List<RecordingModel> recordingModelList;

    /**
     * On item click listener.
     */
    private RecordingsAdapter.OnItemClickListener onItemClickListener;

    //endregion Private fields

    //region Constructor

    public RecordingsAdapter(List<RecordingModel> recordingModelList) {
        this.recordingModelList = recordingModelList;
    }

    //endregion Constructors

    //region Methods

    public void setRecordingModelList(Collection<RecordingModel> recordingModelCollection) {
        validateCassetteCollection(recordingModelCollection);
        this.recordingModelList = (List<RecordingModel>) recordingModelCollection;
    }

    public void setOnItemClickListener(RecordingsAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //endregion Methods

    //region Private helper methods

    private void validateCassetteCollection(Collection<RecordingModel> recordingModelCollection) {
        if (recordingModelCollection == null) {
            throw new IllegalArgumentException("Collection of Recordings cannot be null.");
        }
    }

    //endregion Private helper methods

    //region RecyclerView.Adapter<RecordingsAdapter.RecordingViewHolder> methods

    @Override
    public RecordingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View recordingCardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recording_card_view, parent, false);

        return new RecordingViewHolder(recordingCardView);
    }

    @Override
    public void onBindViewHolder(RecordingViewHolder holder, int position) {
        final RecordingModel currentRecording = this.recordingModelList.get(position);
        holder.bind(currentRecording);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RecordingsAdapter.this.onItemClickListener != null) {
                    RecordingsAdapter.this.onItemClickListener.onRecordingItemClicked(currentRecording);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.recordingModelList.size();
    }

    //endregion RecyclerView.Adapter<RecordingsAdapter.RecordingViewHolder> methods

    static class RecordingViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.recording_card_view_date_time)
        TextView dateTime;

        @Bind(R.id.recording_card_view_length)
        TextView length;


        public RecordingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Binds the provided RecordingModel properties to the ViewHolder.
         *
         * @param recordingModel RecordingModel to bind.
         */
        public void bind(RecordingModel recordingModel) {
            if (recordingModel == null) {
                return;
            }
            Date dateTimeOfRecording = recordingModel.getDateTimeOfRecording();
            long length = recordingModel.getLengthInMilliseconds();

            String userReadableDateTimeString = UserReadableProcessor.getUserReadableDate(dateTimeOfRecording);
            String userReadableLength = UserReadableProcessor.getUserReadableLengthOfTimeFromMilliseconds(length);

            //  ALWAYS use Integer.toString(...) when working with integers.
            //  If you try to set integer as text, you call method setText(int resID)
            //  and application try to set as text some string resource with this resID.
            this.dateTime.setText(userReadableDateTimeString);
            this.length.setText(userReadableLength);
        }
    }
}
