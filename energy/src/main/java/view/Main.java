package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import db.DBUtil;
import db.JPAUtil;
import de.faeuster.eng.energy.Place;
import de.faeuster.eng.energy.PowerPlant;
import de.faeuster.eng.energy.PowerUnit;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {
	JPAUtil util;
	EntityManagerFactory emf;
	EntityManager em;
	DBUtil service;
	private List<PowerPlant> plants = new ArrayList<>();
	private double totalPower;
	private TextField totalPowerTF;

	public static void main(String[] args) {
		launch(args);
	}

	public void init() throws Exception {
		util = new JPAUtil();

		emf = Persistence.createEntityManagerFactory("PostTest");
		em = emf.createEntityManager();
		service = new DBUtil(em);
		em.getTransaction().begin();
		service.createTestdata();
		em.getTransaction().commit();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		init();
		primaryStage = new Stage();
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		TableView<PowerUnit> tableView = new TableView<PowerUnit>();
		TableColumn<PowerUnit, String> nameColumn = new TableColumn<PowerUnit, String>("ID");
		nameColumn.setCellValueFactory(new PropertyValueFactory<PowerUnit, String>("id"));
		TableColumn<PowerUnit, String> powerColumn = new TableColumn<PowerUnit, String>("Leistung");
		powerColumn.setCellValueFactory(new PropertyValueFactory<PowerUnit, String>("power"));
		TableColumn<PowerUnit, String> producerColumn = new TableColumn<PowerUnit, String>("Hersteller");
		producerColumn.setCellValueFactory(new PropertyValueFactory<PowerUnit, String>("producer"));
		TableColumn<PowerUnit, String> typeColumn = new TableColumn<PowerUnit, String>("Type");
		typeColumn.setCellValueFactory(new PropertyValueFactory<PowerUnit, String>("type"));
		TableColumn<PowerUnit, Place> placeColumn = new TableColumn<PowerUnit, Place>("Ort");
		placeColumn.setCellValueFactory(new PropertyValueFactory<PowerUnit, Place>("place"));
		placeColumn.setCellFactory(new Callback<TableColumn<PowerUnit, Place>, TableCell<PowerUnit, Place>>() {
			@Override
			public TableCell<PowerUnit, Place> call(TableColumn<PowerUnit, Place> param) {
				TableCell<PowerUnit, Place> cityCell = new TableCell<PowerUnit, Place>() {

					@Override
					protected void updateItem(Place item, boolean empty) {
						if (item != null) {
							Label cityLabel = new Label(item.getStreet() + ", " + item.getHousenumber() + ", "
									+ item.getCity() + ", " + item.getPostalCode() + ", " + item.getCountry());
							setGraphic(cityLabel);
						}
					}
				};
				return cityCell;
			}
		});
		placeColumn.setMinWidth(260);
		TableColumn<PowerUnit, Integer> priceColumn = new TableColumn<PowerUnit, Integer>("Kaufpreis");
		priceColumn.setCellValueFactory(new PropertyValueFactory<PowerUnit, Integer>("price"));
		TableColumn<PowerUnit, Integer> workingdurationColumn = new TableColumn<PowerUnit, Integer>("Betriebsdauer");
		workingdurationColumn.setCellValueFactory(new PropertyValueFactory<PowerUnit, Integer>("workingduration"));
		tableView.getColumns().add(nameColumn);
		tableView.getColumns().add(producerColumn);
		tableView.getColumns().add(typeColumn);
		tableView.getColumns().add(placeColumn);
		tableView.getColumns().add(powerColumn);
		tableView.getColumns().add(priceColumn);
		tableView.getColumns().add(workingdurationColumn);
		root.setCenter(tableView);
		Collection<PowerPlant> emps = service.findAllPowerPlants();
		for (PowerPlant e : emps) {
			plants.add(e);
		}
		ListView<PowerPlant> listView = new ListView<>(FXCollections.observableArrayList(plants));
		listView.setCellFactory(param -> new ListCell<PowerPlant>() {
			@Override
			protected void updateItem(PowerPlant item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(String.valueOf(item.getId()));
				}
			}
		});
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PowerPlant>() {
			@Override
			public void changed(ObservableValue<? extends PowerPlant> observable, PowerPlant oldValue,
					PowerPlant newValue) {
				totalPower = 0;
				tableView.setItems(FXCollections.observableList(newValue.getUnits()));
				List<PowerUnit> units = newValue.getUnits();
				for (PowerUnit e : units) {
					totalPower += e.getPower();
				}
				totalPowerTF.setText(String.valueOf(totalPower));

			}
		});
		listView.setMaxWidth(50);
		HBox box = new HBox();
		totalPowerTF = new TextField();
		box.getChildren().add(totalPowerTF);
		root.setBottom(box);
		root.setLeft(listView);
		primaryStage.show();
	}

}
