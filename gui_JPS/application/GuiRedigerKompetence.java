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

import dom�ne.KompetentMedarbejder;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kartotek.Systemkartotek_oprettelser;
import kartotek.Systemkartotek_opslag;
import kartotek.Systemkartotek_redigering;
import kartotek.Systemkartotek_s�gning;
import logik.Redigering;

public class GuiRedigerKompetence implements Initializable {

	private String kompetenceStringt;

	private final ObservableList<KompetentMedarbejder> data = FXCollections.observableArrayList();
	@FXML
	private TextField kompetence;
	@FXML
	private ChoiceBox Afdeling;
	String dims;

	@FXML // fx:id="myButton"
	private Button OK; // Value injected by FXMLLoader
	@FXML
	private Button Annull�r;

	TreeItem<String> kompetenceBox;

	List<String> Afdelinger = new ArrayList();
	Systemkartotek_oprettelser sysoOp = new Systemkartotek_oprettelser();
	Systemkartotek_opslag sysoOps = new Systemkartotek_opslag();

	@Override // This method is called by the FXMLLoader when initialization is
				// complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

		Systemkartotek_redigering sysore = new Systemkartotek_redigering();
		try {
			kompetence.setText(sysore.getKompetence());
			kompetence.setEditable(false);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Systemkartotek_oprettelser sysoOpr = new Systemkartotek_oprettelser();
		GuiController guic = new GuiController();
		try {

			Afdeling.setItems(FXCollections.observableArrayList(sysoOps.findAfdelinger()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		kompetence.setEditable(false);
		OK.setOnAction(ae -> {
			try {
				sysore.redigerKompetence(kompetence.getText(), Afdeling.getValue().toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

}
