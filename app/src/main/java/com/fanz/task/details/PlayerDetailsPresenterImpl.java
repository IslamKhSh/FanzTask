package com.fanz.task.details;


public class PlayerDetailsPresenterImpl implements PlayerDetailsPresenter{

    private PlayerDetailsView view;

    public PlayerDetailsPresenterImpl(PlayerDetailsView view) {
        this.view = view;
    }

    @Override
    public void dispatch() {
        view = null;
    }
}
