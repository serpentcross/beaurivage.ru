package ru.beaurivage.msystem.ui.viewssecured;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

import com.vaadin.ui.renderers.ButtonRenderer;
import ru.beaurivage.msystem.logic.dao.ServiceDAO;
import ru.beaurivage.msystem.logic.entities.Service;
import ru.beaurivage.msystem.logic.services.AuthService;
import ru.beaurivage.msystem.logic.util.EjbUtil;

import ru.beaurivage.msystem.ui.VaadinUI;
import ru.beaurivage.msystem.ui.constants.CssStyles;
import ru.beaurivage.msystem.ui.constants.Notifications;
import ru.beaurivage.msystem.ui.constants.UILegend;
import ru.beaurivage.msystem.ui.constants.ViewsNaming;
import ru.beaurivage.msystem.ui.modals.ConfirmWindow;
import ru.beaurivage.msystem.ui.modals.EditInfoWindow;

import java.util.ArrayList;
import java.util.List;

@Theme("beaurivage")
public class SecuredServicesView extends CustomComponent implements View {

    private ServiceDAO serviceDAO;

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
    private Grid<Service> servicesTable;

    private List<Service> services = new ArrayList<>();

    private EditInfoWindow editInfoWindow;
    private ConfirmWindow confirmWindow;

    private Binder<Service> serviceBinder = new Binder<>();

    public SecuredServicesView() {

        setStyleName("background-main-color");

        serviceDAO = EjbUtil.getLocalBean(ServiceDAO.class);

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

        serviceBinder.forField(srvNameFld).withValidator(name -> name.length() >= 3, "Название услуги должно содержать хотя бы 3 буквы!").bind(Service::getName, Service::setName);
        serviceBinder.forField(srvPriceFld).withConverter(new StringToIntegerConverter("Введите цену цифрами!")).bind(Service::getPrice, Service::setPrice);
        serviceBinder.forField(srvDescFld).withValidator(surname -> surname.length() >= 3, "Описание услуги должно содержать хотя бы 3 буквы!").bind(Service::getDescription, Service::setDescription);

        addServiceBtn = new Button("внести новую услугу", event -> {
                if (serviceBinder.validate().isOk()) {
                    this.createService();
                } else {
                    Notification errorNotification = new Notification("Ошибка валидации! Заполните все необходимые поля!", Notification.Type.ERROR_MESSAGE);
                    errorNotification.setDelayMsec(2000);
                    errorNotification.setPosition(Position.TOP_RIGHT);
                    errorNotification.show(Page.getCurrent());
                }
            }
        );

        addServiceBtn.setWidth(CssStyles.WIDTH_100_PERCENTS);
        addServiceBtn.setStyleName(CssStyles.ML_BUTTON_8);

        servicesTable = new Grid<>();
        servicesTable.setWidth(CssStyles.WIDTH_100_PERCENTS);
        servicesTable.getDefaultHeaderRow().setStyleName("header-align-center");
        servicesTable.setSelectionMode(Grid.SelectionMode.SINGLE);

        refreshMainTable();

        newServiceOptionsContainer = new GridLayout(3, 4);
        newServiceOptionsContainer.setSpacing(true);
        newServiceOptionsContainer.setSizeFull();
        newServiceOptionsContainer.addComponent(srvNameFld, 0,0,1,0);
        newServiceOptionsContainer.addComponent(srvPriceFld,2,0);
        newServiceOptionsContainer.addComponent(srvDescFld,0,1,2,1);
        newServiceOptionsContainer.addComponent(addServiceBtn, 0, 2,2,2);
        newServiceOptionsContainer.addComponent(servicesTable,0,3,2,3);
        newServiceOptionsContainer.setComponentAlignment(srvNameFld, Alignment.TOP_LEFT);
        newServiceOptionsContainer.setComponentAlignment(srvPriceFld, Alignment.TOP_RIGHT);

        VerticalLayout basicLayout = new VerticalLayout(navigationOptionsLayout, newServiceOptionsContainer);
        basicLayout.setComponentAlignment(navigationOptionsLayout, Alignment.TOP_CENTER);
        basicLayout.setComponentAlignment(newServiceOptionsContainer, Alignment.TOP_CENTER);

        basicLayout.setSizeFull();

        setCompositionRoot(basicLayout);
    }

    private void createService() {

        Service service = new Service();

        service.setName(srvNameFld.getValue());
        service.setPrice(Integer.parseInt(srvPriceFld.getValue()));
        service.setDescription(srvDescFld.getValue());

        serviceDAO.save(service);

        Notification notif = new Notification(Notifications.RECORD_CREATED_SUCCESS, Notification.Type.HUMANIZED_MESSAGE);
        notif.setDelayMsec(2000);
        notif.setPosition(Position.TOP_RIGHT);
        notif.show(Page.getCurrent());

        prepareFormForNextRequest(service);
    }

    private void prepareFormForNextRequest(Service service) {
        srvNameFld.clear();
        srvPriceFld.clear();
        srvDescFld.clear();
    }

    private void refreshMainTable() {

        services = serviceDAO.getAll();

        ButtonRenderer viewButtonRenderer = new ButtonRenderer(clickEvent -> {
            Service selectedService = (Service) clickEvent.getItem();
            Notification.show(selectedService.getName() + " items selected");
        });

        viewButtonRenderer.setHtmlContentAllowed(true);

        servicesTable.setColumns();
        servicesTable.setItems(services);
        servicesTable.addColumn(Service::getId).setCaption("#").setWidth(60).setId("1");
        servicesTable.addColumn(Service::getName).setCaption("Наименование").setWidth(400).setId("2");
        servicesTable.addColumn(Service::getDescription).setCaption("Описание").setId("3");
        servicesTable.addColumn(Service::getPrice).setCaption("Цена").setWidth(150).setId("5");

        for (Grid.Column singleColumn : servicesTable.getColumns()) {
            singleColumn.setStyleGenerator(item -> "v-grid-column-header-content");
        }
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
