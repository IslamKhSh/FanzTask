package com.fanz.task.main;

import com.fanz.task.R;
import com.fanz.task.entity.Player;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.ArrayList;
import java.util.List;

public class MainPresenterImpl implements MainPresenter {
    private MainView view;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    MainPresenterImpl(MainView view) {
        this.view = view;

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
    }

    @Override
    public void getPlayers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("players").get()
                .addOnCompleteListener(task -> {
                    getPlayersScore(task.getResult().getDocuments());
                });
    }

    private void getPlayersScore(List<DocumentSnapshot> players) {
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Player> playersList = new ArrayList<>();

                        players.forEach(item -> {
                            Player player = new Player(
                                    item.getString("name"),
                                    item.getString("position"),
                                    Integer.parseInt(item.getId()),
                                    (int) mFirebaseRemoteConfig.getLong("score" + item.getId())
                                    );

                            playersList.add(player);
                        });

                        if (task.isSuccessful() && view != null) {
                            view.onGetPlayers(playersList);
                        }
                    }
                });

    }


    @Override
    public void dispatch() {
        view = null;
    }
}
