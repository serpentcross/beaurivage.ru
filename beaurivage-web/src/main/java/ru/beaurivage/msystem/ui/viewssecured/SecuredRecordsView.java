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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.beaurivage.msystem.logic.dao.CustomerDAO;
import ru.beaurivage.msystem.logic.dao.RecordDAO;
import ru.beaurivage.msystem.logic.entities.Customer;
import ru.beaurivage.msystem.logic.entities.Record;
import ru.beaurivage.msystem.logic.services.AuthService;
import ru.beaurivage.msystem.logic.util.EjbUtil;
import ru.beaurivage.msystem.ui.constants.UILegend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Theme("beaurivage")
public class SecuredRecordsView extends CustomComponent implements View {

    private CustomerDAO customerDAO;
    private RecordDAO recordDAO;

    private ComboBox<Customer> prevCusFld;

    private TextField nameTxtFld;
    private TextField surnTxtFld;
    private TextField middTxtFld;
    private TextField phoneTxtFld;
    private TextField emailTxtFld;
    private DateField dateTxtFld;

    private ComboBox<String> timeFrTxtFld;
    private ComboBox<String> timeToTxtFld;

    private Button navPatientsTableBtn;
    private Button navServicesTableBtn;
    private Button logOutBtn;

    private Label hBar;

    private Button addRecordBtn;
    private Grid<Record> grid;

    private boolean customerAlreadyExists;

    public SecuredRecordsView() {

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

        customerDAO = EjbUtil.getLocalBean(CustomerDAO.class);
        recordDAO = EjbUtil.getLocalBean(RecordDAO.class);

        prevCusFld = new ComboBox<>();
        prevCusFld.setItems(customerDAO.getAll());
        prevCusFld.setPlaceholder("Поиск ранее записанного пациента");
        prevCusFld.setWidth("100%");
        prevCusFld.setEmptySelectionAllowed(true);

        prevCusFld.setItemCaptionGenerator(
            p -> p.getLastName() + " " + p.getFirstName() + " " + p.getMiddleName() + " " + p.getPhone() + " " + p.getEmail()
        );

        prevCusFld.addValueChangeListener(
            e -> inputNewCustomer()
        );

        GridLayout viewOptionsLayout = new GridLayout(3, 2);
        //viewOptionsLayout.setMargin(true);
        viewOptionsLayout.setSpacing(true);
        viewOptionsLayout.setSizeFull();

        hBar = new Label();
        hBar.setStyleName("horizontalRule");

        logOutBtn = new Button("выход", this::onLogout);
        logOutBtn.setWidth("250px");
        logOutBtn.setStyleName("ml-button-13");

        navPatientsTableBtn = new Button("пациенты");
        navPatientsTableBtn.setWidth("250px");

        navServicesTableBtn = new Button("услуги");
        navServicesTableBtn.setWidth("250px");

        viewOptionsLayout.addComponent(navPatientsTableBtn,0,0);
        viewOptionsLayout.addComponent(navServicesTableBtn,1,0);
        viewOptionsLayout.addComponent(logOutBtn,2,0);
        viewOptionsLayout.setComponentAlignment(navPatientsTableBtn, Alignment.TOP_LEFT);
        viewOptionsLayout.setComponentAlignment(navServicesTableBtn, Alignment.TOP_CENTER);
        viewOptionsLayout.setComponentAlignment(logOutBtn, Alignment.TOP_RIGHT);
        viewOptionsLayout.addComponent(hBar, 0,1,2,1);


        GridLayout newCustomerOptionsContainer = new GridLayout(3,6);
        newCustomerOptionsContainer.setSpacing(true);
        newCustomerOptionsContainer.setSizeFull();
        newCustomerOptionsContainer.addComponent(prevCusFld, 0,0,2,0);
        newCustomerOptionsContainer.setComponentAlignment(prevCusFld, Alignment.TOP_CENTER);

        nameTxtFld = new TextField();
        nameTxtFld.setPlaceholder(UILegend.TXT_FIELD_NAME);
        nameTxtFld.setWidth("250px");

        surnTxtFld = new TextField();
        surnTxtFld.setPlaceholder(UILegend.TXT_FIELD_SURN);
        surnTxtFld.setWidth("250px");

        middTxtFld = new TextField();
        middTxtFld.setPlaceholder(UILegend.TXT_FIELD_MIDD);
        middTxtFld.setWidth("250px");

        phoneTxtFld = new TextField();
        phoneTxtFld.setPlaceholder(UILegend.TXT_FIELD_PHONE);
        phoneTxtFld.setWidth("250px");

        emailTxtFld = new TextField();
        emailTxtFld.setPlaceholder(UILegend.TXT_FIELD_EMAIL);
        emailTxtFld.setWidth("250px");

        dateTxtFld = new DateField();
        dateTxtFld.setPlaceholder("dd-MM-yyyy");
        dateTxtFld.setWidth("250px");
        dateTxtFld.setValue(LocalDate.now());
        dateTxtFld.setDateFormat("dd-MM-yyyy");

        timeFrTxtFld = new ComboBox<>();
        timeFrTxtFld.setItems(timeOptionList);
        timeFrTxtFld.setPlaceholder("время начала приёма");
        timeFrTxtFld.setWidth("250px");
        timeFrTxtFld.setEmptySelectionAllowed(false);
        timeFrTxtFld.setTextInputAllowed(false);

        timeToTxtFld = new ComboBox<>();
        timeToTxtFld.setItems(timeOptionList);
        timeToTxtFld.setPlaceholder("время конца приёма");
        timeToTxtFld.setWidth("250px");
        timeToTxtFld.setEmptySelectionAllowed(false);
        timeToTxtFld.setTextInputAllowed(false);

        addRecordBtn = new Button("создать запись", e -> createRecord());
        addRecordBtn.setWidth("250px");
        addRecordBtn.setStyleName("ml-button-8");

        grid = new Grid<>();
        grid.setWidth("100%");
        grid.getDefaultHeaderRow().setStyleName("v-grid-column-header-content");

        refreshMainTable();

        newCustomerOptionsContainer.addComponent(nameTxtFld, 0,1);
        newCustomerOptionsContainer.addComponent(surnTxtFld, 1,1);
        newCustomerOptionsContainer.addComponent(middTxtFld, 2, 1);

        newCustomerOptionsContainer.addComponent(phoneTxtFld, 0,2);
        newCustomerOptionsContainer.addComponent(emailTxtFld, 1,2);
        newCustomerOptionsContainer.addComponent(dateTxtFld, 2, 2);

        newCustomerOptionsContainer.addComponent(timeFrTxtFld, 0,3);
        newCustomerOptionsContainer.addComponent(timeToTxtFld, 1,3);

        newCustomerOptionsContainer.addComponent(addRecordBtn, 2,3);
        newCustomerOptionsContainer.addComponent(grid, 0,5,2,5);

        newCustomerOptionsContainer.setComponentAlignment(nameTxtFld, Alignment.TOP_LEFT);
        newCustomerOptionsContainer.setComponentAlignment(surnTxtFld, Alignment.TOP_CENTER);
        newCustomerOptionsContainer.setComponentAlignment(middTxtFld, Alignment.TOP_RIGHT);

        newCustomerOptionsContainer.setComponentAlignment(phoneTxtFld, Alignment.TOP_LEFT);
        newCustomerOptionsContainer.setComponentAlignment(emailTxtFld, Alignment.TOP_CENTER);
        newCustomerOptionsContainer.setComponentAlignment(dateTxtFld, Alignment.TOP_RIGHT);

        newCustomerOptionsContainer.setComponentAlignment(timeFrTxtFld, Alignment.TOP_LEFT);
        newCustomerOptionsContainer.setComponentAlignment(timeToTxtFld, Alignment.TOP_CENTER);
        newCustomerOptionsContainer.setComponentAlignment(addRecordBtn, Alignment.TOP_RIGHT);

        VerticalLayout basicLayout = new VerticalLayout(viewOptionsLayout, newCustomerOptionsContainer);
        basicLayout.setComponentAlignment(newCustomerOptionsContainer, Alignment.TOP_CENTER);
        basicLayout.setComponentAlignment(viewOptionsLayout, Alignment.TOP_CENTER);

        basicLayout.setSizeFull();

        setCompositionRoot(basicLayout);

    }

