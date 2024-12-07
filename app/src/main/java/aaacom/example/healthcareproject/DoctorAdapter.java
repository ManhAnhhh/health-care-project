package aaacom.example.healthcareproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import aaacom.example.healthcareproject.entities.Doctor;

public class DoctorAdapter extends ArrayAdapter<Doctor> {
    private final Activity context;
    private final int layoutId;
    private final ArrayList<Doctor> doctorList;

    public DoctorAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Doctor> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutId = resource;
        this.doctorList = objects;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        if (doctorList.size() > 0 && position >= 0) {
            TextView txtvDoctorName = convertView.findViewById(R.id.txtv_DoctorName);
            TextView txtvSpecialization = convertView.findViewById(R.id.txtv_Specialization);
            TextView txtvExperience = convertView.findViewById(R.id.txtv_Experience);
            TextView txtvHospitalAddress = convertView.findViewById(R.id.txtv_HospitalAddress);
            TextView txtvFee = convertView.findViewById(R.id.txtv_Fee);
            TextView txtvContact = convertView.findViewById(R.id.txtv_Contact);

            Doctor doctor = doctorList.get(position);

            txtvDoctorName.setText(doctor.getName());
            txtvSpecialization.setText(doctor.getSpecialization());
            txtvExperience.setText(doctor.getExperience() + " năm kinh nghiệm");
            txtvHospitalAddress.setText("Địa chỉ: " + doctor.getHospitalAddress());
            txtvContact.setText("SĐT: " + doctor.getContact());

            Locale vietnam = new Locale("vi", "VN");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnam);
            String formattedFee = currencyFormatter.format(doctor.getFee());
            txtvFee.setText("Phí khám: " + formattedFee);
        }

        return convertView;
    }
}
