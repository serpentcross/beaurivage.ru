package ru.beaurivage.msystem.ui.viewssecured;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import ru.beaurivage.msystem.logic.services.AuthService;
import ru.beaurivage.msystem.ui.VaadinUI;
import ru.beaurivage.msystem.ui.constants.CssStyles;
import ru.beaurivage.msystem.ui.constants.ViewsNaming;

@Theme("beaurivage")
public class SecuredServicesView extends CustomComponent implements View {

    private GridLayout navigationOptionsLayout;

    private Button navRecordsTableBtn;
    private Button navPatientsTableBtn;
    private Button logOutBtn;

    private Label horizontalBar;

    public SecuredServicesView() {

        navRecordsTableBtn = new Button("записи", this::navigateToRecordsOptions);
        navRecordsTableBtn.setWidth(CssStyles.WIDTH_250_PX);

        navPatientsTableBtn = new Button("пациенты", this::navigateToPatientsOptions);
        navPatientsTableBtn.setWidth(CssStyles.WIDTH_250_PX);

        logOutBtn = new Button("выход", this::onLogout);
        logOutBtn.setWidth(CssStyles.WIDTH_250_PX);
        logOutBtn.setStyleName("ml-button-13");

        horizontalBar = new Label();
        horizontalBar.setStyleName("horizontalRule");

        navigationOptionsLayout = new GridLayout(3, 2);
        navigationOptionsLayout.setSpacing(true);
        navigationOptionsLayout.setSizeFull();

        navigationOptionsLayout.addComponent(navRecordsTableBtn,0,0);
        navigationOptionsLayout.addComponent(navPatientsTableBtn,1,0);
        navigationOptionsLayout.addComponent(logOutBtn,2,0);
        navigationOptionsLayout.setComponentAlignment(navRecordsTableBtn, Alignment.TOP_LEFT);
        navigationOptionsLayout.setComponentAlignment(navPatientsTableBtn, Alignment.TOP_CENTER);
        navigationOptionsLayout.setComponentAlignment(logOutBtn, Alignment.TOP_RIGHT);
        navigationOptionsLayout.addComponent(horizontalBar, 0,1,2,1);

        VerticalLayout basicLayout = new VerticalLayout(navigationOptionsLayout);
        basicLayout.setComponentAlignment(navigationOptionsLayout, Alignment.TOP_CENTER);

        basicLayout.setSizeFull();

        setCompositionRoot(basicLayout);
    }

    private void navigateToRecordsOptions(Button.ClickEvent event) {
        VaadinUI.getNavigation().navigateTo(ViewsNaming.PRIVATE_RECORDS_VIEW);
    }

    private void navigateToPatientsOptions(Button.ClickEvent event) {
        VaadinUI.getNavigation().navigateTo(ViewsNaming.PRIVATE_PATIENTS_VIEW);
    }

    private void onLogout(Button.ClickEvent event) {
        VaadinUI.endAllSessions();
        AuthService.logOut();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