    private void onLogout(Button.ClickEvent event) {
        AuthService.logOut();
    }

    private void prepareFormForNextRequest(Record record) {

        prevCusFld.clear();

        nameTxtFld.clear();
        surnTxtFld.clear();
        middTxtFld.clear();
        phoneTxtFld.clear();
        emailTxtFld.clear();

        refreshMainTable();
    }

    private void inputNewCustomer() {
        if (prevCusFld.getValue() != null) {
            blockUnusedFields(true);
        } else {
            blockUnusedFields(false);
        }
    }

    private void blockUnusedFields(boolean isBlocked) {
        nameTxtFld.setReadOnly(isBlocked);
        surnTxtFld.setReadOnly(isBlocked);
        middTxtFld.setReadOnly(isBlocked);
        phoneTxtFld.setReadOnly(isBlocked);
        emailTxtFld.setReadOnly(isBlocked);

        customerAlreadyExists = isBlocked;
    }

    private void createRecord() {

        Customer customer = new Customer();

        if (prevCusFld.getValue() != null) {
            customer = prevCusFld.getValue();
        } else {
            customer.setFirstName(nameTxtFld.getValue());
            customer.setLastName(surnTxtFld.getValue());
            customer.setMiddleName(middTxtFld.getValue());
            customer.setPhone(phoneTxtFld.getValue());
            customer.setEmail(emailTxtFld.getValue());
        }

        Record record = new Record();
        record.setCustomer(customer);
        record.setRecDate(dateTxtFld.getValue());
        record.setTime_from(timeFrTxtFld.getValue());
        record.setTime_to(timeToTxtFld.getValue());

        recordDAO.save(record);

        Notification notif = new Notification("Запись успешно добавлена!", Notification.Type.HUMANIZED_MESSAGE);
        notif.setDelayMsec(2000);
        notif.setPosition(Position.TOP_RIGHT);
        notif.show(Page.getCurrent());

        prepareFormForNextRequest(record);
    }

    private void refreshMainTable() {

        grid.setColumns(new String[]{});
        grid.setItems(recordDAO.getAll());

        grid.addColumn(Record::getId).setCaption("#").setStyleGenerator(item -> "v-align-center").setId("1").setWidth(45);
        grid.addColumn(
                d-> d.getRecDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        ).setCaption("Дата приёма").setStyleGenerator(item -> "v-align-center").setId("2").setWidth(130);
        grid.addColumn(Record::getCustomer).setCaption("Пациент").setStyleGenerator(item -> "v-align-center").setId("3");
        grid.addColumn(Record::getTime_from).setCaption("Начало приёма").setStyleGenerator(item -> "v-align-center").setId("4").setWidth(150);
        grid.addColumn(Record::getTime_to).setCaption("Конец приёма").setStyleGenerator(item -> "v-align-center").setId("5").setWidth(150);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
