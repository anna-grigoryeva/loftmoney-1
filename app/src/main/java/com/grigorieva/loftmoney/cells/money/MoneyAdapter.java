package com.grigorieva.loftmoney.cells.money;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grigorieva.loftmoney.R;

import java.util.ArrayList;
import java.util.List;

public class MoneyAdapter extends RecyclerView.Adapter<MoneyAdapter.MoneyViewHolder> {

    private List<MoneyCellModel> moneyCellModelsList = new ArrayList<MoneyCellModel>();

    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.cell_money, null);

        return new MoneyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoneyViewHolder holder, int position) {
        holder.bind(moneyCellModelsList.get(position));
    }

    public void addData(MoneyCellModel moneyCellModel) {
        moneyCellModelsList.add(moneyCellModel);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return moneyCellModelsList.size();
    }

    static class MoneyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView valueView;

        public MoneyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.cellMoneyNameView);
            valueView = itemView.findViewById(R.id.cellMoneyValueView);
        }

        public void bind(final MoneyCellModel moneyCellModel) {
            nameView.setText(moneyCellModel.getName());
            valueView.setText(valueView.getContext().getResources().getString
                    (R.string.price_with_currency, String.valueOf(moneyCellModel.getValue())));
        }
    }
}
