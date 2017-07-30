package ru.beaurivage.msystem.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import ru.beaurivage.msystem.logic.services.AuthService;
import ru.beaurivage.msystem.ui.constants.ViewsNaming;
import ru.beaurivage.msystem.ui.viewspublic.PublicMainView;
import ru.beaurivage.msystem.ui.viewssecured.SecuredRecordsView;

@Theme("beaurivage")
public class VaadinUI extends UI {

    private Navigator navigator;
    private PublicMainView publicMainView;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        navigator = new Navigator(this, this);

        publicMainView = new PublicMainView();

        publicMainView.setStyleName("background-main-color");

        navigator.addView(ViewsNaming.PUBLIC_LOGIN_VIEW, publicMainView);
        navigator.addView(ViewsNaming.PRIVATE_RECORDS_VIEW, new SecuredRecordsView());

        if (!AuthService.isAuthenticated()) {
            showPublicComponent();
        } else {
            showPrivateComponent();
        }
    }

    private void showPublicComponent() {
        navigator.navigateTo(ViewsNaming.PUBLIC_LOGIN_VIEW);
    }

    public void showPrivateComponent() {
        navigator.navigateTo(ViewsNaming.PRIVATE_RECORDS_VIEW);
    }

    public Navigator getNavigation() {
        return navigator;
    }
}
