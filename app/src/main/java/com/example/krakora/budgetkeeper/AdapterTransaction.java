package com.example.krakora.budgetkeeper;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krakora.budgetkeeper.data.TableTracks;

import java.util.Collections;
import java.util.List;

/**
 * Created by KRAKORA on 19.07.2017.
 */

public class AdapterTransaction extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<TableTracks> data= Collections.emptyList();
    TableTracks current;
    int currentPos=0;

    // create constructor to initilize context and data sent from MainActivity
    public AdapterTransaction(Context context, List<TableTracks> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.transaction_list_row_layout, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        TableTracks current=data.get(position);

        // Category image
        myHolder.ivCategoryImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_filter_vintage_black_24px));
        myHolder.textCategory.setText("Category " + current.GenreId);

        // Transaction texts
        myHolder.textTransactionName.setText(current.Name);
        myHolder.textTransactionNotes.setText(
                "2017-07-19 | " +
                        "AlbumID " + current.AlbumId + " | " +
                        "MediaType " + current.MediaTypeId
            );

        myHolder.textPrice.setText(current.UnitPrice + " CZK");
        myHolder.textPrice.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

        // Price presentation
        myHolder.ivTransactionSign.setBackgroundColor(Color.parseColor("#99ee99"));
        myHolder.ivTransactionSign.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_keyboard_arrow_right_black_24px));
        if(current.UnitPrice < 0) {
            myHolder.textPrice.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            myHolder.ivTransactionSign.setBackgroundColor(Color.parseColor("#ee9999"));
            myHolder.ivTransactionSign.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_keyboard_arrow_left_black_24px));
        }

        // Account
        myHolder.textAccount.setText("General");
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<TableTracks> updata) {
        data.clear();
        data.addAll(updata);
        notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView ivCategoryImage;
        TextView textCategory;
        TextView textTransactionName;
        TextView textTransactionNotes;

        ImageView ivTransactionSign;
        TextView textPrice;
        TextView textAccount;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            // Left items
            ivCategoryImage= (ImageView) itemView.findViewById(R.id.ivCategoryImage);
            textCategory= (TextView) itemView.findViewById(R.id.textCategory);
            textTransactionName = (TextView) itemView.findViewById(R.id.textTransactionName);
            textTransactionNotes = (TextView) itemView.findViewById(R.id.textTransactionNotes);
            // Right items
            ivTransactionSign= (ImageView) itemView.findViewById(R.id.ivTransactionSign);
            textPrice = (TextView) itemView.findViewById(R.id.textPrice);
            textAccount = (TextView) itemView.findViewById(R.id.textAccount);
        }

    }

}