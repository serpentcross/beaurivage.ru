package ru.beaurivage.msystem.ui.viewssecured;

import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;

import ru.beaurivage.msystem.logic.dao.PatientDAO;
import ru.beaurivage.msystem.logic.entities.Patient;
import ru.beaurivage.msystem.logic.services.AuthService;
import ru.beaurivage.msystem.logic.util.EjbUtil;

import ru.beaurivage.msystem.ui.VaadinUI;
import ru.beaurivage.msystem.ui.constants.CssStyles;
import ru.beaurivage.msystem.ui.constants.Notifications;
import ru.beaurivage.msystem.ui.constants.UILegend;
import ru.beaurivage.msystem.ui.constants.ViewsNaming;
import ru.beaurivage.msystem.ui.modals.EditInfoWindow;

import java.time.format.DateTimeFormatter;

@Theme("beaurivage")
public class SecuredPatientsView extends CustomComponent implements View {

    private PatientDAO patientDAO;

    private GridLayout navigationOptionsLayout;
    private GridLayout newPatientOptionsContainer;

    private Button navRecordsTableBtn;
    private Button navServicesTableBtn;
    private Button logOutBtn;

    private Label horizontalBar;

    private TextField nameTxtFld;
    private TextField surnTxtFld;
    private TextField middTxtFld;
    private TextField phoneTxtFld;
    private TextField emailTxtFld;
    private DateField brdtTxtFld;

    private Button addPatientBtn;
    private Grid<Patient> patientsTable;

    private EditInfoWindow editInfoWindow;

    public SecuredPatientsView() {

        setStyleName("background-main-color");

        patientDAO = EjbUtil.getLocalBean(PatientDAO.class);

        navRecordsTableBtn = new Button(UILegend.RECORDS_PAGE, this::navigateToRecordsOptions);
        navRecordsTableBtn.setWidth(CssStyles.WIDTH_250_PX);

        navServicesTableBtn = new Button(UILegend.SERVICES_PAGE, this::navigateToServicesOptions);
        navServicesTableBtn.setWidth(CssStyles.WIDTH_250_PX);

        logOutBtn = new Button(UILegend.LOGOUT_BUTTON, this::onLogout);
        logOutBtn.setWidth(CssStyles.WIDTH_250_PX);
        logOutBtn.setStyleName("ml-button-13");

        horizontalBar = new Label();
        horizontalBar.setStyleName("horizontalRule");

        navigationOptionsLayout = new GridLayout(3, 2);
        navigationOptionsLayout.setSpacing(true);
        navigationOptionsLayout.setSizeFull();

        navigationOptionsLayout.addComponent(navRecordsTableBtn,0,0);
        navigationOptionsLayout.addComponent(navServicesTableBtn,1,0);
        navigationOptionsLayout.addComponent(logOutBtn,2,0);
        navigationOptionsLayout.setComponentAlignment(navRecordsTableBtn, Alignment.TOP_LEFT);
        navigationOptionsLayout.setComponentAlignment(navServicesTableBtn, Alignment.TOP_CENTER);
        navigationOptionsLayout.setComponentAlignment(logOutBtn, Alignment.TOP_RIGHT);
        navigationOptionsLayout.addComponent(horizontalBar, 0,1,2,1);

        nameTxtFld = new TextField();
        nameTxtFld.setPlaceholder(UILegend.TXT_FIELD_NAME);
        nameTxtFld.setWidth(CssStyles.WIDTH_250_PX);

        surnTxtFld = new TextField();
        surnTxtFld.setPlaceholder(UILegend.TXT_FIELD_SURN);
        surnTxtFld.setWidth(CssStyles.WIDTH_250_PX);

        middTxtFld = new TextField();
        middTxtFld.setPlaceholder(UILegend.TXT_FIELD_MIDD);
        middTxtFld.setWidth(CssStyles.WIDTH_250_PX);

        phoneTxtFld = new TextField();
        phoneTxtFld.setPlaceholder(UILegend.TXT_FIELD_PHONE);
        phoneTxtFld.setWidth(CssStyles.WIDTH_250_PX);

        emailTxtFld = new TextField();
        emailTxtFld.setPlaceholder(UILegend.TXT_FIELD_EMAIL);
        emailTxtFld.setWidth(CssStyles.WIDTH_250_PX);

        brdtTxtFld = new DateField();
        brdtTxtFld.setDateFormat("dd-MM-yyyy");
        brdtTxtFld.setPlaceholder("дата рождения");
        brdtTxtFld.setWidth(CssStyles.WIDTH_250_PX);
        
        addPatientBtn = new Button("внести нового пациента", this::createPatient);
        addPatientBtn.setWidth(CssStyles.WIDTH_100_PERCENTS);
        addPatientBtn.setStyleName(CssStyles.ML_BUTTON_8);

        patientsTable = new Grid<>();
        patientsTable.setWidth(CssStyles.WIDTH_100_PERCENTS);
        patientsTable.getDefaultHeaderRow().setStyleName("header-align-center");
        patientsTable.setSelectionMode(Grid.SelectionMode.SINGLE);

        refreshPatientsTable();

        newPatientOptionsContainer = new GridLayout(3,4);

        newPatientOptionsContainer.setSpacing(true);
        newPatientOptionsContainer.setSizeFull();

        newPatientOptionsContainer.addComponent(nameTxtFld, 0,0);
        newPatientOptionsContainer.addComponent(surnTxtFld, 1,0);
        newPatientOptionsContainer.addComponent(middTxtFld, 2, 0);

        newPatientOptionsContainer.addComponent(phoneTxtFld, 0,1);
        newPatientOptionsContainer.addComponent(emailTxtFld, 1,1);
        newPatientOptionsContainer.addComponent(brdtTxtFld, 2, 1);

        newPatientOptionsContainer.addComponent(addPatientBtn, 0,2,2,2);
        
        newPatientOptionsContainer.addComponent(patientsTable, 0,3,2,3);

        newPatientOptionsContainer.setComponentAlignment(nameTxtFld, Alignment.TOP_LEFT);
        newPatientOptionsContainer.setComponentAlignment(surnTxtFld, Alignment.TOP_CENTER);
        newPatientOptionsContainer.setComponentAlignment(middTxtFld, Alignment.TOP_RIGHT);

        newPatientOptionsContainer.setComponentAlignment(phoneTxtFld, Alignment.TOP_LEFT);
        newPatientOptionsContainer.setComponentAlignment(emailTxtFld, Alignment.TOP_CENTER);
        newPatientOptionsContainer.setComponentAlignment(brdtTxtFld, Alignment.TOP_RIGHT);

        newPatientOptionsContainer.setComponentAlignment(addPatientBtn, Alignment.TOP_CENTER);

        VerticalLayout basicLayout = new VerticalLayout(navigationOptionsLayout, newPatientOptionsContainer);
        basicLayout.setComponentAlignment(navigationOptionsLayout, Alignment.TOP_CENTER);
        basicLayout.setComponentAlignment(newPatientOptionsContainer, Alignment.TOP_CENTER);


        basicLayout.setSizeFull();

        setCompositionRoot(basicLayout);
    }

