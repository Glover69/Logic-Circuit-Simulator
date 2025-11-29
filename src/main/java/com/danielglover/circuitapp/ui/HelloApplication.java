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
        Scene scene = new Scene(root, 1200, 1100);

        stage.setTitle("Logic Circuit Simulator");
        stage.setScene(scene);
        stage.show();

    }

    public BorderPane createMainLayout(){
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #EBF1FF");

        VBox topPanel = createTopPanel();
        borderPane.setTop(topPanel);


        canvas = new CircuitCanvas(1200, 600);
        canvas.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2); -fx-background-radius: 12; -fx-border-radius: 12;");
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
        labelTitle.setStyle("-fx-text-alignment: center; -fx-text-fill: #1F2937; -fx-font-size: 32px; -fx-font-family: 'SF Pro Display'; -fx-font-weight: bold;");

        Label subLabel = new Label("Convert propositional logic expressions into interactive digital circuits");
        subLabel.setStyle("-fx-text-alignment: center; -fx-text-fill: #4B5563; -fx-font-size: 18px; -fx-padding: 0px 0px 20px 0px; -fx-font-family: 'Roboto Light'; -fx-font-weight: normal;");

        titleBox.getChildren().addAll(imageView, labelTitle);


        HBox outer = new HBox();
        outer.setStyle("-fx-spacing: 12px; -fx-max-width: 750px; -fx-background-radius: 12; -fx-border-radius: 12; -fx-background-color: white; -fx-alignment: center; -fx-padding: 16px 0px 22px 0px;");

        VBox verticalDiv = new VBox();
        verticalDiv.setStyle("-fx-spacing: 10px;");

        VBox hintsDiv = new VBox();
        hintsDiv.setStyle("-fx-background-color: #EFF6FF; -fx-border-color: transparent transparent transparent #3C82F6; -fx-border-width: 0 0 0 5px; -fx-padding: 15px 0px 15px 20px");

        HBox firstHintWrapper = new HBox();
        Label hintOneHeader = new Label("Supported operators: ");
        hintOneHeader.setStyle("-fx-text-fill: #374151; -fx-font-size: 16px; -fx-font-family: 'SF Pro Display'; -fx-font-weight: bold");

        Label hintOneAnswer = new Label("&& (For AND), || (For OR), and ! (For NOT)");
        hintOneAnswer.setStyle("-fx-text-fill: #374151; -fx-font-size: 16px; -fx-font-family: 'SF Pro';");

        firstHintWrapper.getChildren().addAll(hintOneHeader, hintOneAnswer);


        // Second hint
        HBox secondHintWrapper = new HBox();
        Label hintTwoHeader = new Label("Example expressions: ");
        hintTwoHeader.setStyle("-fx-text-fill: #374151; -fx-font-size: 16px; -fx-font-family: 'SF Pro Display'; -fx-font-weight: bold");

        Label hintTwoAnswer = new Label("!(A && B) || C,  A || (B && C)");
        hintTwoAnswer.setStyle("-fx-text-fill: #374151; -fx-font-size: 16px; -fx-font-family: 'SF Pro';");

        secondHintWrapper.getChildren().addAll(hintTwoHeader, hintTwoAnswer);

        HBox buttonAndInputDiv = new HBox();
        buttonAndInputDiv.setStyle("-fx-spacing: 12px;");

        HBox hbox = new HBox();
        hbox.setStyle("-fx-min-width: 100%; -fx-spacing: 22px; -fx-alignment: center; -fx-padding: 12px 12px 12px 12px;");

        Label label = new Label("Input Expression");
        label.setStyle("-fx-font-family: 'SF Pro Display'; -fx-font-weight: bold; -fx-font-size: 16px;");

        // Text field
        expressionField = new TextField();
        expressionField.setPromptText("Enter expression (e.g., A && B || C)");
        expressionField.setPrefWidth(600);
        expressionField.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #D0D7E3; -fx-border-width: 1.5; -fx-padding: 10 14; -fx-font-size: 18px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);");

        // Button
        Button parseButton = new Button("Parse");
        parseButton.setStyle("-fx-background-color: #6760D9; -fx-background-radius: 12; -fx-border-radius: 12; -fx-font-weight: bold; -fx-font-size: 18px; -fx-max-height: 40px; -fx-font-family: 'SF Pro Display'; -fx-text-fill: white;");
        parseButton.setOnAction(actionEvent -> {
            try {
                handleParse();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        hintsDiv.getChildren().addAll(firstHintWrapper, secondHintWrapper);
        outer.getChildren().addAll(verticalDiv);
        verticalDiv.getChildren().addAll(label, buttonAndInputDiv, hintsDiv);
        buttonAndInputDiv.getChildren().addAll(expressionField, parseButton);
        vbox.getChildren().addAll(titleBox, subLabel, outer);

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
