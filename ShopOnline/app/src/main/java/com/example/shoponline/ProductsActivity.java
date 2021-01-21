package com.example.shoponline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shoponline.Adpaters.ProductsCustomeAdpater;
import com.example.shoponline.Models.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    ArrayList<Products> products=new ArrayList<Products>();
    //ArrayList<Products> products;
    String product_type;
    String gender_type;
    FirebaseFirestore database;
    ProductsCustomeAdpater adpater;
    ImageButton bagiconBtn;
    ImageButton profileiconBtn;
    ImageButton wishIconBtn;

// test data
   /* public ArrayList<Products> getArrayProducts(){
        ArrayList<Products> product=new ArrayList<>();
        int resID = getResources().getIdentifier("bag" , "drawable", getPackageName());
        product.add(new Products("PI001","AERO","Shirt","$10.00","Shirts","MEN",resID));
        product.add(new Products("PI002","ZARA","Shirt","$15.00","Shirts","MEN",resID));

        return product;
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        database=FirebaseFirestore.getInstance();
        listView=findViewById(R.id.ProdutsListView);
        Bundle bundle = getIntent().getExtras();
        product_type=bundle.getString("selectedItem");
        gender_type=bundle.getString("genderSelected");
        profileiconBtn=findViewById(R.id.profile);
        wishIconBtn=findViewById(R.id.wishlist);
        bagiconBtn=findViewById(R.id.cart);
        profileiconBtn.setOnClickListener(this);
        wishIconBtn.setOnClickListener(this);
        bagiconBtn.setOnClickListener(this);
        getData();
       // products=getArrayProducts();
        adpater=new ProductsCustomeAdpater(products);
        listView.setAdapter(adpater);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),ProductDetailsActivity.class);
                Products selectedItem =  adpater.getItem(position);
                intent.putExtra("selectedItem", selectedItem.getProduct_id().toString());
                //testing
                intent.putExtra("ProductName", selectedItem.getProduct_name().toString());
                intent.putExtra("BrandName", selectedItem.getBrand_name().toString());
                intent.putExtra("Price", selectedItem.getProduct_price().toString());
                startActivity(intent);
            }
        });
    }

    public void getData(){

        database.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document:task.getResult()){
                                Products product=document.toObject(Products.class);
                                if(product.getGender().equals(gender_type) && product.getProduct_cat().equals(product_type))
                                { // "PI00"+product.getImage_file()
                                    int resID = getResources().getIdentifier("sample" , "drawable", getPackageName());
                                    products.add(new Products(product.getProduct_id().toString(),product.getBrand_name().toString(),product.getProduct_name().toString(),product.getProduct_price().toString(),product.getProduct_cat().toString(),product.getGender().toString(),resID));
                                }
                            }
                            listView.setAdapter(adpater);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cart:
                Intent intent=new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
                break;

            case R.id.profile:
                Intent _intent=new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(_intent);
                break;

            case R.id.wishlist:
                Intent wish_intent=new Intent(getApplicationContext(),WishlistActivity.class);
                startActivity(wish_intent);
                break;
        }
    }
}