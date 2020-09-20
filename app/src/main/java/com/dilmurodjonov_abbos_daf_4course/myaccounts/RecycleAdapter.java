package com.dilmurodjonov_abbos_daf_4course.myaccounts;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.myViewHolder> {

    Context context;
    List<MyData> myList;
    //boolean[] isShowed;


    public RecycleAdapter(Context context, List<MyData> myList) {
        this.context = context;
        this.myList = myList;
        //isShowed = new boolean[myList.size()];
        //for(int i = 0 ; i < isShowed.length;i++)isShowed[i] = false;
    }

    public void setMyList(List<MyData>list){
        myList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.item,parent,false);

        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, final int position) {

        holder.loginTxt.setText(myList.get(position).getLogin());
        holder.passTxt.setText(myList.get(position).getPassword());
        holder.commentTxt.setText(myList.get(position).getComment());
        holder.nameTxt.setText(myList.get(position).getName());

        holder.showTxt.setOnClickListener(new View.OnClickListener() {
            boolean isShowed = false;
            @Override
            public void onClick(View v) {

                if(!isShowed){
                    holder.loginTxt.setBackgroundColor(Color.TRANSPARENT);
                    holder.passTxt.setBackgroundColor(Color.TRANSPARENT);
                    holder.commentTxt.setBackgroundColor(Color.TRANSPARENT);

                    holder.loginTxt.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    holder.passTxt.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    holder.commentTxt.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

                    holder.loginTxt.setAlpha(1.0f);
                    holder.passTxt.setAlpha(1.0f);
                    holder.commentTxt.setAlpha(1.0f);
                    holder.showTxt.setText("Hide");
                }else{
                    holder.loginTxt.setBackgroundResource(R.color.colorPrimaryDark);
                    holder.passTxt.setBackgroundResource(R.color.colorPrimaryDark);
                    holder.commentTxt.setBackgroundResource(R.color.colorPrimaryDark);

                    holder.loginTxt.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                    holder.passTxt.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                    holder.commentTxt.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

                    holder.loginTxt.setAlpha(0.1f);
                    holder.passTxt.setAlpha(0.1f);
                    holder.commentTxt.setAlpha(0.1f);
                    holder.showTxt.setText("Show");
                }

                isShowed = !isShowed;
            }
        });

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView showTxt;
        TextView loginTxt;
        TextView passTxt;
        TextView commentTxt;
        TextView nameTxt;
        public myViewHolder(@NonNull final View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.txt);
            showTxt = itemView.findViewById(R.id.show_textview);
            loginTxt = itemView.findViewById(R.id.login);
            passTxt = itemView.findViewById(R.id.password);
            commentTxt = itemView.findViewById(R.id.comment);


        }
    }
}
