package ru.beaurivage.msystem.ui.viewssecured;

import com.vaadin.annotations.Theme;

import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import com.vaadin.server.Page;

import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;

import ru.beaurivage.msystem.logic.dao.PatientDAO;
import ru.beaurivage.msystem.logic.dao.RecordDAO;
import ru.beaurivage.msystem.logic.dao.ServiceDAO;
import ru.beaurivage.msystem.logic.entities.Patient;
import ru.beaurivage.msystem.logic.entities.Record;
import ru.beaurivage.msystem.logic.entities.Service;
import ru.beaurivage.msystem.logic.enums.CabinetType;
import ru.beaurivage.msystem.logic.services.AuthService;
import ru.beaurivage.msystem.logic.util.EjbUtil;

import ru.beaurivage.msystem.ui.VaadinUI;
import ru.beaurivage.msystem.ui.constants.CssStyles;
import ru.beaurivage.msystem.ui.constants.Notifications;
import ru.beaurivage.msystem.ui.constants.UILegend;
import ru.beaurivage.msystem.ui.constants.ViewsNaming;
import ru.beaurivage.msystem.ui.modals.ConfirmWindow;
import ru.beaurivage.msystem.ui.modals.EditInfoWindow;
import ru.beaurivage.msystem.ui.util.NotificationFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Theme("beaurivage")
public class SecuredRecordsView extends CustomComponent implements View {

    private final PatientDAO patientDAO;
    private final ServiceDAO serviceDAO;
    private final RecordDAO recordDAO;

    private final GridLayout navigationOptionsLayout;
    private final GridLayout newPatientOptionsContainer;

    private final ComboBox<Patient> prevPatientFld;
    private final ComboBox<Service> prevServiceFld;
    private final ComboBox<CabinetType> cabinetSelection;

    private final DateField dateTxtFld;

    private final ComboBox<String> timeFrTxtFld;
    private final ComboBox<String> timeToTxtFld;

    private final Button navPatientsTableBtn;
    private final Button navServicesTableBtn;
    private final Button logOutBtn;
    private final Button previewRecordBtn;

    private final Label horizontalBar;

    private final Button addRecordBtn;
    private final Grid<Record> recordsTable;

    private List<Record> records = new ArrayList<>();

    private EditInfoWindow editInfoWindow;
    private ConfirmWindow confirmWindow;

    private final Binder<Record> recordBinder = new Binder<>();

    private final ArrayList<String> timeOptionList = new ArrayList<>() {{
        add("10:00");
        add("11:00");
        add("12:00");
        add("13:00");
        add("14:00");
        add("15:00");
        add("16:00");
        add("17:00");
        add("18:00");
        add("19:00");
        add("20:00");
        add("21:00");
        add("22:00");
    }};

