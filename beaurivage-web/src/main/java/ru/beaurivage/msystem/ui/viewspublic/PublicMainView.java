package ru.beaurivage.msystem.ui.viewspublic;

import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import ru.beaurivage.msystem.logic.services.AuthService;
import ru.beaurivage.msystem.ui.VaadinUI;
import ru.beaurivage.msystem.ui.constants.CssStyles;
import ru.beaurivage.msystem.ui.constants.Notifications;
import ru.beaurivage.msystem.ui.constants.UILegend;

import java.io.File;

public class PublicMainView extends CustomComponent implements View {

    public PublicMainView() {

        setWidth(CssStyles.WIDTH_100_PERCENTS);

        TextField usernameField = new TextField();
        usernameField.setPlaceholder(UILegend.UNAME_TEXTBOX);
        usernameField.focus();

        PasswordField passwordField = new PasswordField();
        passwordField.setPlaceholder(UILegend.PWORD_TEXTBOX);

        CheckBox rememberMe = new CheckBox(UILegend.REMME_CHKBOX);

        Button button = new Button(UILegend.LOGIN_BUTTON, e -> onLogin(usernameField.getValue(), passwordField.getValue(), rememberMe.getValue()));
        button.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        button.setSizeFull();
        button.setWidth("185px");

        FormLayout authFormLayout = new FormLayout(usernameField, passwordField, button, rememberMe);
        authFormLayout.setSizeUndefined();

        GridLayout mainLogoLayout = new GridLayout();

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/brlogo.png"));
        Image beaurivageLogo = new Image(null, resource);

        mainLogoLayout.addComponent(beaurivageLogo,0,0);
        mainLogoLayout.setComponentAlignment(beaurivageLogo, Alignment.BOTTOM_CENTER);

        VerticalLayout basicLayout = new VerticalLayout(mainLogoLayout, authFormLayout);

        basicLayout.setSizeFull();
        basicLayout.setComponentAlignment(mainLogoLayout, Alignment.BOTTOM_CENTER);
        basicLayout.setComponentAlignment(authFormLayout, Alignment.TOP_CENTER);

        setCompositionRoot(basicLayout);
        setSizeFull();

    }

    private void onLogin(String username, String password, boolean rememberMe) {
        if (AuthService.login(username, password, rememberMe)) {
            VaadinUI ui = (VaadinUI) UI.getCurrent();
            ui.showPrivateComponent();
        } else {
            Notification.show(Notifications.INVALID_CREDENTIALS, Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        System.out.println("Was Entered!");
    }
}
