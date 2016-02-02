/**
 * Sample Skeleton for "simple.fxml" Controller Class
 * Use copy/paste to copy paste this code into your favorite IDE
 **/

package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import domæne.KompetentMedarbejder;

import java.util.Map.Entry;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import kartotek.*;

public class GuiOpretMedarbejder implements Initializable {
	ArrayList<Integer> kompetencerValgt = new ArrayList<Integer>();
	private final ObservableList<KompetentMedarbejder> data = FXCollections.observableArrayList();
	@FXML
	private TreeView<String> tree;

	TreeItem item;
	TreeItem kompetence = null;

	@FXML
	ListView LW;
	@FXML
	private Button btn1;
	@FXML
	private Button btn2;
	@FXML
	private Button btn3;
	@FXML
	private TextField navn;
	@FXML
	private TextField email;

	@FXML
	private TableView<KompetentMedarbejder> tv = new TableView<KompetentMedarbejder>();

	@Override // This method is called by the FXMLLoader when initialization is
				// complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		Systemkartotek_oprettelser sysoOpr = new Systemkartotek_oprettelser();
		try {
			createTree();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ObservableList<String> items = FXCollections.observableArrayList();

		tree.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {

					item = tree.getSelectionModel().getSelectedItem();
					if (!items.contains(item.getValue().toString())) {
						items.add(item.getValue().toString());

						LW.setItems(items);
						try {
							kompetencerAdder(item.getValue().toString());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			}
		});

		LW.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				String valgt = LW.getSelectionModel().getSelectedItem().toString();
				try {
					kompetencerSletter(valgt);
					items.remove(valgt);
					LW.setItems(items);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btn2.setOnAction(ae -> {
			try {
				sysoOpr.opretNyMedarbejder(navn.getText(), email.getText(), kompetencerValgt);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}


	public void kompetencerAdder(String b) throws SQLException {
		Systemkartotek_opslag sysoOp = new Systemkartotek_opslag();
		Integer kompetenceID = sysoOp.findKompID(b);
		kompetencerValgt.add(kompetenceID);
	}

	public void kompetencerSletter(String b) throws SQLException {
		Systemkartotek_opslag sysoOp = new Systemkartotek_opslag();
		for (int i = 0; i < kompetencerValgt.size(); i++) {
			if (kompetencerValgt.get(i) == sysoOp.findKompID(b)) {
				kompetencerValgt.remove(i);
			}
		}
	}

	public void kompetencerFinder(List l) throws SQLException {
		data.clear();
		Systemkartotek_søgning sysoSø = new Systemkartotek_søgning();
		ArrayList<KompetentMedarbejder> kompetenteMedarbejdere = new ArrayList();
		kompetenteMedarbejdere = (ArrayList<KompetentMedarbejder>) sysoSø.opslagPåKompetence(l);
		for (KompetentMedarbejder m : kompetenteMedarbejdere) {
			data.add(m);
		}

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

}
