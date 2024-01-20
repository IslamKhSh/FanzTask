package com.fanz.task.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fanz.task.R;
import com.fanz.task.details.PlayerDetailsActivity;
import com.fanz.task.entity.Player;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;
    private final int[] plan = {4, 3, 3};

    private RecyclerView playersRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();
        initDynamicLink();
        presenter = new MainPresenterImpl(this);
        presenter.getPlayers();
    }

    private void initDynamicLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, pendingDynamicLinkData -> {
                    Uri deepLink = null;
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.getLink();
                    } else
                        return;

                    if (Objects.equals(deepLink.getLastPathSegment(), "salah")) {
                        onPlayerClicked(0);
                    } else if (Objects.equals(deepLink.getLastPathSegment(), "cr7")) {
                        onPlayerClicked(1);
                    }
                });
    }

    @Override
    public void onPlayerClicked(int index) {
        Intent intent = new Intent(this, PlayerDetailsActivity.class);
        intent.putExtra("player", index % 2 == 0 ? "salah" : "cr7");
        startActivity(intent);
    }

    private void initUi() {
        playersRecycler = findViewById(R.id.players_recycler);

        int spanCount = 1;

        for (int item : plan) {
            spanCount *= item;
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        int finalSpanCount = spanCount;
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return finalSpanCount;
                } else if (position >= 1 && position <= plan[0]) {
                    return finalSpanCount / plan[0];
                } else if (position >= plan[0] + 1 && position <= plan[0] + plan[1]) {
                    return finalSpanCount / plan[1];
                } else {
                    return finalSpanCount / plan[2];
                }
            }
        });

        playersRecycler.setLayoutManager(layoutManager);
        playersRecycler.addItemDecoration(new PlayerItemDecoration(plan));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispatch();
    }

    @Override
    public void onGetPlayers(ArrayList<Player> players) {
        PlayersAdapter playersAdapter = new PlayersAdapter(players, this);
        playersRecycler.setAdapter(playersAdapter);
    }
}