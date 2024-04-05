package com.example.evas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.evas.Models.Drivers;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class DriverAdapterAA extends FirebaseRecyclerAdapter<Drivers, DriverAdapterAA.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DriverAdapterAA(@NonNull FirebaseRecyclerOptions<Drivers> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Drivers model) {

            holder.driverName.setText(model.getName());
            holder.userName.setText(model.getUserName());
            holder.phoneNo.setText(model.getPhoneNumber());
            holder.aadharNo.setText(model.getAadharNo());

        Glide.with(holder.img.getContext())
                .load(model.getProfilePic())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.img);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_driver_management_main_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView driverName, userName, phoneNo, aadharNo;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView)itemView.findViewById(R.id.driverImageView);
            driverName = (TextView)itemView.findViewById(R.id.driverName);
            userName = (TextView)itemView.findViewById(R.id.driverUserName);
            phoneNo = (TextView)itemView.findViewById(R.id.driverPhoneNumber);
            aadharNo = (TextView)itemView.findViewById(R.id.driverAadharNumber);

        }
    }
}