    public SecuredRecordsView() {

        setStyleName("background-main-color");

        patientDAO = EjbUtil.getLocalBean(PatientDAO.class);
        serviceDAO = EjbUtil.getLocalBean(ServiceDAO.class);
        recordDAO = EjbUtil.getLocalBean(RecordDAO.class);

        prevPatientFld = new ComboBox<>();
        prevPatientFld.setItems(patientDAO.getAll());
        prevPatientFld.setPlaceholder("Поиск ранее записанного пациента");
        prevPatientFld.setWidth(CssStyles.WIDTH_100_PERCENTS);
        prevPatientFld.setEmptySelectionAllowed(true);

        prevServiceFld = new ComboBox<>();
        prevServiceFld.setItems(serviceDAO.getAll());
        prevServiceFld.setPlaceholder("Выбрать услугу");
        prevServiceFld.setWidth(CssStyles.WIDTH_100_PERCENTS);
        prevServiceFld.setEmptySelectionAllowed(false);

        cabinetSelection = new ComboBox<>();
        cabinetSelection.setItems(CabinetType.values());
        cabinetSelection.setPlaceholder("Тип кабинета");
        cabinetSelection.setWidth(CssStyles.WIDTH_100_PERCENTS);
        cabinetSelection.setEmptySelectionAllowed(false);

        prevPatientFld.setItemCaptionGenerator(
            p -> p.getLastName() + " " + p.getFirstName() + " " + p.getMiddleName() + " " + p.getPhone() + " " + p.getEmail()
        );

        horizontalBar = new Label();
        horizontalBar.setStyleName("horizontalRule");

        navPatientsTableBtn = new Button(UILegend.PATIENTS_PAGE, this::navigateToPatientsOptions);
        navPatientsTableBtn.setWidth(CssStyles.WIDTH_250_PX);

        navServicesTableBtn = new Button(UILegend.SERVICES_PAGE, this::navigateToServicesOptions);
        navServicesTableBtn.setWidth(CssStyles.WIDTH_250_PX);

        logOutBtn = new Button(UILegend.LOGOUT_BUTTON, this::onLogout);
        logOutBtn.setWidth(CssStyles.WIDTH_250_PX);
        logOutBtn.setStyleName("ml-button-13");

        navigationOptionsLayout = new GridLayout(3, 2);
        navigationOptionsLayout.setSpacing(true);
        navigationOptionsLayout.setSizeFull();

        navigationOptionsLayout.addComponent(navPatientsTableBtn,0,0);
        navigationOptionsLayout.addComponent(navServicesTableBtn,1,0);
        navigationOptionsLayout.addComponent(logOutBtn,2,0);
        navigationOptionsLayout.setComponentAlignment(navPatientsTableBtn, Alignment.TOP_LEFT);
        navigationOptionsLayout.setComponentAlignment(navServicesTableBtn, Alignment.TOP_CENTER);
        navigationOptionsLayout.setComponentAlignment(logOutBtn, Alignment.TOP_RIGHT);
        navigationOptionsLayout.addComponent(horizontalBar, 0,1,2,1);

        dateTxtFld = new DateField();
        dateTxtFld.setPlaceholder("dd-MM-yyyy");
        dateTxtFld.setWidth(CssStyles.WIDTH_250_PX);
        dateTxtFld.setValue(LocalDate.now());
        dateTxtFld.setDateFormat("dd-MM-yyyy");

        timeFrTxtFld = new ComboBox<>();
        timeFrTxtFld.setItems(timeOptionList);
        timeFrTxtFld.setPlaceholder(UILegend.TXT_FIELD_TIMEBEFORE);
        timeFrTxtFld.setWidth(CssStyles.WIDTH_250_PX);
        timeFrTxtFld.setEmptySelectionAllowed(false);
        timeFrTxtFld.setTextInputAllowed(false);

        timeToTxtFld = new ComboBox<>();
        timeToTxtFld.setItems(timeOptionList);
        timeToTxtFld.setPlaceholder(UILegend.TXT_FIELD_TIMEAFTER);
        timeToTxtFld.setWidth(CssStyles.WIDTH_250_PX);
        timeToTxtFld.setEmptySelectionAllowed(false);
        timeToTxtFld.setTextInputAllowed(false);

        recordBinder.forField(prevPatientFld).asRequired("Выберите пациента!").bind(Record::getPatient, Record::setPatient);
        recordBinder.forField(prevServiceFld).asRequired("Выберите услугу!").bind(Record::getService, Record::setService);
        recordBinder.forField(cabinetSelection).asRequired("Выберите тип кабинета!").bind(Record::getCabinetType, Record::setCabinetType);
        recordBinder.forField(dateTxtFld).asRequired("Укажите дату приёма!").bind(Record::getRecDate, Record::setRecDate);
        recordBinder.forField(timeFrTxtFld).asRequired("Укажите время начала приёма!").bind(Record::getTime_from, Record::setTime_from);
        recordBinder.forField(timeToTxtFld).asRequired("Укажите время окончания приёма!").bind(Record::getTime_to, Record::setTime_to);

        addRecordBtn = new Button("внести новую запись", event -> {
                if (recordBinder.validate().isOk()) {
                    this.createRecord();
                } else {
                    Notification errorNotification = new Notification("Ошибка валидации! Заполните все необходимые поля!", Notification.Type.ERROR_MESSAGE);
                    errorNotification.setDelayMsec(2000);
                    errorNotification.setPosition(Position.TOP_RIGHT);
                    errorNotification.show(Page.getCurrent());
                }
            }
        );
        addRecordBtn.setWidth(CssStyles.WIDTH_100_PERCENTS);
        addRecordBtn.setStyleName(CssStyles.ML_BUTTON_8);

        previewRecordBtn = new Button("", VaadinIcons.SEARCH);

        recordsTable = new Grid<>();
        recordsTable.setWidth(CssStyles.WIDTH_100_PERCENTS);
        recordsTable.getDefaultHeaderRow().setStyleName("header-align-center");
        recordsTable.setSelectionMode(Grid.SelectionMode.SINGLE);

        refreshMainTable();

        newPatientOptionsContainer = new GridLayout(3,6);
        newPatientOptionsContainer.setSpacing(true);
        newPatientOptionsContainer.setSizeFull();
        newPatientOptionsContainer.addComponent(prevPatientFld, 0,0);
        newPatientOptionsContainer.setComponentAlignment(prevPatientFld, Alignment.TOP_LEFT);
        newPatientOptionsContainer.addComponent(prevServiceFld, 1,0);
        newPatientOptionsContainer.setComponentAlignment(prevPatientFld, Alignment.TOP_LEFT);
        newPatientOptionsContainer.addComponent(cabinetSelection, 2,0);
        newPatientOptionsContainer.setComponentAlignment(cabinetSelection, Alignment.TOP_RIGHT);

        newPatientOptionsContainer.addComponent(dateTxtFld, 0, 1);

        newPatientOptionsContainer.addComponent(timeFrTxtFld, 1,1);
        newPatientOptionsContainer.addComponent(timeToTxtFld, 2,1);

        newPatientOptionsContainer.addComponent(addRecordBtn, 0,3,2,3);
        newPatientOptionsContainer.addComponent(recordsTable, 0,5,2,5);

        newPatientOptionsContainer.setComponentAlignment(dateTxtFld, Alignment.TOP_LEFT);
        newPatientOptionsContainer.setComponentAlignment(timeFrTxtFld, Alignment.TOP_CENTER);
        newPatientOptionsContainer.setComponentAlignment(timeToTxtFld, Alignment.TOP_RIGHT);

        newPatientOptionsContainer.setComponentAlignment(addRecordBtn, Alignment.TOP_CENTER);

        VerticalLayout basicLayout = new VerticalLayout(navigationOptionsLayout, newPatientOptionsContainer);
        basicLayout.setComponentAlignment(newPatientOptionsContainer, Alignment.TOP_CENTER);
        basicLayout.setComponentAlignment(navigationOptionsLayout, Alignment.TOP_CENTER);

        basicLayout.setSizeFull();

        setCompositionRoot(basicLayout);
    }

