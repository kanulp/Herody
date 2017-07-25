package com.kanu_lp.support;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kanu on 7/15/2017.
 */

public class Home_Fragment extends Fragment {

    Product_Data p1;
    ArrayList<Product_Data> pat = new ArrayList<>();
    private Product_Adapter adapter;
    private RecyclerView recyclerView;
    EditText search;
    String searchtext = null;
    Button searchbutton;
    private static String AFFILIATE_ID = "nitbssgma";
    private static String AFFILIATE_TOKEN = "65f1bded23e746759c95536bf82fde25";

    public Home_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.home_fragment, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        search = (EditText)v.findViewById(R.id.search);
        searchbutton = (Button)v.findViewById(R.id.buttonsearch);

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search.getText().toString().trim().equals("")){
                    Toast.makeText(getActivity(), "plz enter product", Toast.LENGTH_SHORT).show();
                }else{
                    searchtext=search.getText().toString().trim();
                    new callwebservice().execute();
                }
            }
        });


        return  v;
    }


    class callwebservice extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... params) {

            String url="https://affiliate-api.flipkart.net/affiliate/1.0/search.json?query='"+searchtext+"'&resultCount=10";

            try {
                ArrayList<param> parameters= new ArrayList<>();
                parameters.add(new param("Fk-Affiliate-Id",AFFILIATE_ID));
                parameters.add(new param("Fk-Affiliate-Token",AFFILIATE_TOKEN));

                //JSONObject obj =new  geturl().makeHttpRequestpost(url,parameters);
                JSONObject obj = new geturl().makeHttpRequestget(url,parameters);
                pat.clear();
                JSONArray jarray1= obj.getJSONArray("productInfoList");
                for(int i =0;i<jarray1.length();i++)
                {
                    JSONObject obj1 = jarray1.getJSONObject(i);

                    JSONObject productBaseInfoV1 = obj1.getJSONObject("productBaseInfoV1");
                    String product_id= productBaseInfoV1.getString("productId");
                    String product_title= productBaseInfoV1.getString("title");
                    String product_url=productBaseInfoV1.getString("productUrl");
                    JSONObject flipkartSpecialPrice = productBaseInfoV1.getJSONObject("flipkartSpecialPrice");
                    String product_price=flipkartSpecialPrice.getString("amount");

                    JSONObject productShippingInfoV1 = obj1.getJSONObject("productShippingInfoV1");
                    String estimatedDeliveryTime = productShippingInfoV1.getString("estimatedDeliveryTime");


                    p1 = new Product_Data(product_id,product_title,product_price,product_url,estimatedDeliveryTime);
                    pat.add(p1);
                    Log.d("categories",pat.size()+" "+pat);
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("error in","mainfrag");
                return "error";
            }
            return "valid";
        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();
            if(result.equals("error"))
            {
                Toast.makeText(getActivity(), "Error or limit exceeded !", Toast.LENGTH_SHORT).show();

            }
            else
            {
                if(pat.size()<0)
                    Toast.makeText(getActivity(), "No Data !", Toast.LENGTH_SHORT).show();
                else {
                    adapter = new Product_Adapter(pat,getActivity());
                    recyclerView.setAdapter(adapter);

                }
            }
        }
        @Override
        protected void onPreExecute() {
            progressDialog= new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }



}
