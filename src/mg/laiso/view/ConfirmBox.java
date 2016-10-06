package mg.laiso.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Laiso on 05/10/2016.
 */
public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String message){
        Stage confirmBoxStage = new Stage();

        confirmBoxStage.initModality(Modality.APPLICATION_MODAL);
        confirmBoxStage.setTitle(title);
        confirmBoxStage.setMinWidth(300);

        Label label = new Label(message);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10,10,10,10));

        Line line = new Line();

        ButtonBar buttonBar = new ButtonBar();

        Button btnValider = new Button("Oui");
        Button btnAnnuler= new Button("Non");

        btnValider.setOnAction(e -> {
            answer = true; confirmBoxStage.close();
        });

        btnAnnuler.setOnAction(e -> {
            answer = false; confirmBoxStage.close();
        });

        buttonBar.getButtons().addAll(btnValider, btnAnnuler);

        vBox.getChildren().addAll(label, line, buttonBar);

        Scene scene = new Scene(vBox);
        confirmBoxStage.setScene(scene);

        confirmBoxStage.showAndWait();

        return answer;
    }
}
