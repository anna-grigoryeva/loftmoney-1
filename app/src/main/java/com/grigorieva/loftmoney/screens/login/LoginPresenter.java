package com.grigorieva.loftmoney.screens.login;

import com.grigorieva.loftmoney.remote.AuthApi;

public interface LoginPresenter {
    void performLogin(AuthApi authApi);
    void attachViewState(LoginView loginView);
    void disposeRequests();
}