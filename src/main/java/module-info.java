module com.vm.valorizzazionemagazzinofxml {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.vm.valorizzazionemagazzinofxml to javafx.fxml;
    exports com.vm.valorizzazionemagazzinofxml;
}
