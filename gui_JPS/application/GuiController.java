/**
 * Sample Skeleton for "simple.fxml" Controller Class
 * Use copy/paste to copy paste this code into your favorite IDE
 **/

package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import domæne.KompetentMedarbejder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kartotek.Systemkartotek_oprettelser;
import kartotek.Systemkartotek_opslag;
import kartotek.Systemkartotek_redigering;
import kartotek.Systemkartotek_søgning;
import logik.Redigering;
import old.files.Kompetence;

public class GuiController implements Initializable {
	private final ObservableList<KompetentMedarbejder> data = FXCollections.observableArrayList();
	@FXML
	private TableView<KompetentMedarbejder> tv = new TableView<KompetentMedarbejder>();
	@FXML
	private DialogPane DP;
	@FXML // fx:id="myButton"
	private Button myButton; // Value injected by FXMLLoader
	@FXML
	private TextField tf;
	@FXML
	private Button sletButton;

	@FXML // fx:id="myButton"
	private Button findMedArb; // Value injected by FXMLLoader

	@FXML // fx:id="myButton"
	private Button jMedArb; // Value injected by FXMLLoader

	@FXML // fx:id="myButton"
	private Button afdelinger; // Value injected by FXMLLoader

	@FXML // fx:id="myButton"
	private MenuItem jKompetencer; // Value injected by FXMLLoader
	@FXML
	private MenuItem editKomp;
	@FXML
	private MenuItem nyAfd;
	@FXML 
	private MenuItem redAfd;
	@FXML
	private TreeView<String> tree;
	@FXML
	private Button rediger;
	@FXML
	private TextArea LW;
	
	@FXML
	private Label info;

	TreeItem<String> item;
	TreeItem<String> kompetence = null;
	List<Integer> kompetencerValgt = new ArrayList();
	

	@Override // This method is called by the FXMLLoader when initialization is
				// complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		Systemkartotek_redigering re = new Systemkartotek_redigering();
		Systemkartotek_opslag sysoOps = new Systemkartotek_opslag();
		try {
			re.setKompetence("");
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		Kompetence k;
		Systemkartotek_oprettelser sysoOp = new Systemkartotek_oprettelser();



		TableColumn ID = new TableColumn("Medarbejder-ID");
		ID.setCellValueFactory(new PropertyValueFactory<KompetentMedarbejder, String>("ID"));
		TableColumn navn = new TableColumn("Navn");
		navn.setCellValueFactory(new PropertyValueFactory<KompetentMedarbejder, String>("navn"));
		TableColumn email = new TableColumn("E-mail");
		email.setCellValueFactory(new PropertyValueFactory<KompetentMedarbejder, String>("email"));
		TableColumn kompetencer = new TableColumn("Kompetencer");
		kompetencer.setCellValueFactory(new PropertyValueFactory<KompetentMedarbejder, String>("kompetencer"));
		tv.getColumns().addAll(ID, navn, email, kompetencer);
		tv.setPlaceholder(new Label("Vælg en kompetence i menuen til venstre."));

		try {
			createTree();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		tree.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {

					data.clear();
					item = tree.getSelectionModel().getSelectedItem();
					String kompetenceTilData = item.getValue().toString();
					
					try {
						re.setKompetence(kompetenceTilData);
						re.setAfdeling(kompetenceTilData);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					ArrayList kompetence = new ArrayList();
					String kompetenceItem = item.getValue();
					try {
						kompetencerAdder(kompetenceItem);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						kompetencerFinder(kompetencerValgt);

						Systemkartotek_opslag sysoOp = new Systemkartotek_opslag();

						tv.setItems(data);
						tv.setPlaceholder(new Label("Ingen medarbejdere har den valgte kompetence."));

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});

		
		

		tv.setRowFactory(tv -> {

			TableRow<KompetentMedarbejder> row = new TableRow<>();
			ArrayList listv = new ArrayList();

			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {

					KompetentMedarbejder rowData = row.getItem();
					try {

						String navntf = rowData.getNavn().toString();
						String IDtf = rowData.getID().toString();
						re.setMedarbejder(IDtf);
						tf.setText("Navn: " + navntf + " / ID: " + IDtf);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						tf.setText("Navn:  / ID: ");
					}
				}
			});
			return row;
		});

		editKomp.setOnAction(ae -> {
			try {
				if(sysoOps.erIKompetence(re.getKompetence())){
				try {
					info.setText("");
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RedigerKompetence.fxml"));

					Parent root1 = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					

					// stage.initModality(Modality.APPLICATION_MODAL);

					stage.setTitle("Find medarbejdere.");
					stage.setScene(new Scene(root1));

					
					stage.show();

				} catch (Exception e) {
					e.printStackTrace();
				}
				} else{
					info.setText("Vælg venligst en kompetence at redigere.");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		});
		
		rediger.setOnAction(ae -> {
			try {
				
				try {
					info.setText("");
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("redigermedarbejder.fxml"));

					Parent root1 = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					

					// stage.initModality(Modality.APPLICATION_MODAL);

					stage.setTitle("Find medarbejdere.");
					stage.setScene(new Scene(root1));

					
					stage.show();

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				}
			 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		});
		
		redAfd.setOnAction(ae -> {
			try {
				if(sysoOps.erIAfdeling(re.getAfdeling())){
				try {
					info.setText("");
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RedigerAfdeling.fxml"));

					Parent root1 = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					

					// stage.initModality(Modality.APPLICATION_MODAL);

					stage.setTitle("Find medarbejdere.");
					stage.setScene(new Scene(root1));

					
					stage.show();

				} catch (Exception e) {
					e.printStackTrace();
				}
				} else{
					info.setText("Vælg venligst en afdeling at redigere.");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		});
		

		jKompetencer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NyKompetence.fxml"));
					Parent root1 = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);

					stage.setTitle("Find medarbejdere.");
					stage.setScene(new Scene(root1));
					stage.show();
				} catch (Exception e) {
					System.out.println("error opening new window.");
				}
			}
		});
		
jMedArb.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("That was easy, wasn't it?");
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("opretmedarbejder.fxml"));
					Parent root1 = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);

					stage.setTitle("Find medarbejdere.");
					stage.setScene(new Scene(root1));
					stage.show();
				} catch (Exception e) {
					System.out.println("error opening new window.");
				}
			}
		});
		
