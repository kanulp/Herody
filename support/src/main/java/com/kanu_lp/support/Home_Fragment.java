package com.kanu_lp.support;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kanu on 7/15/2017.
 */

public class Home_Fragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {

    Product_Data p1;
    ArrayList<Product_Data> pat = new ArrayList<>();
    private Product_Adapter adapter;
    private RecyclerView recyclerView;
    EditText search;
    String searchtext = null;
    Button searchbutton;
    private static String AFFILIATE_ID = "nitbssgma";
    private static String AFFILIATE_TOKEN = "65f1bded23e746759c95536bf82fde25";
    public static final int OPERATION_SEARCH_LOADER = 22;
    public static final String OPERATION_QUERY_URL_EXTRA = "query";
    ProgressDialog progressDialog;
    String datad = "";
    String url="";
    RelativeLayout ln;
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

        ln = (RelativeLayout)v.findViewById(R.id.ln);

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search.getText().toString().trim().equals("")){
                    Toast.makeText(getActivity(), "plz enter product", Toast.LENGTH_SHORT).show();
                }else{
                        searchtext = search.getText().toString().trim();
                         url = "https://affiliate-api.flipkart.net/affiliate/1.0/search.json?query='" + searchtext + "'&resultCount=10";
                        runtask();
                        //new callwebservice().execute();
                    }

            }
        });

        return  v;
    }
    private void makeOperationSearchQuery(String url) {

        Bundle queryBundle = new Bundle();
        queryBundle.putString(OPERATION_QUERY_URL_EXTRA,url);
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(OPERATION_SEARCH_LOADER);
        loaderManager.restartLoader(OPERATION_SEARCH_LOADER, queryBundle, this);

        Log.d("whereami3","loader restart");
    }




    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
          datad = "empty";
        return new AsyncTaskLoader<String>(getActivity()) {
            @Override
            public String loadInBackground() {
                String url = args.getString(OPERATION_QUERY_URL_EXTRA);

                Log.d("whereami2",datad+" doin");


                if (url!=null&&"".equals(url)) {
                    return null;
                }

                try {
                    ArrayList<param> parameters= new ArrayList<>();
                    parameters.add(new param("Fk-Affiliate-Id",AFFILIATE_ID));
                    parameters.add(new param("Fk-Affiliate-Token",AFFILIATE_TOKEN));
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
                        Log.d("whereami","in doinback");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("error in","mainfrag");
                    return "error";
                }
                return "valid";


            }

            @Override
            protected void onStartLoading() {
              if(datad.equals("empty")) {
                  progressDialog = new ProgressDialog(getActivity());
                  progressDialog.setMessage("Loading");
                  progressDialog.setCanceledOnTouchOutside(false);
                  progressDialog.show();
                  forceLoad();
                  Log.d("whereami", "onstart");
              }else {
                  deliverResult(datad);
                  Log.d("whereami","onstart "+datad);

              }
            }


            @Override
            public void deliverResult(String data) {
                datad = data;
                super.deliverResult(data);
                Log.d("whereami3","loader deliver "+datad);

            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {


        progressDialog.dismiss();
        if(data.equals("error")||data.equals(""))
        {
            Snackbar snackbar = Snackbar
                .make(ln, "Connection Tme Out !", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(ln, "Retrying ...", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                        runtask();
                    }
                });
            snackbar.show();
            Toast.makeText(getActivity(), "Make sure you have active internet !", Toast.LENGTH_SHORT).show();

        }
        else {
            if (pat.size() < 0){

                Toast.makeText(getActivity(), "No Data try searching something else!", Toast.LENGTH_SHORT).show();
            }
            else {
                adapter = new Product_Adapter(pat,getActivity());
                recyclerView.setAdapter(adapter);
                Log.d("whereami","onfinish");

            }
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        Log.d("whereami3","loader reset");

    }
    public boolean isConnectedToInternet(){
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager connectivity = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] netinfo = connectivity.getAllNetworkInfo();
            if (netinfo != null) {
                for (NetworkInfo ni : netinfo) {
                    if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                        if (ni.isConnected())
                            haveConnectedWifi = true;
                    if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                        if (ni.isConnected())
                            haveConnectedMobile = true;
                }
            }
            return haveConnectedWifi || haveConnectedMobile;
        }
        return false;
    }
    public void runtask() {
        if (isConnectedToInternet())
            makeOperationSearchQuery(url);
        else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setTitle("No Internet");
            builder1.setMessage("Please turn on Internet ");
            builder1.setCancelable(false);

            builder1.setNegativeButton(
                    "Retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            runtask();
                        }
                    });
            builder1.setPositiveButton(
                    "Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            System.exit(0);
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    }

/*

    class callwebservice extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... params) {

            String url="https://affiliate-api.flipkart.net/affiliate/1.0/search.json?query='"+searchtext+"'&resultCount=10";

            try {
                ArrayList<param> parameters= new ArrayList<>();
                parameters.add(new param("Fk-Affiliate-Id",AFFILIATE_ID));
                parameters.add(new param("Fk-Affiliate-Token",AFFILIATE_TOKEN));
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
*/



