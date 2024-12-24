package aaacom.example.healthcareproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import aaacom.example.healthcareproject.entities.Medicine;
import aaacom.example.healthcareproject.utils.Commons;

public class MedicineAdapter extends android.widget.BaseAdapter {
    private Context context;
    private int layout;
    private List<Medicine> medicines;

    public MedicineAdapter(Context context, int layout, List<Medicine> medicines) {
        this.context = context;
        this.layout = layout;
        this.medicines = medicines;
    }
    public void updateList(List<Medicine> newList) {
        medicines.clear();
        medicines.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return medicines.size();
    }

    @Override
    public Object getItem(int position) {
        return medicines.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(layout, parent, false);
            holder = new ViewHolder();
            holder.txtName = view.findViewById(R.id.txtName);
            holder.txtPrice = view.findViewById(R.id.txtPrice);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Medicine medicine = medicines.get(position);
        holder.txtName.setText(medicine.getName());
        holder.txtPrice.setText("Gia tiền: " + Commons.FormatDecimalCommon(medicine.getPrice()));

        return view;
    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtPrice;
    }
}