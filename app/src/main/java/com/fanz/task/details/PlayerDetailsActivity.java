package com.fanz.task.details;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fanz.task.R;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

public class PlayerDetailsActivity extends AppCompatActivity implements PlayerDetailsView {

    private PlayerDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);
        presenter = new PlayerDetailsPresenterImpl(this);
        initDynamicLink();
        if (getIntent().hasExtra("player")) {
            initUi(getIntent().getStringExtra("player"));
        }
    }

    private void initUi(String player) {
        TextView name = findViewById(R.id.tv_player_name);
        ImageView card = findViewById(R.id.player_card);


        card.setImageResource(player.equals("salah") ? R.drawable.salah_card : R.drawable.cr7_card);
        name.setText(player.equals("salah") ? "Mohamed Salah" : "Ronaldo");
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

                    initUi(deepLink.getLastPathSegment());
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispatch();
    }
}