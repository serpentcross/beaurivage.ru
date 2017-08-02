package ru.beaurivage.msystem.ui.viewssecured;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.beaurivage.msystem.logic.services.AuthService;
import ru.beaurivage.msystem.ui.VaadinUI;
import ru.beaurivage.msystem.ui.constants.CssStyles;
import ru.beaurivage.msystem.ui.constants.UILegend;
import ru.beaurivage.msystem.ui.constants.ViewsNaming;

@Theme("beaurivage")
public class SecuredServicesView extends CustomComponent implements View {

    private GridLayout navigationOptionsLayout;
    private GridLayout newServiceOptionsContainer;

    private Button navRecordsTableBtn;
    private Button navPatientsTableBtn;
    private Button logOutBtn;

    private Label horizontalBar;

    private TextField srvNameFld;
    private TextField srvPriceFld;

    private TextArea srvDescFld;

    private Button addServiceBtn;

    public SecuredServicesView() {

        setStyleName("background-main-color");

        navPatientsTableBtn = new Button(UILegend.PATIENTS_PAGE, this::navigateToPatientsOptions);
        navPatientsTableBtn.setWidth(CssStyles.WIDTH_250_PX);

        navRecordsTableBtn = new Button(UILegend.RECORDS_PAGE, this::navigateToRecordsOptions);
        navRecordsTableBtn.setWidth(CssStyles.WIDTH_250_PX);

        logOutBtn = new Button(UILegend.LOGOUT_BUTTON, this::onLogout);
        logOutBtn.setWidth(CssStyles.WIDTH_250_PX);
        logOutBtn.setStyleName("ml-button-13");

        horizontalBar = new Label();
        horizontalBar.setStyleName("horizontalRule");

        navigationOptionsLayout = new GridLayout(3, 2);
        navigationOptionsLayout.setSpacing(true);
        navigationOptionsLayout.setSizeFull();

        navigationOptionsLayout.addComponent(navPatientsTableBtn,0,0);
        navigationOptionsLayout.addComponent(navRecordsTableBtn,1,0);
        navigationOptionsLayout.addComponent(logOutBtn,2,0);
        navigationOptionsLayout.setComponentAlignment(navPatientsTableBtn, Alignment.TOP_LEFT);
        navigationOptionsLayout.setComponentAlignment(navRecordsTableBtn, Alignment.TOP_CENTER);
        navigationOptionsLayout.setComponentAlignment(logOutBtn, Alignment.TOP_RIGHT);
        navigationOptionsLayout.addComponent(horizontalBar, 0,1,2,1);

        srvNameFld = new TextField();
        srvNameFld.setPlaceholder(UILegend.TXT_FIELD_SRV_NAME);
        srvNameFld.setWidth(CssStyles.WIDTH_100_PERCENTS);

        srvPriceFld = new TextField();
        srvPriceFld.setPlaceholder(UILegend.TXT_FIELD_SRV_PRICE);
        srvPriceFld.setWidth(CssStyles.WIDTH_100_PERCENTS);

        srvDescFld = new TextArea();
        srvDescFld.setPlaceholder(UILegend.TXT_AREA_SRV_DESC);
        srvDescFld.setWidth(CssStyles.WIDTH_100_PERCENTS);

        addServiceBtn = new Button("добавить новую услугу");
        addServiceBtn.setWidth(CssStyles.WIDTH_100_PERCENTS);
        addServiceBtn.setStyleName(CssStyles.ML_BUTTON_8);

        newServiceOptionsContainer = new GridLayout(3, 3);
        newServiceOptionsContainer.addComponent(srvNameFld, 0,0,1,0);
        newServiceOptionsContainer.addComponent(srvPriceFld,2,0);
        newServiceOptionsContainer.addComponent(srvDescFld,0,1,2,1);
        newServiceOptionsContainer.addComponent(addServiceBtn, 0, 2,2,2);
        newServiceOptionsContainer.setComponentAlignment(srvNameFld, Alignment.TOP_LEFT);
        newServiceOptionsContainer.setComponentAlignment(srvPriceFld, Alignment.TOP_RIGHT);
        newServiceOptionsContainer.setSpacing(true);
        newServiceOptionsContainer.setSizeFull();

        VerticalLayout basicLayout = new VerticalLayout(navigationOptionsLayout, newServiceOptionsContainer);
        basicLayout.setComponentAlignment(navigationOptionsLayout, Alignment.TOP_CENTER);
        basicLayout.setComponentAlignment(newServiceOptionsContainer, Alignment.TOP_CENTER);

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
