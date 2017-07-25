package com.kanu_lp.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kanu on 6/16/2017.
 */

public class Product_Adapter extends RecyclerView.Adapter<Product_Adapter.ViewHolder> {

    private ArrayList<Product_Data> paydata;
    Context context;

    public Product_Adapter(ArrayList<Product_Data> paydata,Context context) {
        this.paydata = paydata;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.constraint_layout_demo, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Product_Data item = paydata.get(position);

        holder.protitle.setText(paydata.get(position).getProduct_title());
        holder.amount.setText(paydata.get(position).getProduct_price());
        holder.delivery.setText(paydata.get(position).getProduct_delivery_time());


        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri webpage = Uri.parse(item.getProduct_url());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return paydata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView protitle,amount,delivery;
        Button buy;

        public ViewHolder(View view) {
            super(view);

            delivery = (TextView) view.findViewById(R.id.delivery);
            protitle = (TextView)view.findViewById(R.id.protitle);
            amount = (TextView)view.findViewById(R.id.amount);
            buy = (Button)view.findViewById(R.id.buy);

        }
    }
}
