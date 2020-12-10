package com.example.apirecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView userid,name;
    RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycle=findViewById(R.id.recycle);

        listningfavourite();
        LinearLayoutManager llm=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(llm);
    }

    void listningfavourite()
    {
        ApiInterface apiInterface = Retrofit.getRetrofit().create(ApiInterface.class);
        Call<List<Pojo>> listningfavourite = apiInterface.getPost();
        listningfavourite.enqueue(new Callback<List<Pojo>>() {
            @Override
            public void onResponse(Call<List<Pojo>> call, Response<List<Pojo>> response) {
                    if (response.isSuccessful())
                    {
                        List<Pojo> post=response.body();
                        for (Pojo pojo :post)
                        {
                            recycleradapter adapter=new recycleradapter(response.body());
                            recycle.setAdapter(adapter);
                        }
                    }
                    else {
                        //null
                    }

            }

            @Override
            public void onFailure(Call<List<Pojo>> call, Throwable t) {

            }
        });
    }
    class recycleradapter extends RecyclerView.Adapter<recycleradapter.MyViewHolder>{
        List<Pojo>list;

        public recycleradapter(List<Pojo> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public recycleradapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout,null);
            recycleradapter.MyViewHolder viewHolder=new recycleradapter.MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull recycleradapter.MyViewHolder holder, int position) {
            holder.names.setText(list.get(position).getTitle());
           // holder.ids.setText(list.get(position).getData().get(position).getLastName());

//            Picasso.with(getApplicationContext()).load(list.get(position).getImg())
//                    .placeholder(listitem.get(position).getImg()).into(holder.imgs);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView names,ids;
            ImageView imgs;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                names=itemView.findViewById(R.id.names);
                ids=itemView.findViewById(R.id.ids);
                imgs=itemView.findViewById(R.id.imgs);
            }
        }
    }

}