package ru.beaurivage.msystem.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import ru.beaurivage.msystem.logic.services.AuthService;
import ru.beaurivage.msystem.ui.constants.ViewsNaming;
import ru.beaurivage.msystem.ui.viewspublic.PublicErrorView;
import ru.beaurivage.msystem.ui.viewspublic.PublicMainView;
import ru.beaurivage.msystem.ui.viewssecured.SecuredPatientsView;
import ru.beaurivage.msystem.ui.viewssecured.SecuredRecordsView;
import ru.beaurivage.msystem.ui.viewssecured.SecuredServicesView;

@Theme("beaurivage")
public class VaadinUI extends UI {

    private static Navigator navigator;
    private PublicMainView publicMainView;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        publicMainView = new PublicMainView();
        publicMainView.setStyleName("background-main-color");

        navigator = new Navigator(this, this);
        navigator.addView(ViewsNaming.PUBLIC_LOGIN_VIEW, publicMainView);
        navigator.setErrorView(new PublicErrorView());

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
        navigator.addView(ViewsNaming.PRIVATE_RECORDS_VIEW, new SecuredRecordsView());
        navigator.addView(ViewsNaming.PRIVATE_PATIENTS_VIEW, new SecuredPatientsView());
        navigator.addView(ViewsNaming.PRIVATE_SERVICES_VIEW, new SecuredServicesView());
        navigator.navigateTo(ViewsNaming.PRIVATE_RECORDS_VIEW);
    }

    public static Navigator getNavigation() {
        return navigator;
    }

    public static void endAllSessions() {
        navigator.removeView(ViewsNaming.PRIVATE_RECORDS_VIEW);
        navigator.removeView(ViewsNaming.PRIVATE_PATIENTS_VIEW);
        navigator.removeView(ViewsNaming.PRIVATE_SERVICES_VIEW);
    }
}
