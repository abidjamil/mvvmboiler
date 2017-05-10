package co.appdev.boilerplate.ui.main;

import java.util.List;

import co.appdev.boilerplate.data.model.Users;
import co.appdev.boilerplate.ui.base.MvpView;


public interface MainMvpView extends MvpView {

    void showUsers(List<Users> users);

    void showUsersEmpty();

    void showError();

}
