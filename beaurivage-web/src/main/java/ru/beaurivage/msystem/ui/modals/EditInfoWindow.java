package ru.beaurivage.msystem.ui.modals;

import com.vaadin.data.Binder;

import com.vaadin.server.Page;

import com.vaadin.shared.Position;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import ru.beaurivage.msystem.logic.dao.PatientDAO;
import ru.beaurivage.msystem.logic.dao.RecordDAO;
import ru.beaurivage.msystem.logic.entities.Patient;
import ru.beaurivage.msystem.logic.entities.Record;
import ru.beaurivage.msystem.logic.enums.CabinetType;
import ru.beaurivage.msystem.logic.util.EjbUtil;

import ru.beaurivage.msystem.ui.constants.CssStyles;
import ru.beaurivage.msystem.ui.constants.UILegend;

import java.time.LocalDate;
import java.util.ArrayList;

public final class EditInfoWindow extends Window {

    private PatientDAO patientDAO = EjbUtil.getLocalBean(PatientDAO.class);
    private RecordDAO recordDAO = EjbUtil.getLocalBean(RecordDAO.class);

    private VerticalLayout basicLayout = new VerticalLayout();

    private ComboBox<Patient> prevPatientFld;

    private DateField dateTxtFld;

    private ComboBox<String> timeFrTxtFld;
    private ComboBox<String> timeToTxtFld;

    private TextField surnTxtFld;
    private TextField nameTxtFld;
    private TextField middTxtFld;
    private TextField phoneTxtFld;
    private TextField emailTxtFld;
    private DateField brdtTxtFld;

    private Button saveButton = new Button("Сохранить");

    private GridLayout editWindowButtonLayout;
    private FormLayout content;

    private Binder<Patient> patientBinder = new Binder<>();
    private Binder<Record> recordBinder = new Binder<>();

    public EditInfoWindow(Record record, ArrayList<String> timeOptionList) {

        setCaption("Редактирование записи № " + record.getId());
        setModal(true);
        setResizable(false);
        setDraggable(false);
        setWidth(700, Unit.PIXELS);

        content = new FormLayout();
        content.setMargin(true);

        GridLayout editWindowFieldsLayout;

        prevPatientFld = new ComboBox<>();
        prevPatientFld.setItems(patientDAO.getAll());
        prevPatientFld.setPlaceholder("Поиск ранее записанного пациента");
        prevPatientFld.setWidth(CssStyles.WIDTH_100_PERCENTS);
        prevPatientFld.setEmptySelectionAllowed(true);
        prevPatientFld.setValue(record.getPatient());

        dateTxtFld = new DateField();
        dateTxtFld.setValue(record.getRecDate());
        dateTxtFld.setWidth(CssStyles.WIDTH_250_PX);

        ComboBox<CabinetType> cabinetTypeFld = new ComboBox<>();
        cabinetTypeFld.setItems(CabinetType.values());
        cabinetTypeFld.setEmptySelectionAllowed(false);
        cabinetTypeFld.setValue(record.getCabinetType());
        cabinetTypeFld.setWidth(CssStyles.WIDTH_250_PX);

        timeFrTxtFld = new ComboBox<>();
        timeFrTxtFld.setItems(timeOptionList);
        timeFrTxtFld.setValue(record.getTime_from());
        timeFrTxtFld.setWidth(CssStyles.WIDTH_250_PX);

        timeToTxtFld = new ComboBox<>();
        timeToTxtFld.setItems(timeOptionList);
        timeToTxtFld.setValue(record.getTime_to());
        timeToTxtFld.setWidth(CssStyles.WIDTH_250_PX);

        editWindowFieldsLayout = new GridLayout(2, 3);
        editWindowFieldsLayout.setSizeFull();
        editWindowFieldsLayout.setSpacing(true);

        recordBinder.forField(prevPatientFld).asRequired("Выберите пациента!").bind(Record::getPatient, Record::setPatient);
        recordBinder.forField(cabinetTypeFld).asRequired("Выберите тип кабинета!").bind(Record::getCabinetType, Record::setCabinetType);
        recordBinder.forField(dateTxtFld).asRequired("Укажите дату приёма!").bind(Record::getRecDate, Record::setRecDate);
        recordBinder.forField(timeFrTxtFld).asRequired("Укажите время начала приёма!").bind(Record::getTime_from, Record::setTime_from);
        recordBinder.forField(timeToTxtFld).asRequired("Укажите время окончания приёма!").bind(Record::getTime_to, Record::setTime_to);

        saveButton.addClickListener(event -> {
                if (recordBinder.validate().isOk()) {
                    this.editRecord(record);
                } else {
                    Notification notif = new Notification("Ошибка валидации! Заполните все необходимые поля!", Notification.Type.ERROR_MESSAGE);
                    notif.setDelayMsec(2000);
                    notif.setPosition(Position.TOP_RIGHT);
                    notif.show(Page.getCurrent());
                }
            }
        );
        saveButton.setWidth("300px");

        editWindowFieldsLayout.addComponent(prevPatientFld, 0,0, 1 ,0);

        editWindowFieldsLayout.addComponent(dateTxtFld, 0, 1);
        editWindowFieldsLayout.addComponent(cabinetTypeFld, 1, 1);

        editWindowFieldsLayout.addComponent(timeFrTxtFld, 0, 2);
        editWindowFieldsLayout.addComponent(timeToTxtFld, 1, 2);

        editWindowFieldsLayout.setComponentAlignment(prevPatientFld, Alignment.MIDDLE_CENTER);

        editWindowFieldsLayout.setComponentAlignment(cabinetTypeFld, Alignment.MIDDLE_LEFT);
        editWindowFieldsLayout.setComponentAlignment(dateTxtFld, Alignment.MIDDLE_RIGHT);

        editWindowFieldsLayout.setComponentAlignment(timeFrTxtFld, Alignment.MIDDLE_LEFT);
        editWindowFieldsLayout.setComponentAlignment(timeFrTxtFld, Alignment.MIDDLE_RIGHT);

        editWindowButtonLayout = new GridLayout(2, 1);
        editWindowButtonLayout.setSizeFull();
        editWindowButtonLayout.setSpacing(true);
        editWindowButtonLayout.addComponent(saveButton,0, 0, 1,0);
        editWindowButtonLayout.setComponentAlignment(saveButton, Alignment.MIDDLE_CENTER);

        basicLayout.addComponent(editWindowFieldsLayout);
        basicLayout.addComponent(editWindowButtonLayout);

        basicLayout.setComponentAlignment(editWindowFieldsLayout, Alignment.TOP_CENTER);
        basicLayout.setComponentAlignment(editWindowButtonLayout, Alignment.TOP_CENTER);

        basicLayout.setSizeFull();
        basicLayout.setSpacing(true);

        content.addComponent(basicLayout);
        content.setComponentAlignment(basicLayout, Alignment.TOP_CENTER);

        setContent(content);
    }

