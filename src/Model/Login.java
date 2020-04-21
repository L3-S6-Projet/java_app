package Model;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class Login {
    public Login(Group root) {
        HBox view = new HBox();
        VBox left = new VBox();
        ImageView picture = new ImageView("imageLogin.jpg");
        Text title = new Text("Scolendar");
        Text subtitle = new Text("Serveur AMU");
        TextField id = new TextField();
        TextField password = new TextField();
        VBox fields = new VBox();
        fields.getChildren().addAll(id, password);
        Button connexion = new Button("Connexion");
        Text copyRigth = new Text("En vous connectant, vous aceptez les termes et conditions.\n© Scolendar 2020 - Tout droits réservés");

        left.getChildren().addAll(title, subtitle, fields, connexion, copyRigth);
        view.getChildren().addAll(left, picture);
        root.getChildren().addAll(view);
    }
}
