package ru.beaurivage.msystem.ui.viewspublic;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

import ru.beaurivage.msystem.ui.VaadinUI;
import ru.beaurivage.msystem.ui.constants.CssStyles;
import ru.beaurivage.msystem.ui.constants.ViewsNaming;

import java.io.File;

public class PublicErrorView extends CustomComponent implements View {

    public PublicErrorView() {

        setWidth(CssStyles.WIDTH_100_PERCENTS);

        GridLayout errorLogoLayout = new GridLayout(1, 2);

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/notfound.png"));
        Image errorLogo = new Image(null, resource);

        Button goBackBtn = new Button("вернуться назад", e -> VaadinUI.getNavigation().navigateTo(ViewsNaming.PUBLIC_LOGIN_VIEW));
        goBackBtn.setWidth(CssStyles.WIDTH_250_PX);
        goBackBtn.setStyleName("ml-button-13");

        errorLogoLayout.addComponent(errorLogo,0,0);
        errorLogoLayout.setComponentAlignment(errorLogo, Alignment.MIDDLE_CENTER);

        errorLogoLayout.addComponent(goBackBtn, 0, 1);
        errorLogoLayout.setComponentAlignment(goBackBtn, Alignment.MIDDLE_CENTER);

        VerticalLayout basicLayout = new VerticalLayout(errorLogoLayout);
        basicLayout.setSizeFull();
        basicLayout.setSpacing(true);
        basicLayout.setComponentAlignment(errorLogoLayout, Alignment.MIDDLE_CENTER);

        setCompositionRoot(basicLayout);
        setSizeFull();

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {}
}