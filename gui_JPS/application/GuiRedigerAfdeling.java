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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kartotek.Systemkartotek_oprettelser;
import kartotek.Systemkartotek_opslag;
import kartotek.Systemkartotek_redigering;
import kartotek.Systemkartotek_søgning;
import logik.Redigering;

public class GuiRedigerAfdeling implements Initializable {

	@FXML
	private TextField afdeling;
	@FXML
	private Label info;

	@FXML // fx:id="myButton"
	private Button OK; // Value injected by FXMLLoader
	@FXML
	private Button Annullér;

	Systemkartotek_oprettelser sysoOp = new Systemkartotek_oprettelser();
	Systemkartotek_opslag sysoOps = new Systemkartotek_opslag();
	String afdelingtekst;

	@Override // This method is called by the FXMLLoader when initialization is
				// complete

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

		Systemkartotek_redigering sysore = new Systemkartotek_redigering();
		try {
			afdelingtekst = sysore.getAfdeling();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		afdeling.setText(afdelingtekst);

		OK.setOnAction(ae -> {
			try {
				sysore.redigerAfdeling(afdeling.getText(), afdelingtekst);
				;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

}