    private void prepareFormForNextRequest() {

        prevPatientFld.setValue(null);
        prevServiceFld.setValue(null);
        cabinetSelection.setValue(null);
        timeFrTxtFld.setValue(null);
        timeToTxtFld.setValue(null);

        refreshMainTable();
    }

    private void createRecord() {

        Patient patient = prevPatientFld.getValue();
        Service service = prevServiceFld.getValue();

        Record record = new Record();

        record.setPatient(patient);
        record.setService(service);
        record.setRecDate(dateTxtFld.getValue());
        record.setTime_from(timeFrTxtFld.getValue());
        record.setTime_to(timeToTxtFld.getValue());
        record.setCabinetType(cabinetSelection.getValue());

        recordDAO.save(record);

        NotificationFactory.constructNotification(Notifications.RECORD_CREATED_SUCCESS, Notification.Type.HUMANIZED_MESSAGE, Page.getCurrent());

        prepareFormForNextRequest();
    }

    private void refreshMainTable() {

        records = recordDAO.getAll();

        var deleteButtonRenderer = new ButtonRenderer(clickEvent -> {
            confirmWindow = new ConfirmWindow((Record) clickEvent.getItem());
            UI.getCurrent().addWindow(confirmWindow);
            confirmWindow.addCloseListener(e -> refreshMainTable());
        });

        var viewButtonRenderer = new ButtonRenderer(clickEvent -> {
            Record selectedRecord = (Record) clickEvent.getItem();
            Notification.show(selectedRecord.getPatient().getFirstName() + " items selected");
        });

        var editButtonRenderer = new ButtonRenderer(clickEvent -> {
            editInfoWindow = new EditInfoWindow((Record) clickEvent.getItem(), timeOptionList);
            UI.getCurrent().addWindow(editInfoWindow);
            editInfoWindow.addCloseListener(e -> refreshMainTable());
        });

        viewButtonRenderer.setHtmlContentAllowed(true);
        deleteButtonRenderer.setHtmlContentAllowed(true);
        editButtonRenderer.setHtmlContentAllowed(true);

        recordsTable.setColumns();
        recordsTable.setItems(records);
        recordsTable.addColumn(Record::getId).setCaption("#").setWidth(60).setId("1");
        recordsTable.addColumn(d-> d.getRecDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).setCaption("Дата").setWidth(150).setId("2");
        recordsTable.addColumn(Record::getPatient).setCaption("Пациент").setId("3");
        recordsTable.addColumn(Record::getService).setCaption("Услуга").setId("4");
        recordsTable.addColumn(Record::getTime_from).setCaption("Начало приёма").setWidth(150).setId("5");
        recordsTable.addColumn(Record::getTime_to).setCaption("Конец приёма").setWidth(150).setId("6");
        recordsTable.addColumn(Record::getCabinetType).setCaption("Тип кабинета").setWidth(200).setId("7");
        recordsTable.addColumn(rec -> VaadinIcons.TRASH.getHtml(), deleteButtonRenderer).setWidth(65);
        recordsTable.addColumn(rec -> VaadinIcons.EDIT.getHtml() , editButtonRenderer).setWidth(65);

        recordsTable.getColumns().forEach(singleColumn -> singleColumn.setStyleGenerator(item -> "v-grid-column-header-content"));

    }

    private void navigateToPatientsOptions(Button.ClickEvent event) {
        VaadinUI.getNavigation().navigateTo(ViewsNaming.PRIVATE_PATIENTS_VIEW);
    }

    private void navigateToServicesOptions(Button.ClickEvent event) {
        VaadinUI.getNavigation().navigateTo(ViewsNaming.PRIVATE_SERVICES_VIEW);
    }

    private void onLogout(Button.ClickEvent event) {
        VaadinUI.endAllSessions();
        AuthService.logOut();
    }

    private void deleteTableRecord(Button.ClickEvent event) {
        System.out.println(event.getSource().toString());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
