package Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Domain.UrlConfig;
import ModelClass.CryptoListModelClass;
import ModelClass.WalletModelClass;
import ws.wolfsoft.cryptostar.CreatePinAcitivyt;
import ws.wolfsoft.cryptostar.DetailedSnowActivity;
import ws.wolfsoft.cryptostar.DetailedView;
import ws.wolfsoft.cryptostar.LoginActivity;
import ws.wolfsoft.cryptostar.R;
import ws.wolfsoft.cryptostar.SignupActivity;
import ws.wolfsoft.cryptostar.SplashScreenActivity;
import ws.wolfsoft.cryptostar.TradeCryptoStarActivity;
import ws.wolfsoft.cryptostar.WalletCryptoStarActivity;


public class WalletRecycleAdapter extends RecyclerView.Adapter<WalletRecycleAdapter.MyViewHolder> {

    Context context;


    private List<WalletModelClass> OfferList;


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView title,price;



        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            price = (TextView) view.findViewById(R.id.price);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) title.getTag();
                    UrlConfig urlConfig = OfferList.get(position).getUrlConfig();
                    urlConfig.Count = Integer.parseInt(price.getText().toString());
                    Intent intent = new Intent(context, DetailedSnowActivity.class);
                    intent.putExtra("urlConfig", (Parcelable) urlConfig);
                    context.startActivity(intent);
                    Log.i("response",urlConfig.toString()+"");

                }
            });

        }

    }


    public WalletRecycleAdapter(Context context, List<WalletModelClass> offerList) {
        this.OfferList = offerList;
        this.context = context;
    }



    @Override
    public WalletRecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wallet_list, parent, false);


        return new WalletRecycleAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        WalletModelClass lists = OfferList.get(position);
        holder.title.setText(lists.getTitle());
        holder.price.setText(lists.getPrice());
        holder.title.setTag(lists.getValue());
        holder.price.setTag(lists.getUrlConfig());

    }


    @Override
    public int getItemCount() {
        return OfferList.size();

    }

}