nyAfd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("That was easy, wasn't it?");
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OpretNyAfdeling.fxml"));
					Parent root1 = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);

					stage.setTitle("Find medarbejdere.");
					stage.setScene(new Scene(root1));
					stage.show();
				} catch (Exception e) {
					System.out.println("error opening new window.");
				}
			}
		});

		findMedArb.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("That was easy, wasn't it?");
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("findMedArb.fxml"));
					Parent root1 = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);

					stage.setTitle("Find medarbejdere.");
					stage.setScene(new Scene(root1));
					stage.show();
				} catch (Exception e) {
					System.out.println("error opening new window.");
				}
			}
		});

	}

	public void createTree(String... rootItems) throws SQLException {
		Systemkartotek_opslag sysoOp = new Systemkartotek_opslag();

		Iterator entries = sysoOp.getKompetencerAfdeling().entrySet().iterator();
		TreeItem<String> root1 = new TreeItem<>("Afdelinger");
		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			Object Afdeling = thisEntry.getKey();

			List Kompetencer = (List) thisEntry.getValue();

			TreeItem<String> afdeling = new TreeItem<>(Afdeling.toString());
			root1.getChildren().add(afdeling);
			for (int i = 0; i < Kompetencer.size(); i++) {
				TreeItem<String> itemChild = new TreeItem<>(Kompetencer.get(i).toString());

				afdeling.getChildren().add(itemChild);

			}

			tree.setRoot(root1);

		}

	}

	/*public void populateTable(String kompetence) throws SQLException {
		Systemkartotek_opslag sysoOp = new Systemkartotek_opslag();

		try {

			int ID = Integer.parseInt(sysoOp.getKompetenceID(kompetence));

			ArrayList IDlist = new ArrayList();
			IDlist.add(ID);

			data.clear();
			Systemkartotek_søgning sysoSø = new Systemkartotek_søgning();
			List<KompetentMedarbejder> kompetenteMedarbejdere = new ArrayList();
			System.out.println(IDlist.toString());
			kompetenteMedarbejdere = sysoSø.opslagPåKompetence(IDlist);
			System.out.println("size" + kompetenteMedarbejdere.size());
			for (KompetentMedarbejder m : kompetenteMedarbejdere) {

				System.out.println("guiid=" + m.getID());

			}
		} catch (Exception e) {
			System.out.println("fejl");
		}
	}*/

	public void kompetencerFinder(List l) throws SQLException {
		
		Systemkartotek_søgning sysoSø = new Systemkartotek_søgning();
		ArrayList<KompetentMedarbejder> kompetenteMedarbejdere = new ArrayList();
		kompetenteMedarbejdere = (ArrayList<KompetentMedarbejder>) sysoSø.opslagPåKompetence(l);
		

		for (KompetentMedarbejder m : kompetenteMedarbejdere) {
			data.add(m);

		}

	}

	public void kompetencerAdder(String kompetenceItem) throws SQLException {
		kompetencerValgt.clear();
		Systemkartotek_opslag sysoOp = new Systemkartotek_opslag();
		Integer kompetenceID = sysoOp.findKompID(kompetenceItem);

		kompetencerValgt.add(kompetenceID);

	}

}
