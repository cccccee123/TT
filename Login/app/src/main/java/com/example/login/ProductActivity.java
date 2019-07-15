package com.example.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {
    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Product, ProductActivity.ViewHolder> mPeopleRVAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        setTitle("Product");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Product");
        mDatabase.keepSynced(true);




        mPeopleRV = (RecyclerView) findViewById(R.id.myRecycleView);
        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("Product");
        Query personsQuery = personsRef.orderByKey();


        mPeopleRV.hasFixedSize();
        mPeopleRV.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Product>().setQuery(personsQuery, Product.class).build();

        mPeopleRV.hasFixedSize();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<Product, ProductActivity.ViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder( ProductActivity.ViewHolder holder, int position,  Product model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.setImage(getBaseContext(), model.getImage());
            }

            @Override
            public ProductActivity.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row, parent, false);

                return new ProductActivity.ViewHolder(view);
            }
        };
    }
        public static class ViewHolder extends RecyclerView.ViewHolder{
            View mView;
            public ViewHolder(View itemView){
                super(itemView);
                mView = itemView;
            }
            public void setTitle(String title){
                TextView post_title = (TextView)mView.findViewById(R.id.post_title);
                post_title.setText(title);
            }
            public void setDesc(String desc){
                TextView post_desc = (TextView)mView.findViewById(R.id.post_desc);
                post_desc.setText(desc);
            }
            public void setImage(Context ctx, String image){
                ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
                Picasso.with(ctx).load(image).into(post_image);
            }
        }


}
