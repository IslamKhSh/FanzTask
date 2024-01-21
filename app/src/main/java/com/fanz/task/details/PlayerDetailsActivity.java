package com.fanz.task.details;

import android.net.Uri;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.fanz.task.R;
import com.fanz.task.Rotate3dAnimation;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

public class PlayerDetailsActivity extends AppCompatActivity implements PlayerDetailsView {

    private PlayerDetailsPresenter presenter;
    private MotionLayout motionLayout;
    private ImageView card;

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
        card = findViewById(R.id.player_card);
        motionLayout = findViewById(R.id.motionLayout);

        card.setImageResource(player.equals("salah") ? R.drawable.salah_card : R.drawable.cr7_card);
        name.setText(player.equals("salah") ? "Mohamed Salah" : "Ronaldo");

        apply3dRotationOnCard();
    }

    private void apply3dRotationOnCard() {
        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                final float centerX = card.getX() + card.getWidth() / 2.0f;
                final float centerY = card.getY() + card.getHeight() / 2.0f;
                Rotate3dAnimation animation;
                if (currentId == R.id.end) {
                    animation = new Rotate3dAnimation(0, 45, centerX, centerY, 50, false);
                } else {
                    animation = new Rotate3dAnimation(0, 0, centerX, centerY, 0, false);
                }
                animation.setFillAfter(true);
                animation.setInterpolator(new AccelerateInterpolator());
                card.startAnimation(animation);
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

            }
        });
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