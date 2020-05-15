package ru.altstu.pricechecker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {


    private ArrayList<Product> products;
    private Context context;

    public ProductAdapter(ArrayList<Product> products, Context context){
        this.products = products;
        this.context = context;

    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productNameView.setText(product.getName());
        Picasso.get().load(product.getImgUrl()).into(holder.productImageView);
        holder.productPriceView.setText(product.getPrice());
        holder.productShopView.setText(product.getShop());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImageView;
        TextView productNameView;
        TextView productShopView;
        TextView productPriceView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImage);
            productNameView = itemView.findViewById(R.id.productName);
            productShopView = itemView.findViewById(R.id.productShop);
            productPriceView = itemView.findViewById(R.id.productPrice);
        }
    }
}
