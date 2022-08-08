package com.example.cityguide.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.cityguide.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.example.cityguide.HelperClasses.HomeAdapter.CategoriesHelperClass;
import com.example.cityguide.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.example.cityguide.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.example.cityguide.HelperClasses.HomeAdapter.MostViewedAdapter;
import com.example.cityguide.HelperClasses.HomeAdapter.MostViewedHelperClass;
import com.example.cityguide.R;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {

    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        featuredRecycler = findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);

        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();
    }

    private void featuredRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.mcdonalds, "McDonald's", "askdjalsjdasd asdlkasdjcnqw asdjk asdljqlerwkqw"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.cafe1, "Cofee Shop", "askdjalsjdasd asdlkasdjcnqw asdjk asdljqlerwkqw"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.cafe2, "Restaurants", "askdjalsjdasd asdlkasdjcnqw asdjk asdljqlerwkqw"));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);

    }

    private void mostViewedRecycler() {
        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewedHelperClass> mostViewed = new ArrayList<>();

        mostViewed.add(new MostViewedHelperClass(R.drawable.mcdonalds, "McDonald's", "askdjalsjdasd asdlkasdjcnqw asdjk asdljqlerwkqw"));
        mostViewed.add(new MostViewedHelperClass(R.drawable.cafe1, "Cofee Shop", "askdjalsjdasd asdlkasdjcnqw asdjk asdljqlerwkqw"));
        mostViewed.add(new MostViewedHelperClass(R.drawable.cafe2, "Restaurants", "askdjalsjdasd asdlkasdjcnqw asdjk asdljqlerwkqw"));

        adapter = new MostViewedAdapter(mostViewed);
        mostViewedRecycler.setAdapter(adapter);

    }

    private void categoriesRecycler() {
        categoriesRecycler.setHasFixedSize(true);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<CategoriesHelperClass> categories = new ArrayList<>();

        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});

        categories.add(new CategoriesHelperClass(gradient1,R.drawable.mcdonalds, "McDonald's"));
        categories.add(new CategoriesHelperClass(gradient2,R.drawable.cafe1, "Cofee Shop"));
        categories.add(new CategoriesHelperClass(gradient3,R.drawable.cafe2, "Restaurants"));
        categories.add(new CategoriesHelperClass(gradient4,R.drawable.transport, "Transport"));
        categories.add(new CategoriesHelperClass(gradient1,R.drawable.education, "Education"));

        adapter = new CategoriesAdapter(categories);
        categoriesRecycler.setAdapter(adapter);



    }
}