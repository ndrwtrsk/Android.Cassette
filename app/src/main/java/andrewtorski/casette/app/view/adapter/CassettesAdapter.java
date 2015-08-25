package andrewtorski.casette.app.view.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import andrewtorski.casette.R;
import andrewtorski.casette.app.model.CassetteModel;

public class CassettesAdapter extends RecyclerView.Adapter<CassettesAdapter.CassetteViewHolder> {

    public interface OnItemClickListener {
        void onUserItemClicked(CassetteModel cassetteModel);
    }

    //region Private fields

    private List<CassetteModel> cassetteModelList;
    private OnItemClickListener onItemClickListener;

    //endregion Private fields

    //region Constructor

    public CassettesAdapter(Collection<CassetteModel> cassetteModelCollection) {
        this.setCassetteModelList(cassetteModelCollection);
    }

    //endregion Constructor

    //region RecyclerView.Adapter Methods

    @Override
    public CassetteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View cassetteCardView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cassette_card_view, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters

        return new CassetteViewHolder(cassetteCardView);
    }

    @Override
    public void onBindViewHolder(CassetteViewHolder cassetteViewHolder, int i) {
        CassetteModel cassette = cassetteModelList.get(i);
        String title = cassette.getTitle(),
                description = cassette.getDescription();

        cassetteViewHolder.textViewTitle.setText(title);
        cassetteViewHolder.textViewDescription.setText(description);
    }

    @Override
    public int getItemCount() {
        return cassetteModelList.size();
    }

    //endregion RecyclerView.Adapter Methods

    //region Methods

    public void setCassetteModelList(Collection<CassetteModel> cassetteModelCollection) {
        validateCassetteCollection(cassetteModelCollection);
        cassetteModelList = (List<CassetteModel>) cassetteModelCollection;
    }

    private void validateCassetteCollection(Collection<CassetteModel> cassetteModelCollection) {
        if (cassetteModelCollection == null) {
            throw new IllegalArgumentException("Collection of Cassettes cannot be null.");
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //endregion Methods

    static class CassetteViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitle,
                textViewDescription;

        public CassetteViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.cassette_card_view_text_title);
            textViewDescription = (TextView) itemView.findViewById(R.id.cassette_card_view_text_description);

        }
    }

}
