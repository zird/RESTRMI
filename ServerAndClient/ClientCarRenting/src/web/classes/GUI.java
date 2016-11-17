package web.classes;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {
	/* Related to the graphical interface */
	private String cssFile = "bootstrap3.css";
	private HashMap<String, Scene> scenes = new HashMap<>();
	private final double appWidth = 1280;
	private final double appHeight = 720;
	/* Related to the app */
	private Client sessionClient = null;
	private CarsService carsService;
	/* Related to tabs */
	private enum TabName { NONE, ALL_CARS, WAITING_CARS, RENTING_CARS, SEARCH_CARS; }
	private TabName currentTab = TabName.NONE;
	private String currentSearchInput = "";
	private HashMap<TabName, List<RentInformation>> tabs = new HashMap<>();
	
	
	/**
	 * Constructs the GUI.
	 * @throws Exception
	 */
	public GUI() throws Exception {
		carsService = (CarsService) Naming.lookup("rmi://localhost:1099/CarsService");
		initTabsLists();
	}


	@Override
	public void start(Stage stage) throws RemoteException {
		Scene sceneAuth = new Scene(new GridPane(), 400, 400);
		Scene sceneRegister = new Scene(new GridPane(), 400, 400);
		Scene sceneTabs = new Scene(new BorderPane(), appWidth, appHeight);

		sceneAuthInit(stage, sceneAuth);
		sceneRegisterInit(stage, sceneRegister);
		// sceneTabsInit called in sceneAuth after successful login

		scenes.put("authentication", sceneAuth);
		scenes.put("register", sceneRegister);
		scenes.put("tabs", sceneTabs);

		stage.setResizable(false);
		stage.setScene(sceneAuth);
		stage.sizeToScene(); // correct unintended margin caused by setResizable
		stage.show();
	}

	@Override
	public void stop() {
		System.out.println("The GUI was closed.");
		performLogout();
		Platform.exit();
		System.exit(0); // might move this somewhere else
	}
	
	/* Tabs methods ***********************************************************/
	
	private void initTabsLists() throws RemoteException {
		tabs.put(TabName.RENTING_CARS, carsService.listClientRenting(sessionClient));
		tabs.put(TabName.WAITING_CARS, carsService.listClientWaiting(sessionClient));
		tabs.put(TabName.ALL_CARS, carsService.list());
		tabs.put(TabName.SEARCH_CARS, carsService.listSearchResults(currentSearchInput));
	}
	
	private void updateTabList(TabName tabName) throws RemoteException {
		switch(tabName) {
		case RENTING_CARS:
			tabs.put(tabName, carsService.listClientRenting(sessionClient));
			break;
		case WAITING_CARS:
			tabs.put(tabName, carsService.listClientWaiting(sessionClient));
			break;
		case ALL_CARS:
			tabs.put(tabName, carsService.list());
			break;
		case SEARCH_CARS:
			tabs.put(tabName, carsService.listSearchResults(currentSearchInput));
			break;
		default:
			break;
		}
	}
	
	/* Scene initializations **************************************************/
	
	private void sceneAuthInit(Stage stage, Scene scene) {
		/* Basic initialization */
		GridPane pane = (GridPane) scene.getRoot();
		styleGridPane(pane);
		scene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
		stage.setTitle("Authentication");

		/* Elements */
		Text sceneTitle = new Text("Authentication");
		sceneTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 30));
		Label labelLogin = new Label("Login");
		TextField fieldLogin = new TextField();
		Label labelPassword = new Label("Password");
		PasswordField fieldPassword = new PasswordField();
		Text resultText = new Text("Authentication failure. Retry.");
		resultText.setVisible(false);
		resultText.setFill(Color.RED);

		/* Buttons */
		Button btnLogin = new Button("Login");
		Button btnRegister = new Button("Register");
		btnLogin.getStyleClass().setAll("button", "primary");
		btnRegister.getStyleClass().setAll("button", "info");

		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.BOTTOM_RIGHT);
		hbox.getChildren().add(btnRegister);
		hbox.getChildren().add(btnLogin);

		pane.add(sceneTitle, 0, 0, 2, 1);
		pane.add(labelLogin, 0, 1);
		pane.add(fieldLogin, 1, 1);
		pane.add(labelPassword, 0, 2);
		pane.add(fieldPassword, 1, 2);
		pane.add(resultText, 1, 3);
		pane.add(hbox, 1, 4);

		/* Disabling buttons on conditions */
		btnLogin.disableProperty().bind(
				Bindings.isEmpty(fieldLogin.textProperty())
			.or(Bindings.isEmpty(fieldPassword.textProperty())
		));

		/* Event handlers */
		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				try {
					Client clientTryLogIn = carsService.logIn(fieldLogin.getText(), fieldPassword.getText());
					if (null == clientTryLogIn) {
						resultText.setVisible(true);
						System.out.println("Login FAILED");
					} else {
						System.out.println("Login SUCCESS : " + fieldLogin.getText() + " " + fieldPassword.getText());
						sessionClient = clientTryLogIn; // set current client
						// reset all elements
						fieldLogin.setText("");
						fieldPassword.setText("");
						resultText.setVisible(false);
						sceneTabsInit(stage, scenes.get("tabs")); 
						stage.setScene(scenes.get("tabs"));
					}
				} catch (RemoteException e) {
					System.err.println("Exception : " + e);
				}
			}
		});

		btnRegister.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				// reset all elements
				fieldLogin.setText("");
				fieldPassword.setText("");
				resultText.setVisible(false);
				stage.setScene(scenes.get("register"));
			}
		});
	}

	private void sceneRegisterInit(Stage stage, Scene scene) {
		/* Basic initialization */
		GridPane pane = (GridPane) scene.getRoot();
		styleGridPane(pane);
		scene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
		stage.setTitle("Register");

		/* Elements */
		Text sceneTitle = new Text("Register");
		sceneTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 30));
		Label labelLogin = new Label("Login");
		Label labelPassword = new Label("Password");
		Label labelFirstName = new Label("First name");
		Label labelLastName = new Label("Last name");
		Label labelStatus = new Label("Status");
		TextField fieldLogin = new TextField();
		TextField fieldFirstName = new TextField();
		TextField fieldLastName = new TextField();
		PasswordField fieldPassword = new PasswordField();
		ToggleGroup groupStatus = new ToggleGroup();
		RadioButton radioBtn1 = new RadioButton("Student");
		RadioButton radioBtn2 = new RadioButton("Professor");
		RadioButton radioBtn3 = new RadioButton("Outsider");
		radioBtn1.setToggleGroup(groupStatus);
		radioBtn1.setSelected(true);
		radioBtn2.setToggleGroup(groupStatus);
		radioBtn3.setToggleGroup(groupStatus);
		Text resultText = new Text("This client already exists.");
		resultText.setVisible(false);
		resultText.setFill(Color.RED);

		/* Buttons */
		Button btnRegister = new Button("Confirm");
		Button btnBack = new Button("Back");
		btnRegister.getStyleClass().setAll("button", "success");
		btnBack.getStyleClass().setAll("button", "default");

		HBox hboxButtons = new HBox(10);
		hboxButtons.setAlignment(Pos.BOTTOM_RIGHT);
		hboxButtons.getChildren().add(btnBack);
		hboxButtons.getChildren().add(btnRegister);

		HBox hboxRadio = new HBox(radioBtn1, radioBtn2, radioBtn3);

		pane.add(sceneTitle, 0, 0, 2, 1);
		pane.add(labelLogin, 0, 1);
		pane.add(fieldLogin, 1, 1);
		pane.add(labelPassword, 0, 2);
		pane.add(fieldPassword, 1, 2);
		pane.add(labelFirstName, 0, 3);
		pane.add(fieldFirstName, 1, 3);
		pane.add(labelLastName, 0, 4);
		pane.add(fieldLastName, 1, 4);
		pane.add(labelStatus, 0, 5);
		pane.add(hboxRadio, 1, 5);
		pane.add(resultText, 1, 6);
		pane.add(hboxButtons, 1, 7);

		/* Disabling buttons on conditions */
		btnRegister.disableProperty().bind(
				Bindings.isEmpty(fieldLogin.textProperty())
			.or(Bindings.isEmpty(fieldPassword.textProperty())
			.or(Bindings.isEmpty(fieldFirstName.textProperty())
			.or(Bindings.isEmpty(fieldLastName.textProperty())
		))));

		/* Event handlers */
		btnRegister.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				Status status;
				String selectedToggle = (String) ((RadioButton) groupStatus.getSelectedToggle()).getText();
				switch (selectedToggle) {
				case "Student":
					status = Status.STUDENT;
					break;
				case "Professor":
					status = Status.PROFESSOR;
					break;
				case "Outsider":
					status = Status.OUTSIDER;
					break;
				default:
					status = Status.OUTSIDER;
				}
				try {
					
					if (false == carsService.addClient(fieldLogin.getText(), fieldPassword.getText(),
							fieldFirstName.getText(), fieldLastName.getText(), status)) {
						resultText.setVisible(true);
						System.out.println("Registering FAILED : already exists");
					} else {
						System.out.println("Registering SUCCESS");
						stage.setScene(scenes.get("authentication"));
					}
				} catch (RemoteException e) {
					System.err.println("Exception : " + e);
				}
			}
		});

		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				// reset all elements
				fieldLogin.setText("");
				fieldPassword.setText("");
				fieldFirstName.setText("");
				fieldLastName.setText("");
				resultText.setVisible(false);
				groupStatus.selectToggle(radioBtn1);
				stage.setScene(scenes.get("authentication"));
			}
		});
	}

	private void sceneTabsInit(Stage stage, Scene scene) throws RemoteException {
		BorderPane pane = (BorderPane) scene.getRoot();
		pane.setStyle("-fx-background-color: white;");
		scene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
		stage.setTitle("Application");

		/* Identity + Logout button ************/
		HBox identityHBox = new HBox(40);
		HBox buttonsBox = new HBox(5);
		identityHBox.setPadding(new Insets(15, 15, 15, 40));
		Text textIdentity;
		if (null != sessionClient) {
			textIdentity = new Text(sessionClient.getFirstname() + " " + sessionClient.getLastname());
		} else {
			textIdentity = new Text("Error Identity");
		}
		textIdentity.setFont(Font.font("Arial", FontWeight.NORMAL, 25));
		Button btnLogout = new Button("Logout");
		btnLogout.getStyleClass().setAll("button", "danger", "sm");
		Button btnRefresh = new Button("Refresh");
		btnRefresh.getStyleClass().setAll("button", "info", "sm");
		buttonsBox.getChildren().addAll(btnLogout, btnRefresh);
		identityHBox.getChildren().addAll(textIdentity, buttonsBox);

		/* Tabs ********************************/
		TabPane tabPane = new TabPane();
		Tab tab1 = new Tab("All cars");
		Tab tab2 = new Tab("My current cars");
		Tab tab3 = new Tab("My waiting list");
		Tab tab4 = new Tab("Search cars");
		tab1.setClosable(false);
		tab2.setClosable(false);
		tab3.setClosable(false);
		tab4.setClosable(false);
		tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);
		
		// set content of default tab : tab1
		currentTab = TabName.ALL_CARS;
		tab1.setContent(getTabContent());
		
		// Listener on tab selection
		tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			if (newTab.equals(tab1)) { // if tab "All cars"
				try {
					currentTab = TabName.ALL_CARS;
					tab1.setContent(getTabContent());
				} catch (RemoteException e) {
					System.err.println("Exception : " + e);
				}
			} else if (newTab.equals(tab2)) {  // if tab "My current cars"
				try {
					currentTab = TabName.RENTING_CARS;
					tab2.setContent(getTabContent());
				} catch (RemoteException e) {
					System.err.println("Exception : " + e);
				}
			} else if (newTab.equals(tab3)) { // if tab "Waiting list"
				try {
					currentTab = TabName.WAITING_CARS;
					tab3.setContent(getTabContent());
				} catch (RemoteException e) {
					System.err.println("Exception : " + e);
				}
			} else {
				try {
					currentTab = TabName.SEARCH_CARS;
					tab4.setContent(getTabContent());
				} catch (RemoteException e) {
					System.err.println("Exception : " + e);
				}
			}
		});

		// Add components to the BorderPane
		pane.setTop(identityHBox);
		pane.setCenter(tabPane);

		/* Button clicks ***********************/
		btnLogout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				performLogout();
				stage.setScene(scenes.get("authentication"));
				scenes.remove("tabs");
				scenes.put("tabs", new Scene(new BorderPane(), appWidth, appHeight));
			}

		});
		
		btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				if (currentTab == TabName.ALL_CARS) { // if tab "All cars"
					try {
						tab1.setContent(getTabContent());
					} catch (RemoteException e) {
						System.err.println("Exception : " + e);
					}
				} else if (currentTab == TabName.RENTING_CARS) {  // if tab "My current cars"
					try {
						tab2.setContent(getTabContent());
					} catch (RemoteException e) {
						System.err.println("Exception : " + e);
					}
				} else { // if tab "Waiting list"
					try {
						tab3.setContent(getTabContent());
					} catch (RemoteException e) {
						System.err.println("Exception : " + e);
					}
				}
			}

		});
	}
	
	private void performLogout() {
		currentTab = TabName.NONE;
		try {
			if (null != sessionClient) {
				carsService.logOut(sessionClient.getLogin());
			}
		} catch (RemoteException e) {
			System.err.println("Exception : " + e);
		}
		sessionClient = null;
	}
	
	/**
	 * Get the content of the tab listing all cars
	 * @return the content of the tab listing all cars
	 * @throws RemoteException
	 */
	private ScrollPane getTabContent() throws RemoteException {
		ScrollPane scrollPane = new ScrollPane();
		styleScrollPane(scrollPane);
		Accordion accordion = new Accordion();
		styleAccordion(accordion);
		updateTabList(currentTab); // update the list of RentInfos for the tab
		
		if (TabName.SEARCH_CARS != currentTab && tabs.get(currentTab).isEmpty()) {
			Text message = new Text("The list is empty.");
			Text message2 = new Text("Check all cars to rent a car !");
			message.setFont(Font.font("Arial", FontWeight.NORMAL, 60));
			message2.setFont(Font.font("Arial", FontWeight.NORMAL, 40));
			message.setFill(Color.gray(0.5,0.2));
			message2.setFill(Color.gray(0.5,0.2));
			VBox vbox = new VBox();
			vbox.getChildren().addAll(message, message2);
			vbox.setAlignment(Pos.CENTER);
			BorderPane emptyListBox = new BorderPane();
			emptyListBox.setPrefWidth(appWidth);
			emptyListBox.setPrefHeight(appHeight-105);
			emptyListBox.setStyle("-fx-background-color:white;");
			emptyListBox.setCenter(vbox);
			scrollPane.setContent(emptyListBox);
		} else {
			refreshAccordion(accordion);
			scrollPane.setContent(accordion);
		}
		
		return scrollPane;
	}

	/**
	 * Add the pane containing the search bar.
	 * @param accordion the Accordion object to add the search bar in
	 * @throws RemoteException
	 */
	private void addSearchBar(Accordion accordion) throws RemoteException {
		TitledPane searchBarPane = new TitledPane("Search", new Rectangle(300,400,Color.GRAY));
		styleTitledPane(searchBarPane);
		
		GridPane searchBarContent = getSearchBarContent(searchBarPane, accordion);
		searchBarPane.setContent(searchBarContent);
		accordion.getPanes().add(0, searchBarPane); // add to head
	}
	
	/**
	 * Add the pane containing the form to add a new car, in the specified Accordion object
	 * @param accordion
	 * @throws RemoteException
	 */
	private void addFormNewCar(Accordion accordion) throws RemoteException {
		TitledPane newCarPane = new TitledPane("Add a new car...", new Rectangle(300,400,Color.GRAY));
		styleTitledPane(newCarPane);
		
		GridPane newCarPaneContent = getFormNewCarContent(newCarPane, accordion);
		newCarPane.setContent(newCarPaneContent);
		accordion.getPanes().add(0, newCarPane); // add to head
	}

	/**
	 * Get a pane with the form to add a car
	 * @param parentToCollapse the parent pane to collapse on success
	 * @param accordion the accordion containing the list of all cars
	 * @return a pane with the form to add a car
	 */
	private GridPane getFormNewCarContent(TitledPane parentToCollapse, Accordion accordion) {
		/* content of the titled pane */
		GridPane newCarPaneContent = new GridPane();
		newCarPaneContent.setHgap(15);
		newCarPaneContent.setVgap(5);
		Button btnAdd = new Button("Add");
		btnAdd.getStyleClass().addAll("button", "success", "lg");
		Label labelBrand = new Label("Brand");
		Label labelModel = new Label("Model");
		Label labelLicense = new Label("License plate");
		Label labelPrice = new Label("Price");
		Label labelFirstCirculation = new Label("First circulation");
		TextField fieldBrand = new TextField();
		TextField fieldModel = new TextField();
		TextField fieldLicense = new TextField();
		TextField fieldPrice = new TextField();
		Text textFail = new Text("Failed to add this new car : it already exists.");
		textFail.setFill(Color.RED);
		textFail.setVisible(false);
		DatePicker datePicker = new DatePicker();
		Calendar datePickerResult = new GregorianCalendar();
		datePicker.setOnAction(event -> {
		    LocalDate localDate = datePicker.getValue();
		    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		    datePickerResult.setTime(date);
		});
		newCarPaneContent.add(labelBrand, 0, 1);
		newCarPaneContent.add(fieldBrand, 1, 1);
		newCarPaneContent.add(labelModel, 0, 2);
		newCarPaneContent.add(fieldModel, 1, 2);
		newCarPaneContent.add(labelLicense, 0, 3);
		newCarPaneContent.add(fieldLicense, 1, 3);
		newCarPaneContent.add(labelPrice, 0, 4);
		newCarPaneContent.add(fieldPrice, 1, 4);
		newCarPaneContent.add(labelFirstCirculation, 0, 5);
		newCarPaneContent.add(datePicker, 1, 5);
		newCarPaneContent.add(textFail, 1, 6);
		newCarPaneContent.add(btnAdd, 1, 7);
		
		// force numeric input only
		fieldPrice.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d+(\\.\\d{1,2})?")) {
	                fieldPrice.setText(newValue.replaceAll("[^\\d\\.]", ""));
	            }
	        }
	    });
		
		/* Disabling buttons on conditions */
		btnAdd.disableProperty().bind(
				Bindings.isEmpty(fieldBrand.textProperty())
			.or(Bindings.isEmpty(fieldModel.textProperty())
			.or(Bindings.isEmpty(fieldPrice.textProperty())
			.or(Bindings.isEmpty(fieldLicense.textProperty())
			.or(datePicker.valueProperty().isNull())
			))));
		
		/* Button listener */
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				System.out.println("Adding a car...");
				try {
					if (true == carsService.addCar(fieldLicense.getText().toUpperCase(), fieldBrand.getText().toUpperCase(), fieldModel.getText().toUpperCase(), datePickerResult, Double.parseDouble(fieldPrice.getText())) ) {
						System.out.println("Adding car SUCCESS");
						textFail.setVisible(false); // hide error msg
						resetAddCarForm(fieldBrand, fieldModel, fieldLicense, fieldPrice, datePicker);
						parentToCollapse.setExpanded(false); // collapse pane
						refreshAccordion(accordion);
					} else {
						textFail.setVisible(true); // show error msg
						System.out.println("Adding car FAILURE");
					}
				} catch (Exception e) {
					System.err.println("Exception : " + e);
				}
			}

			private void resetAddCarForm(TextField fieldBrand, TextField fieldModel, TextField fieldLicense,
					TextField fieldPrice, DatePicker datePicker) {
				fieldBrand.setText("");
				fieldModel.setText("");
				fieldPrice.setText("");
				fieldLicense.setText("");
				datePicker.getEditor().clear();
			}
		});
		return newCarPaneContent;
	}
	
	/**
	 * Get a pane with the form to search a car
	 * @param parentToCollapse the parent pane to collapse on success
	 * @param accordion the accordion containing the list of all cars
	 * @return a pane with the form to add a car
	 */
	private GridPane getSearchBarContent(TitledPane parentToCollapse, Accordion accordion) {
		/* content of the titled pane */
		GridPane searchBarContent = new GridPane();
		searchBarContent.setHgap(15);
		searchBarContent.setVgap(5);
		Button btnSearch = new Button("Search");
		btnSearch.getStyleClass().addAll("button", "success", "lg");
		Label labelSearch = new Label("Search");
		TextField fieldSearch = new TextField();
		searchBarContent.add(labelSearch, 0, 1);
		searchBarContent.add(fieldSearch, 1, 1);
		searchBarContent.add(btnSearch, 1, 2);
		
		/* Disabling buttons on conditions */
		btnSearch.disableProperty().bind(Bindings.isEmpty(fieldSearch.textProperty()));
		
		/* Button listener */
		btnSearch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				currentSearchInput = fieldSearch.getText();
				try {
					parentToCollapse.setExpanded(false); // collapse pane
					refreshAccordion(accordion);
				} catch (RemoteException e) {
					System.err.println("Exception : " + e);
				}
				// we chose not to reset the field after searching
			}

		});
		return searchBarContent;
	}

	/**
	 * Fills the accordion with cars
	 * @param accordion Accordion containing the cars
	 * @param rentInfoList 
	 * @throws RemoteException
	 */
	private void fillAccordionWithCars(Accordion accordion, List<RentInformation> rentInfoList) throws RemoteException {
		for(RentInformation rentInfo : rentInfoList) {
			Car car = rentInfo.getCar();
			
			/* the titled pane */
			TitledPane carPane = new TitledPane(car.getBrand() + " " + car.getModel(), new Rectangle(300,400,Color.GRAY));
			refreshCarPaneColor(car, carPane);
			styleTitledPane(carPane);
			carPane.setContent(addCarPaneButtons(getCarPaneContent(car), car, accordion));
			accordion.getPanes().add(carPane);
		}
	}
	
	/**
	 * Get the content for the car pane, containing the details of the car
	 * @param car the car contained in the car pane
	 * @return a grid pane with the details of the car
	 * @throws RemoteException
	 */
	private GridPane getCarPaneContent(Car car) throws RemoteException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
		
		/* content of the titled pane */
		GridPane carPaneContent = new GridPane();
		carPaneContent.setHgap(15);
		carPaneContent.setVgap(5);
		Label labelBrand = new Label("Brand");
		Label labelModel = new Label("Model");
		Label labelLicense = new Label("License plate");
		Label labelFirstCirculate = new Label("First circulation");
		Label labelPrice = new Label("Price");
		Label labelAvailable = new Label("Available");
		Text textBrand = new Text(car.getBrand());
		Text textModel = new Text(car.getModel());
		Text textLicense = new Text(car.getLicensePlate());
		Text textFirstCirculate = new Text(formatter.format(car.getFirstCirculationDate().getTime()));
		Text textPrice = new Text(car.getPrice() + " EUR");
		Text textAvailable = new Text(car.isAvailable() ? "Yes":"No");
		textAvailable.setFill(car.isAvailable() ? Color.GREEN:Color.RED);
		
		carPaneContent.add(labelBrand, 0, 0);
		carPaneContent.add(textBrand, 1, 0);
		carPaneContent.add(labelModel, 0, 1);
		carPaneContent.add(textModel, 1, 1);
		carPaneContent.add(labelLicense, 0, 2);
		carPaneContent.add(textLicense, 1, 2);
		carPaneContent.add(labelFirstCirculate, 0, 3);
		carPaneContent.add(textFirstCirculate, 1, 3);
		carPaneContent.add(labelPrice, 0, 4);
		carPaneContent.add(textPrice, 1, 4);
		carPaneContent.add(labelAvailable, 0, 5);
		carPaneContent.add(textAvailable, 1, 5);
		
		return carPaneContent;
	}
	
	/**
	 * Add a button to rent the car, and to return it, in the pane with the details of the car
	 * @param carPaneContent the pane containing the details of the car
	 * @param car the car contained in the car pane
	 * @return the specified grid pane, but with the additional button
	 * @throws RemoteException 
	 */
	private GridPane addCarPaneButtons(GridPane carPaneContent, Car car, Accordion accordion) throws RemoteException {
		Button btnRent = new Button();
		Button btnReturn = new Button("Return this car");
		btnRent.getStyleClass().setAll("button", "warning");
		btnReturn.getStyleClass().setAll("button","primary");
		
		refreshCarPaneButtons(car, btnRent, btnReturn);
		
		carPaneContent.add(btnRent, 3, 1);
		carPaneContent.add(btnReturn, 4, 1);
		
		/* Event handlers */
		btnRent.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				try {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText(null);
					RentStatus rentResult = carsService.rent(sessionClient, car.getLicensePlate());
					switch(rentResult) {
					case SUCCESS:
						alert.setContentText("You have been granted the car : " + car.getBrand() + " " + car.getModel() + ".");
						break;
					case WAITING_QUEUE:
						alert.setAlertType(AlertType.WARNING);
						alert.setContentText("The car " + car.getBrand() + " " + car.getModel() + " is already in use. You have been put in the waiting queue for this car.");
						break;
					case ALREADY_WAITING_QUEUE:
						alert.setAlertType(AlertType.ERROR);
						alert.setContentText("You are already queueing up for this car.");
						break;
					case ALREADY_RENTING:
						alert.setAlertType(AlertType.ERROR);
						alert.setContentText("You are already renting this car.");
						break;
					default:
						alert.setAlertType(AlertType.ERROR);
						alert.setContentText("The car does not exist. This should not happen.");
					}
					refreshAccordion(accordion);
					alert.showAndWait();
				} catch (RemoteException e) {
					System.err.println("Exception : " + e);
				} 
			}
		});
		
		btnReturn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText(null);
					if (true == carsService.returnCar(sessionClient, car.getLicensePlate())) {
						alert.setContentText("You have returned the car : " + car.getBrand() + " " + car.getModel() + ".");
					} else {
						alert.setAlertType(AlertType.ERROR);
						alert.setContentText("Error returning the car.");
					}
					alert.showAndWait();
					refreshAccordion(accordion);
				} catch (RemoteException e) {
					System.err.println("Exception : " + e);
				}
			}
		});
		
		return carPaneContent;
	}

	/* Refreshing stuff *******************************************************/
	
	private void refreshCarPaneColor(Car car, TitledPane carPane) throws RemoteException {
		if (car.isAvailable()) {
			carPane.getStyleClass().add("success");
		} else {
			carsService.getRentStatus(sessionClient, car.getLicensePlate());
			switch(carsService.getRentStatus(sessionClient, car.getLicensePlate())) {
			case ALREADY_WAITING_QUEUE:
				carPane.getStyleClass().add("warning");
				break;
			case ALREADY_RENTING:
				carPane.getStyleClass().add("info");
				break;
			default:
				carPane.getStyleClass().add("danger");
			}
		}
	}

	private void refreshAccordion(Accordion accordion) throws RemoteException {
		updateTabList(currentTab); // update the list of RentInfos for the tab
		accordion.getPanes().clear();
		
		if (currentTab == TabName.ALL_CARS) {
			addFormNewCar(accordion);
		}
		if (currentTab == TabName.SEARCH_CARS) {
			addSearchBar(accordion);
		}
		
		fillAccordionWithCars(accordion, tabs.get(currentTab));
	}
	
	private void refreshCarPaneButtons(Car car, Button btnRent, Button btnReturn) throws RemoteException {
		if (car.isAvailable()) {
			btnRent.setText("Rent this car now");
			btnRent.setDisable(false);
			btnReturn.setVisible(false);
		} else {
			switch(carsService.getRentStatus(sessionClient, car.getLicensePlate())) {
			case ALREADY_WAITING_QUEUE:
				btnRent.setText("Already queueing for this car");
				btnRent.setDisable(true);	
				btnReturn.setVisible(false);
				break;
			case ALREADY_RENTING:
				btnRent.setText("Already renting this car");
				btnRent.setDisable(true);
				btnReturn.setVisible(true);
				break;
			default:
				btnRent.setText("Queue up to rent this car");
				btnRent.setDisable(false);
				btnReturn.setVisible(false);
			}
		}
	}

	/* Styling methods *********************************************************/
	
	private void styleGridPane(GridPane pane) {
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setPadding(new Insets(25, 25, 25, 25));
	}
	
	private void styleTitledPane(TitledPane pane) throws RemoteException {
		pane.getStyleClass().add("primary");
		pane.setPrefWidth(appWidth-35);
		pane.setPrefHeight(80);
		pane.setStyle("-fx-padding: 20 20 0 20; -fx-font-size:20;");
	}
	
	private void styleAccordion(Accordion accordion) {
		accordion.setStyle("-fx-background-color: white;");
		accordion.setPrefWidth(appWidth-10);
		accordion.setPrefHeight(appHeight-110);
	}

	private void styleScrollPane(ScrollPane scrollPane) {
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setStyle("-fx-unit-increment:3");
		scrollPane.setStyle("-fx-block-increment:100");
	}

	/**
	 * Start the GUI. Open authentication window.
	 * 
	 * @throws RemoteException
	 */
	public void start() throws RemoteException {
		launch();
	}

}
