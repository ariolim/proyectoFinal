/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Andres Ríos Lima
 */
public class FXMLTerceraVentanaController implements Initializable {

  @FXML
  private TableView<?> tableViewMediasAnuales;
  @FXML
  private TableColumn<?, ?> tableColNomProvincia;
  @FXML
  private TableColumn<?, ?> tableColTemMin;
  @FXML
  private TableColumn<?, ?> tableColTemMedia;
  @FXML
  private TableColumn<?, ?> tableColTemMax;
  @FXML
  private TableColumn<?, ?> tableColPreci;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  
  
}
