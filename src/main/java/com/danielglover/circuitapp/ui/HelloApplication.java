package com.danielglover.circuitapp.ui;

import com.danielglover.circuitapp.logic.Parser;
import com.danielglover.circuitapp.logic.Token;
import com.danielglover.circuitapp.logic.Tokenizer;
import com.danielglover.circuitapp.logic.circuit.Circuit;
import com.danielglover.circuitapp.logic.circuit.CircuitBuilder;
import com.danielglover.circuitapp.logic.nodes.ExprNode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;


public class HelloApplication extends Application {
    private Circuit circuit;
    private CircuitCanvas canvas;
    private ControlPanel controlPanel;
    private TextField expressionField;
    private Parser parser;
    private Tokenizer tokenizer;
    private CircuitBuilder builder;


    @Override
    public void start(Stage stage) throws Exception {

        // Initialize components
        parser = new Parser();
        tokenizer = new Tokenizer();
        builder = new CircuitBuilder();
        controlPanel = new ControlPanel();

        BorderPane root = createMainLayout();

        // Create scene
        Scene scene = new Scene(root, 1440, 1200, Color.rgb(244, 244, 244));

        stage.setTitle("Logic Circuit Simulator");
        stage.setScene(scene);
        stage.show();

    }

    public BorderPane createMainLayout(){
        BorderPane borderPane = new BorderPane();

        VBox topPanel = createTopPanel();
        borderPane.setTop(topPanel);

        canvas = new CircuitCanvas(1200, 800);
        borderPane.setCenter(canvas);

        ControlPanel controlPanel = new ControlPanel();
        borderPane.setBottom(controlPanel);

        return borderPane;
    }

    public VBox createTopPanel(){
        VBox vbox = new VBox();
        vbox.setStyle("-fx-min-width: 100%; -fx-alignment: center;");

        HBox titleBox = new HBox(5);
        titleBox.setStyle("-fx-alignment: center; -fx-padding: 32px 4px 4px 4px;");


        Image image = new Image(getClass().getResource("/images/circuitry.png").toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setPreserveRatio(true);


        Label labelTitle = new Label("Logic Circuit Simulator");
        labelTitle.setStyle("-fx-text-alignment: center; -fx-font-size: 28px; -fx-font-family: 'SF Pro Display'; -fx-font-weight: bold;");

        Label subLabel = new Label("Convert propositional logic expressions into interactive digital circuits");
        subLabel.setStyle("-fx-text-alignment: center; -fx-font-size: 18px; -fx-font-family: 'Roboto Light'; -fx-font-weight: normal;");

        titleBox.getChildren().addAll(imageView, labelTitle);

        HBox hbox = new HBox();
        hbox.setStyle("-fx-min-width: 100%; -fx-spacing: 12px; -fx-alignment: center; -fx-padding: 24px 4px 4px 4px;");

        Label label = new Label("Expression: ");
        label.setStyle("-fx-font-family: 'SF Pro Display'; -fx-font-weight: bold;");

        // Text field
        expressionField = new TextField();
        expressionField.setPromptText("Enter expression (e.g., A && B || C)");
        expressionField.setPrefWidth(400);
        expressionField.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

        // Button
        Button parseButton = new Button("Parse");
        parseButton.setStyle("-fx-background-color: #6760D9; -fx-font-weight: bold; -fx-font-family: 'SF Pro Display'; -fx-text-fill: white;");
        parseButton.setOnAction(actionEvent -> {
            try {
                handleParse();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        vbox.getChildren().addAll(titleBox, subLabel, hbox);
        hbox.getChildren().addAll(label, expressionField, parseButton);
        return vbox;
    }

    public void handleParse() throws Exception {
        try {
            // Get expression from text field
            String expression = expressionField.getText().trim();

            if (expression.isEmpty()){
                throw new Error("Please enter an expression!");
            }

            // Parse expression
            List<Token> tokens = tokenizer.tokenize(expression);
            ExprNode tree = parser.parse(tokens);

            // Build circuit
            circuit = builder.buildCircuit(tree);

            // Update canvas
            canvas.setCircuit(circuit);
            canvas.redraw();

            // Update control panel
            controlPanel.setCircuit(circuit);
            controlPanel.updateFromCircuit();

            // Initialize all inputs to false
            circuit.reset();
            canvas.redraw();
        } catch (Exception e){
            throw new Exception("Error parsing expression: " + e.getMessage());
        }
    }

    public void loadExample() throws Exception {
        expressionField.setText("(A && B) || C");
        handleParse();
    }

    public void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
