package com.example.filip.movielist.presentation

import com.example.filip.movielist.common.extensions.isValid
import com.example.filip.movielist.view.UsernameChangeView

/**
 * Created by Filip Babic @cobe
 */
class UsernameChangePresenterImpl : UsernameChangePresenter {

    private lateinit var changeUsernameView: UsernameChangeView

    override fun changeUsername(username: String?) {
        if (username.isValid()) {
            changeUsernameView.saveUsername(username!!)
        } else {
            changeUsernameView.showInvalidUsernameError()
        }
    }

    override fun switchViews() {
        changeUsernameView.animateSwitchViews()
    }

    override fun onRequestsCancelled() {
    }

    override fun cancelSubscriptions() {
    }

    override fun setView(view: UsernameChangeView) {
        changeUsernameView = view
    }
}