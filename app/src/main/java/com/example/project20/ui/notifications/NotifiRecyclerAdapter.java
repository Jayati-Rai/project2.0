package com.example.project20.ui.notifications;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project20.R;

import java.util.ArrayList;

public class NotifiRecyclerAdapter extends RecyclerView.Adapter<NotifiRecyclerAdapter.Viewholder> {

    Context context;
    ArrayList<NotificationList> noticelist;

    public NotifiRecyclerAdapter(Context context,ArrayList<NotificationList> noticelist)
    {
        this.context=context;
        this.noticelist=noticelist;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.notification_recycler_row,parent,false);
        return new Viewholder(view);
    }

    //TO-DO -- Fix the url
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        NotificationList NL= noticelist.get(position);
        holder.txtview.setText(NL.str);
        holder.txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txtview.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });

    }

    @Override
    public int getItemCount() {
        return noticelist.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        TextView txtview;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtview=itemView.findViewById(R.id.textView);
        }
    }
}
