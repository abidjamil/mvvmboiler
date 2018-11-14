package co.appdev.boilerplate.ui.main;

import java.util.List;

import co.appdev.boilerplate.data.model.User;
import co.appdev.boilerplate.ui.base.MvpView;


public interface MainMvpView extends MvpView {

    void showUsers(List<User> users);

    void showUsersEmpty();

    void showError();

}
