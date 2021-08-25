package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/* A JavaFX application for the Valencia Sandwich Shop. 
 * User can order sandwiches by using list boxes and the 
 * application displays the price. Each sandwich should allow 
 * a choice of at least three main ingredients (chicken, for example)
 * at three different prices. The user should also be able to choose 
 * between three different bread types. Use CheckBoxes for 
 * additional ingredients - lettuce, tomato, etc.

   Create an ArrayList to hold all of the sandwiches associated with an order.
   Display information about all the sandwiches that were ordered. 
 * */

public class Main extends Application {

	// Application Launch Area
	public static void main(String[] args) {
		Application.launch(args);
	}

	// Local GUI Elements
	final Button button = new Button("Add To Order");
	final Button button2 = new Button("Submit Order & Exit");
	final Label notification = new Label();
	final Label customerLabel = new Label("Customer Name ");
	final Label baseOptions = new Label("Base Options: ");
	final Label breadOptions = new Label("Bread Options: ");
	final Label order = new Label("Order:");
	final TextField customerNameTF = new TextField();

	// TextArea For Printing Order Info to GUI
	protected static TextArea textArea = new TextArea();
	double textAreaHeight = 300;
	double textAreaWidth = 200;

	// Method to send Order info to textArea
	public static void println(Sandwich s) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				textArea.setText(textArea.getText() + s);
				System.out.println(s); // echo to console
			}
		});
	}

	// Overloaded method to send totalPrice String to textArea
	public static void println(String s) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				textArea.setText(textArea.getText() + s);
				System.out.println(s); // echo to console
			}
		});
	}

	// Overloaded method to send totalPrice Double to textArea
	public static void print(Double d) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				textArea.setText(textArea.getText() + d + "0");
				System.out.print(d); // echo to console
			}
		});
	}

	// Local variables
	protected int counter = 0;
	protected String customerName;
	protected String sandwichBase;
	protected String sandwichBread;
	protected String specialInstructions;
	protected double sandwichPrice;
	protected double totalPrice = 0;

	// Placeholders for Sandwich constructor
	protected boolean tomato;
	protected boolean spinach;
	protected boolean onion;
	protected boolean salt;
	protected boolean pepper;
	protected boolean oldbay;

	// Create ArrayList of Sandwich orders
	ArrayList<Sandwich> orders = new ArrayList<>();

	// Create a stage called window
	// Doing outside start() so local variable for
	// window.close() method @ bottom
	Stage window;

	// Create a BackgroundImage
	BackgroundImage myBI = new BackgroundImage(new Image("background.jpeg", 600, 900, false, true),
			BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

	// Get to it!
	@Override
	public void start(Stage stage) throws Exception {

		// Rename stage to window (easier on brain)
		window = stage;

		// Set Stage Title
		window.setTitle("Valencia Sandwich Shop");

		// Runs closeProgram() whenever window is closed manually (X'd out).
		// Useful if a file needs to be saved prior to quitting app
		window.setOnCloseRequest(e -> {
			// Consume the event to allow ConfirmBox
			// to do its job (via closeProgram())!
			e.consume();

			// Call closeProgram() method below
			closeProgram();
		});

		// Set Scene Title and add to grid
		Text sceneTitle = new Text("Welcome");

		// Set sceneTitle ID for CSS
		sceneTitle.setId("welcome-text");

		// Set default textArea size (for order window)
		textArea.setPrefSize(textAreaWidth, textAreaHeight);

		// Combo Box (List Box) for sandwich base (protein)options
		final ComboBox<String> baseComboBox = new ComboBox<String>();
		baseComboBox.getItems().addAll("Choose One:", "Chicken - $6.00", "Tofu - $6.50", "Steak - $7.00");

		// Combo Box (List Box) for bread options
		final ComboBox<String> breadComboBox = new ComboBox<String>();
		breadComboBox.getItems().addAll("Choose One:", "White", "Wheat", "Italian Five Grain");

		// Set Default Values for List Boxes
		baseComboBox.setValue("Choose One:");
		breadComboBox.setValue("Choose One:");

		// Create Topping Option CheckBoxes
		CheckBox cb1 = new CheckBox("Tomato");
		CheckBox cb2 = new CheckBox("Spinach");
		CheckBox cb3 = new CheckBox("Onion");
		CheckBox cb4 = new CheckBox("Salt");
		CheckBox cb5 = new CheckBox("Pepper");
		CheckBox cb6 = new CheckBox("Old Bay");

		//Labels and Text Areas
		final Label name = new Label("Customer Name:");
		final Label orderLabel = new Label("Orders:");
		
		
		// Create a Vertical Box Container for GUI Elements
		VBox vBox = new VBox(10);

		// Pad up the sides
		vBox.setPadding(new Insets(20, 20, 20, 20));
		
		// Add GUI Elements
		vBox.getChildren().addAll(sceneTitle, baseOptions, baseComboBox, breadOptions, breadComboBox, cb1, cb2, cb3,
				cb4, cb5, cb6, name, customerNameTF, button, notification, orderLabel, textArea, button2);

		// Create two StackPane containers
		StackPane stackPane = new StackPane();
		StackPane stackPane1 = new StackPane();

		// Add background image with opacity (CSS) because
		// opacity on stackPane makes ALL elements opaque as well
		stackPane1.setId("pane");

		// Add stackPane1 and vBox elements to stackPane
		stackPane.getChildren().addAll(stackPane1, vBox);

		// Add stackPane to scene
		Scene scene = new Scene(stackPane, 600, 900);

		// "Add To Order" Button Action
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				// Ensure each sandwich has both bread AND base (protein) selected
				if (baseComboBox.getValue() != "Choose One:" && breadComboBox.getValue() != "Choose One:") {

					// Determine sandwich price & set sandwichBase appropriately
					if (baseComboBox.getValue().toString() == "Chicken - $6.00") {
						sandwichPrice = SandwichConstants.chickenPrice;
						sandwichBase = "Chicken";
					} else if (baseComboBox.getValue().toString() == "Tofu - $6.50") {
						sandwichPrice = SandwichConstants.tofuPrice;
						sandwichBase = "Tofu";
					} else if (baseComboBox.getValue().toString() == "Steak - $7.00") {
						sandwichPrice = SandwichConstants.steakPrice;
						sandwichBase = "Steak";
					} else {
						System.out.println("Error!");
					}

					// Gather variables for Order Constructor
					sandwichBread = breadComboBox.getValue().toString();
					customerName = customerNameTF.getCharacters().toString();

					// Set toppings according to CheckBox selections
					tomato = cb1.isSelected();
					spinach = cb2.isSelected();
					onion = cb3.isSelected();
					salt = cb4.isSelected();
					pepper = cb5.isSelected();
					oldbay = cb6.isSelected();

					// Create a SandwichOrder and add to Array
					orders.add(new SandwichOrder(customerName, sandwichBread, sandwichBase, tomato, spinach, onion,
							salt,
							pepper, oldbay, sandwichPrice));

					// Order Success GUI notification
					notification.setText("Sandwich added to order! =)");
					notification.setId("notification");

					// Increment counter
					counter++;

					// Reset choice options for next entry
					baseComboBox.setValue("Choose One:");
					breadComboBox.setValue("Choose One:");
					customerNameTF.clear();

					// Extracted Method below
					clearCheckBoxes(cb1, cb2, cb3, cb4, cb5, cb6);


				} else {
					// If both bread and base (protein) are not chosen, notify.
					notification.setText("Please choose base and bread for your sandwich!");
					AlertBox.display("Aw Snap!", "Sandwiches must include bread and base!");
				}
				
				// Clear totalPrice
				totalPrice = 0;

				// Print Order Info to textArea
				printOrderLoop();

			}

			private void printOrderLoop() {
				for (int i = 0; i < counter; i++) {
					// Avoid repeat information (1, 1/2, 1/2/3, etc.)
					textArea.clear();
					// Print order list up to current index position
					Main.println(orders.get(i));
					// Sum prices of all sandwiches thus far
					totalPrice += orders.get(i).getSandwichPrice();
					// Print it out!
					Main.println("Total: $");
					Main.print(totalPrice);
					Main.println("\n");
					Main.println("\n");
				}
			}

			private void clearCheckBoxes(CheckBox cb1, CheckBox cb2, CheckBox cb3, CheckBox cb4, CheckBox cb5,
					CheckBox cb6) {
				cb1.setSelected(false);
				cb2.setSelected(false);
				cb3.setSelected(false);
				cb4.setSelected(false);
				cb5.setSelected(false);
				cb6.setSelected(false);
			}
		});

		// Confirm Button Action Events
		button2.setOnAction(e -> {
			ConfirmBox.display("Submit Order", "Confirm order?");
			closeProgram();
		});

		// Set root, scene, stage
		window.setScene(scene);

		// Load CSS
		scene.getStylesheets().addAll(this.getClass().getResource("application.css").toExternalForm());

		// Show stage
		window.show();

	}

	//
	private void closeProgram() {
		// Ask if user wants to exit
		Boolean answer = ConfirmBox.display("Exit?", "Are you sure you want to exit?");
		if (answer) {
			// Run any necessary code before window closes:
			// Save / transfer files, etc...
			System.out.println("Window Closed!");
			window.close();
		}
	}

}