    public EditInfoWindow(Patient patient) {

        setCaption("Редактирование пациента № " + patient.getId());
        setModal(true);
        setResizable(false);
        setDraggable(false);
        setWidth(750, Unit.PIXELS);

        editWindowButtonLayout = new GridLayout(2, 1);
        editWindowButtonLayout.setSizeFull();
        editWindowButtonLayout.setSpacing(true);
        editWindowButtonLayout.addComponent(saveButton,0, 0, 1,0);
        editWindowButtonLayout.setComponentAlignment(saveButton, Alignment.MIDDLE_CENTER);

        content = new FormLayout();
        content.setMargin(true);

        GridLayout patientLayoutOptions = new GridLayout(3, 2);
        patientLayoutOptions.setSpacing(true);

        surnTxtFld = new TextField();
        surnTxtFld.setPlaceholder(UILegend.TXT_FIELD_SURN);
        surnTxtFld.setWidth("200px");
        surnTxtFld.setValue(patient.getLastName());

        nameTxtFld = new TextField();
        nameTxtFld.setPlaceholder(UILegend.TXT_FIELD_NAME);
        nameTxtFld.setWidth("200px");
        nameTxtFld.setValue(patient.getFirstName());

        middTxtFld = new TextField();
        middTxtFld.setPlaceholder(UILegend.TXT_FIELD_MIDD);
        middTxtFld.setWidth("200px");
        middTxtFld.setValue(patient.getMiddleName());

        phoneTxtFld = new TextField();
        phoneTxtFld.setPlaceholder(UILegend.TXT_FIELD_PHONE);
        phoneTxtFld.setWidth("200px");
        phoneTxtFld.setValue(patient.getPhone());

        emailTxtFld = new TextField();
        emailTxtFld.setPlaceholder(UILegend.TXT_FIELD_EMAIL);
        emailTxtFld.setWidth("200px");
        emailTxtFld.setValue(patient.getEmail());

        brdtTxtFld = new DateField();
        brdtTxtFld.setPlaceholder("dd-MM-yyyy");
        brdtTxtFld.setWidth("200px");
        brdtTxtFld.setValue(LocalDate.now());
        brdtTxtFld.setDateFormat("dd-MM-yyyy");
        brdtTxtFld.setValue(patient.getBirthDate());

        patientBinder.forField(nameTxtFld).withValidator(name -> name.length() >= 3, "Имя должно содержать хотя бы 3 буквы!").bind(Patient::getFirstName, Patient::setFirstName);
        patientBinder.forField(surnTxtFld).withValidator(surname -> surname.length() >= 3, "Фамилия должна содержать хотя бы 3 буквы!").bind(Patient::getLastName, Patient::setLastName);
        patientBinder.forField(phoneTxtFld).withValidator(phone -> phone.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$"), "Введите корректный номер телефона начиная с +7 или 8! ").bind(Patient::getEmail, Patient::setEmail);
       // patientBinder.forField(emailTxtFld).withValidator(new EmailValidator("Это не e-mail! Введите корректный e-mail!")).bind(Patient::getEmail, Patient::setEmail);
        patientBinder.forField(brdtTxtFld).asRequired("Дата рождения не может быть пустой!").bind(Patient::getBirthDate, Patient::setBirthDate);

        saveButton.addClickListener(e -> {
                if (patientBinder.validate().isOk()) {
                    this.editPatient(patient);
                } else {
                    Notification notif = new Notification("Ошибка валидации! Заполните все необходимые поля!", Notification.Type.ERROR_MESSAGE);
                    notif.setDelayMsec(2000);
                    notif.setPosition(Position.TOP_RIGHT);
                    notif.show(Page.getCurrent());
                }
            }
        );
        saveButton.setWidth("300px");

        patientLayoutOptions.addComponent(surnTxtFld, 0, 0);
        patientLayoutOptions.addComponent(nameTxtFld, 1,0);
        patientLayoutOptions.addComponent(middTxtFld, 2 , 0);

        patientLayoutOptions.addComponent(phoneTxtFld, 0,1);
        patientLayoutOptions.addComponent(emailTxtFld, 1, 1);
        patientLayoutOptions.addComponent(brdtTxtFld, 2 , 1);

        patientLayoutOptions.setComponentAlignment(surnTxtFld, Alignment.MIDDLE_LEFT);
        patientLayoutOptions.setComponentAlignment(nameTxtFld, Alignment.MIDDLE_CENTER);
        patientLayoutOptions.setComponentAlignment(nameTxtFld, Alignment.MIDDLE_RIGHT);

        patientLayoutOptions.setComponentAlignment(phoneTxtFld, Alignment.MIDDLE_LEFT);
        patientLayoutOptions.setComponentAlignment(emailTxtFld, Alignment.MIDDLE_CENTER);
        patientLayoutOptions.setComponentAlignment(brdtTxtFld, Alignment.MIDDLE_RIGHT);

        basicLayout.addComponent(patientLayoutOptions);
        basicLayout.addComponent(editWindowButtonLayout);
        basicLayout.setComponentAlignment(patientLayoutOptions, Alignment.MIDDLE_CENTER);
        basicLayout.setComponentAlignment(editWindowButtonLayout, Alignment.TOP_CENTER);

        basicLayout.setSizeFull();
        basicLayout.setMargin(true);
        basicLayout.setSpacing(true);

        content.addComponent(basicLayout);
        content.setComponentAlignment(basicLayout, Alignment.TOP_CENTER);

        setContent(content);

    }

    private void editRecord(Record record) {
        record.setPatient(prevPatientFld.getValue());
        record.setRecDate(dateTxtFld.getValue());
        record.setTime_from(timeFrTxtFld.getValue());
        record.setTime_to(timeToTxtFld.getValue());
        recordDAO.save(record);
        this.close();
    }

    private void editPatient(Patient patient) {
        patient.setLastName(surnTxtFld.getValue());
        patient.setFirstName(nameTxtFld.getValue());
        patient.setMiddleName(middTxtFld.getValue());
        patient.setPhone(phoneTxtFld.getValue());
        patient.setEmail(emailTxtFld.getValue());
        patient.setBirthDate(brdtTxtFld.getValue());
        patientDAO.save(patient);
        this.close();
    }
}