    private void prepareFormForNextRequest(Patient patient) {

        nameTxtFld.clear();
        surnTxtFld.clear();
        middTxtFld.clear();
        phoneTxtFld.clear();
        emailTxtFld.clear();

        refreshPatientsTable();
    }

    private void createPatient(Button.ClickEvent event) {

        Patient patient = new Patient();

        patient.setFirstName(nameTxtFld.getValue());
        patient.setLastName(surnTxtFld.getValue());
        patient.setMiddleName(middTxtFld.getValue());
        patient.setPhone(phoneTxtFld.getValue());
        patient.setBirthDate(brdtTxtFld.getValue());
        patient.setEmail(emailTxtFld.getValue());

        patientDAO.save(patient);

        Notification notif = new Notification(Notifications.RECORD_CREATED_SUCCESS, Notification.Type.HUMANIZED_MESSAGE);
        notif.setDelayMsec(2000);
        notif.setPosition(Position.TOP_RIGHT);
        notif.show(Page.getCurrent());

        prepareFormForNextRequest(patient);
    }


    private void refreshPatientsTable() {

        patientsTable.setColumns(new String[]{});
        patientsTable.setItems(patientDAO.getAll());

        ButtonRenderer editButtonRenderer = new ButtonRenderer(clickEvent -> {
            Patient selectedPatient = (Patient) clickEvent.getItem();
            editInfoWindow = new EditInfoWindow(selectedPatient);
            UI.getCurrent().addWindow(editInfoWindow);
            editInfoWindow.addCloseListener(e -> refreshPatientsTable());
        });

        editButtonRenderer.setHtmlContentAllowed(true);

        patientsTable.addColumn(Patient::getId).setCaption(UILegend.NUMBER_COLUMN).setWidth(150).setId("1");
        patientsTable.addColumn(Patient::getFirstName).setCaption(UILegend.TXT_FIELD_NAME).setWidth(150).setId("2");
        patientsTable.addColumn(Patient::getLastName).setCaption(UILegend.TXT_FIELD_SURN).setWidth(150).setId("3");
        patientsTable.addColumn(Patient::getMiddleName).setCaption(UILegend.TXT_FIELD_MIDD).setWidth(150).setId("4");
        patientsTable.addColumn(Patient::getPhone).setCaption(UILegend.TXT_FIELD_PHONE).setWidth(150).setId("5");
        patientsTable.addColumn(d-> d.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).setCaption("Дата рождения").setWidth(150).setId("6");
        patientsTable.addColumn(Patient::getEmail).setCaption(UILegend.TXT_FIELD_EMAIL).setId("7");
        patientsTable.addColumn(rec -> VaadinIcons.EDIT.getHtml() , editButtonRenderer).setWidth(65);

        for (Grid.Column singleColumn : patientsTable.getColumns()) {
            singleColumn.setStyleGenerator(item -> "v-align-center");
        }
    }

    private void navigateToRecordsOptions(Button.ClickEvent event) {
        VaadinUI.getNavigation().navigateTo(ViewsNaming.PRIVATE_RECORDS_VIEW);
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
