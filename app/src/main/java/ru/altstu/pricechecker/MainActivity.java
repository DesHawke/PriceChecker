package ru.altstu.pricechecker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ArrayList<Product> productList = new ArrayList<>();

    public static final String baseURL1 = "https://priceguard.ru/search?q=";
    public static final String baseURL2 = "&t=isbn,barcode,egk";
    public static String query;
    private Button btn;

    static final private int BACK_INFO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn = (Button) findViewById(R.id.btn);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(productAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivityForResult(intent, BACK_INFO);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BACK_INFO) {
            if (resultCode == RESULT_OK) {
                query = data.getStringExtra("barcode");
                Content content = new Content();
                content.execute();
            }
        }
    }

    private class Content extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            productAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String URL = baseURL1 + query + baseURL2;
                productList.clear();

                Document doc = Jsoup.connect(URL).get();

                Elements data = doc.select("div.or-item");
                int size = data.size();
                if (data == null || data.size() == 0) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Товаров с таким штрихкодом не найдено", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    for (int i = 0; i < size; i++) {
                        String imgUrl = data.select("td.or-image-block")
                                .select("img.or-image")
                                .eq(i)
                                .attr("src");

                        String name = data.select("td.or-main-block")
                                .select("div.or-title.mr5")
                                .eq(i)
                                .text();

                        String shop = data.select("td.or-main-block")
                                .select("div.or-breadcrumb")
                                .eq(i).select("span").first()
                                .text();
                        String price = data.select("td.or-price-block")
                                .select("div.or-price.nw")
                                .eq(i)
                                .text();

                        Log.d("imgUrl", imgUrl);
                        productList.add(new Product(name, imgUrl, shop, price + " р."));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
