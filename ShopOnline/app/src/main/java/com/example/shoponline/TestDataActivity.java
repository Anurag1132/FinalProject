package com.example.shoponline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoponline.Models.Category;
import com.example.shoponline.Models.Products;
import com.example.shoponline.R_Models.CreateWishlist;
import com.example.shoponline.api_interface.JsonPlaceHolder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestDataActivity extends AppCompatActivity implements View.OnClickListener {

    Button addTestDatabtn;
    FirebaseFirestore database;

    EditText productId;
    EditText userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_data);
        addTestDatabtn=findViewById(R.id.addTestData);

        productId=findViewById(R.id.product_id);
        userName=findViewById(R.id.user_name);

        addTestDatabtn.setOnClickListener(this);
        database=FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {

        Toast.makeText(getApplicationContext(),userName.getText().toString()+""+productId.getText().toString(),Toast.LENGTH_LONG).show();
        //retrofit
        create_wishlist(userName.getText().toString(),productId.getText().toString());



        //testdata to add products
       /*addProduct("PI0001","Reebok","Short","$12.00","Shorts","MEN",1);*/





    }
//retrofit
    public void create_wishlist(String user_name, String product_id){

        CreateWishlist wish_list=new CreateWishlist(product_id,user_name);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.2.18:3060/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolder JsonHolder=retrofit.create(JsonPlaceHolder.class);
        Call<CreateWishlist> call=JsonHolder.SendWishlist(wish_list);

        call.enqueue(new Callback<CreateWishlist>() {
            @Override
            public void onResponse(Call<CreateWishlist> call, Response<CreateWishlist> response) {
                if(!response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),"Code :"+response.code(),Toast.LENGTH_LONG).show();
                    return;
                }

                CreateWishlist cart1= response.body();

                String content="";
                content+="Response Code: "+response.code()+"\n";
                content+="id: "+cart1.getId()+"\n";
                content+="productid: "+cart1.getProduct_id()+"\n";
                content+="username: "+cart1.getUser_name()+"\n";
                Toast.makeText(getApplicationContext(),content,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<CreateWishlist> call, Throwable t) {

                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    public void addCategory(String name){
        Category new_cat=new Category(name);
        database.collection("category")
                .add(new_cat)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Category Submitted",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error. Try Again"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void addProduct(String product_id,String brand,String product,String price,String product_cat,String gender,int imgid){
        Products new_cat=new Products( product_id, brand, product, price, product_cat, gender, imgid);
        database.collection("products")
                .add(new_cat)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"product Submitted",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error. Try Again"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }
}