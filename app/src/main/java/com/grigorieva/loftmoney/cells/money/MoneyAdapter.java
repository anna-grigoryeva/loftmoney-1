package com.grigorieva.loftmoney.cells.money;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.grigorieva.loftmoney.R;

import java.util.ArrayList;
import java.util.List;

public class MoneyAdapter extends RecyclerView.Adapter<MoneyAdapter.MoneyViewHolder> {

    private List<MoneyCellModel> moneyCellModelsList = new ArrayList<MoneyCellModel>();
    private final int colorId;

    public MoneyAdapter(final int colorId) {
        this.colorId = colorId;
    }

    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.cell_money, null);

        return new MoneyViewHolder(itemView, colorId);
    }

    @Override
    public void onBindViewHolder(@NonNull MoneyViewHolder holder, int position) {
        holder.bind(moneyCellModelsList.get(position));
    }

    public void addData(MoneyCellModel moneyCellModel) {
        moneyCellModelsList.add(moneyCellModel);
        notifyDataSetChanged();
    }

    public void clearItems () {
        moneyCellModelsList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return moneyCellModelsList.size();
    }

    static class MoneyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView valueView;

        public MoneyViewHolder(@NonNull View itemView, int colorId) {
            super(itemView);
            nameView = itemView.findViewById(R.id.cellMoneyNameView);
            valueView = itemView.findViewById(R.id.cellMoneyValueView);
            final Context context = valueView.getContext();
            valueView.setTextColor(ContextCompat.getColor(context, colorId));
        }

        public void bind(final MoneyCellModel moneyCellModel) {
            nameView.setText(moneyCellModel.getName());
            valueView.setText(valueView.getContext().getResources().getString
                    (R.string.price_with_currency, String.valueOf(moneyCellModel.getValue())));
        }
    }
}
