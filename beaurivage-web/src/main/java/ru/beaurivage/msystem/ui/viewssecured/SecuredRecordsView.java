package ru.beaurivage.msystem.ui.viewssecured;

import com.vaadin.annotations.Theme;
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
import com.vaadin.ui.VerticalLayout;

import ru.beaurivage.msystem.logic.dao.PatientDAO;
import ru.beaurivage.msystem.logic.dao.RecordDAO;
import ru.beaurivage.msystem.logic.entities.Patient;
import ru.beaurivage.msystem.logic.entities.Record;
import ru.beaurivage.msystem.logic.services.AuthService;
import ru.beaurivage.msystem.logic.util.EjbUtil;

import ru.beaurivage.msystem.ui.VaadinUI;
import ru.beaurivage.msystem.ui.constants.CssStyles;
import ru.beaurivage.msystem.ui.constants.Notifications;
import ru.beaurivage.msystem.ui.constants.UILegend;
import ru.beaurivage.msystem.ui.constants.ViewsNaming;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Theme("beaurivage")
public class SecuredRecordsView extends CustomComponent implements View {

    private PatientDAO patientDAO;
    private RecordDAO recordDAO;

    private GridLayout navigationOptionsLayout;
    private GridLayout newPatientOptionsContainer;

    private ComboBox<Patient> prevPatientFld;

    private DateField dateTxtFld;

    private ComboBox<String> timeFrTxtFld;
    private ComboBox<String> timeToTxtFld;

    private Button navPatientsTableBtn;
    private Button navServicesTableBtn;
    private Button logOutBtn;

    private Label horizontalBar;

    private Button addRecordBtn;
    private Grid<Record> recordsTable;

    public SecuredRecordsView() {

        setStyleName("background-main-color");

        ArrayList<String> timeOptionList = new ArrayList<String>() {{
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

        patientDAO = EjbUtil.getLocalBean(PatientDAO.class);
        recordDAO = EjbUtil.getLocalBean(RecordDAO.class);

        prevPatientFld = new ComboBox<>();
        prevPatientFld.setItems(patientDAO.getAll());
        prevPatientFld.setPlaceholder("Поиск ранее записанного пациента");
        prevPatientFld.setWidth(CssStyles.WIDTH_100_PERCENTS);
        prevPatientFld.setEmptySelectionAllowed(true);

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

        addRecordBtn = new Button("создать запись", e -> createRecord());
        addRecordBtn.setWidth(CssStyles.WIDTH_100_PERCENTS);
        addRecordBtn.setStyleName(CssStyles.ML_BUTTON_8);

        recordsTable = new Grid<>();
        recordsTable.setWidth(CssStyles.WIDTH_100_PERCENTS);
        recordsTable.getDefaultHeaderRow().setStyleName("header-align-center");
        recordsTable.setSelectionMode(Grid.SelectionMode.SINGLE);

        refreshMainTable();

        recordsTable.addItemClickListener(e -> {
            Record selectedRecord = e.getItem();
            Notification.show(selectedRecord.getPatient().getFirstName() + " items selected");
        });

        newPatientOptionsContainer = new GridLayout(3,6);
        newPatientOptionsContainer.setSpacing(true);
        newPatientOptionsContainer.setSizeFull();
        newPatientOptionsContainer.addComponent(prevPatientFld, 0,0,2,0);
        newPatientOptionsContainer.setComponentAlignment(prevPatientFld, Alignment.TOP_CENTER);

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
        refreshMainTable();
    }

    private void createRecord() {

        Patient patient = prevPatientFld.getValue();
        Record record = new Record();

        record.setPatient(patient);
        record.setRecDate(dateTxtFld.getValue());
        record.setTime_from(timeFrTxtFld.getValue());
        record.setTime_to(timeToTxtFld.getValue());

        recordDAO.save(record);

        Notification notif = new Notification(Notifications.RECORD_CREATED_SUCCESS, Notification.Type.HUMANIZED_MESSAGE);

        notif.setDelayMsec(2000);
        notif.setPosition(Position.TOP_RIGHT);
        notif.show(Page.getCurrent());

        prepareFormForNextRequest();
    }

    private void refreshMainTable() {

        recordsTable.setColumns(new String[]{});
        recordsTable.setItems(recordDAO.getAll());
        recordsTable.addColumn(Record::getId).setCaption("#").setWidth(150).setId("1");
        recordsTable.addColumn(d-> d.getRecDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).setCaption("Дата").setWidth(150).setId("2");
        recordsTable.addColumn(Record::getPatient).setCaption("Пациент").setId("3");
        recordsTable.addColumn(Record::getTime_from).setCaption("Начало приёма").setWidth(150).setId("4");
        recordsTable.addColumn(Record::getTime_to).setCaption("Конец приёма").setWidth(150).setId("5");

        for (Grid.Column singleColumn : recordsTable.getColumns()) {
            singleColumn.setStyleGenerator(item -> "v-align-center");
        }
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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
