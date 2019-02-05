package app

import com.vaadin.flow.component.Composite
import com.vaadin.flow.component.Html
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.dependency.HtmlImport
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.page.BodySize
import com.vaadin.flow.component.page.Viewport
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import groovy.util.logging.Slf4j
import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class Application extends SpringBootServletInitializer {
    static void main(String[] args) throws Exception {
        new SpringApplication(Application).tap {
            // further config...
            bannerMode = Banner.Mode.OFF
            run(args)
        }
    }
}

@RestController
class HelloWorldController {
    @GetMapping("/hello")
    String index() {
        return "Hello World"
    }
}

@Route('')
@Slf4j
@Theme(Lumo)
@HtmlImport('frontend:///styles.html')
@BodySize(height = "100vh", width = "100vw")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
class MainLayout extends Composite<Div> {
    MainLayout() {
        content.add(
                new AppLayout().tap {
                    branding = new Html("<span><strong>Branded</strong>&amp;<em>Exiled</em></span>")
                    content = new VerticalLayout(
                            new H1("Lorem Ipsum"),
                            new Span("Lorem Ipsum"),
                            new TextField("Some input")
                    )
                    createMenu().tap {
                        addMenuItem(VaadinIcon.ABACUS.create(), "About")
                        addMenuItem(VaadinIcon.COGS.create(), "Settings")
                        addMenuItem(new ComboBox().tap{
                            setItems([Lumo.LIGHT,Lumo.DARK])
                            value = Lumo.LIGHT
                            addValueChangeListener{ theme ->
                                UI.ifPresent{ ui ->
                                    def themeList = ui.element.themeList
                                    if (theme.value==Lumo.LIGHT) {
                                        themeList.remove(Lumo.DARK)
                                        themeList.add(Lumo.LIGHT)
                                    } else {
                                        themeList.remove(Lumo.LIGHT)
                                        themeList.add(Lumo.DARK)
                                    }
                                }
                            }
                        })
                    }
                }
        )
    }
}

