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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kartotek.Systemkartotek_oprettelser;
import kartotek.Systemkartotek_opslag;
import kartotek.Systemkartotek_søgning;

public class GuiNyAfdeling implements Initializable {
	ArrayList kompetencerValgt = new ArrayList();
	@FXML
	private TextField Afdeling;
	@FXML
	private TextField Kompetence1;
	@FXML
	private TextField Kompetence2;
	@FXML
	private TextField Kompetence3;
	@FXML // fx:id="myButton"
	private Button OK; // Value injected by FXMLLoader
	@FXML
	private Button Annullér;
	@FXML
	private Label info;

	Systemkartotek_oprettelser sysoOpr = new Systemkartotek_oprettelser();
	Systemkartotek_opslag sysoOps = new Systemkartotek_opslag();

	@Override // This method is called by the FXMLLoader when initialization is
				// complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

		OK.setOnAction(ae -> {
			if (Afdeling.getText() != "") {
				if (!Kompetence1.getText().equals("")) {
					kompetencerValgt.add(Kompetence1.getText());
				}
				if (!Kompetence2.getText().equals("")) {
					kompetencerValgt.add(Kompetence2.getText());
				}
				if (!Kompetence3.getText().equals("")) {
					kompetencerValgt.add(Kompetence3.getText());
				}
				if (!kompetencerValgt.isEmpty()) {
					try {
						sysoOpr.opretNyAfdeling(Afdeling.getText(), kompetencerValgt);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					info.setTextFill(Color.web("#FF0000"));
					info.setText("OBS! Vælg venligst mindst 1 kompetence.");
				}
			}
		});

	}

}
