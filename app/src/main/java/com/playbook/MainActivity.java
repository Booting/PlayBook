package com.playbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.playbook.SupportClass.Config;
import com.playbook.SupportClass.SpacesItemDecoration;
import com.playbook.SupportClass.TypeFaceSpan;
import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rcBookList;
    private PlaybookRecyclerAdapter playbookRecyclerAdapter;
    private RequestQueue queue;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        queue    	= Volley.newRequestQueue(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rcBookList  = (RecyclerView) findViewById(R.id.rcBookList);

        getListPlaybook();
    }

    private void initToolbar() {
        SpannableString spanToolbar = new SpannableString("PLAYBOOK");
        spanToolbar.setSpan(new TypeFaceSpan(MainActivity.this, "Lato-Bold"), 0, spanToolbar.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //Initiate Toolbar/ActionBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(spanToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getListPlaybook() {
        String url = Config.URL_MAIN + "getPustaka.php";
        JsonArrayRequest jsArrRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jArrResponse) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 3);
                rcBookList.setLayoutManager(gridLayoutManager);
                playbookRecyclerAdapter = new PlaybookRecyclerAdapter(MainActivity.this, jArrResponse);
                rcBookList.addItemDecoration(new SpacesItemDecoration(3, getResources().getDimensionPixelSize(R.dimen.space), false));
                rcBookList.setAdapter(playbookRecyclerAdapter);

                rcBookList.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsArrRequest);
    }
}
