package baseline;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;


public class FXMLController {

    @FXML private AnchorPane aPane;
    @FXML private ToggleGroup group;
    @FXML private RadioButton allV;
    @FXML private RadioButton completedV;
    @FXML private RadioButton inCompletedV;
    @FXML private TableColumn<Items, String> desc;
    @FXML private TableColumn<Items, String> date;
    @FXML private TableColumn<Items, Boolean> completed;
    @FXML private TableView<Items> tableList;
    @FXML private TextField descriptionBox;
    @FXML private DatePicker dueDatePicker;

    ObservableList<Items> list = FXCollections.observableArrayList();
    ObservableList<Items> compList = FXCollections.observableArrayList();
    ObservableList<Items> inCompList = FXCollections.observableArrayList();

    public void addTextFields(ActionEvent event) {
        Boolean validateItems = validateItems(descriptionBox.getText());
        if (validateItems == true) {
            String dateToString = dateToString(dueDatePicker);
            addItems(descriptionBox,dateToString);
        }
        else {
            //clear values in textbox
            descriptionBox.clear();
            dueDatePicker.getEditor().clear();
        }
    }

    public String dateToString(DatePicker dueDatePicker) {
        String dateString = "N/A";
        ////if the date picker is null then set date as "" else String date = datepicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (dueDatePicker.getValue() == null) {
            return dateString;
        }
        else {
            String dueString = dueDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return  dueString;
        }
    }

    public Boolean validateItems(String descriptionBox) {
        int length = descriptionBox.length();
        //get the length of descriptionBox and if it is less than one or over the limit of 256 characters make a substring and return that
        if (length < 1 || length > 256) {
            return false;
        }
        //else return the string
        return true;
    }

    private void addItems(TextField descBox, String dueDateString) {
        //Item newItem = new Item(descriptionBox,dueDatePicker, false)
        Items newItem  = new Items(descBox.getText(), dueDateString);
        //tableList.getItems().add(Item)
        tableList.getItems().add(newItem);
        //clear items in the textfields and datepicker
        descriptionBox.clear();
        dueDatePicker.getEditor().clear();
    }

    public void removeSelectedList(ActionEvent event) {
        //get the list and define two variables selectedRows, allItems
        // ObservableList<Items> selectedRows, allItems;
        ObservableList<Items> selectedRows, allItems;
        //allItems = tableList.getItems();
        allItems = tableList.getItems();
        //selectedRows = tableList.getSelectionModel().getSelectedItems();
        selectedRows = tableList.getSelectionModel().getSelectedItems();
        //for (Items item : selectedRows) {allItems.remove(item);}
        for (Items item : selectedRows) {
            allItems.remove(item);
        }
    }

    public void clearList(ActionEvent event) {
        //tableList.getItems().clear();
        tableList.getItems().clear();
    }

    public void initialize() {

        list.add(new Items("Going to School", "2021-05-18"));
        list.add(new Items("Lax Practice", "2020-03-04"));

        //set up the columns in the table
        desc.setCellValueFactory(new PropertyValueFactory<Items, String>("desc"));
        date.setCellValueFactory(new PropertyValueFactory<Items, String>("date"));
        completed.setCellValueFactory(new PropertyValueFactory<Items, Boolean>("completed"));
        completed.setCellFactory(CheckBoxTableCell.forTableColumn(completed));


        //load dummy data
        tableList.setItems(list);
        group.selectedToggleProperty().addListener(
                (observable, oldToggle, newToggle) -> {
                    if (newToggle == allV) {
                        compList.clear();
                        inCompList.clear();
                        tableList.setItems(list);
                    }
                    else if (newToggle == completedV) {
                        compList.clear();
                        inCompList.clear();
                        for (Items items : list) {
                            if (items.isCompleted() == true) {
                                compList.add(items);
                            }
                        }
                        tableList.setItems(compList);
                    }
                    else if (newToggle == inCompletedV) {
                        compList.clear();
                        inCompList.clear();
                        for (Items items : list) {
                            if (items.isCompleted() == false) {
                                inCompList.add(items);
                            }
                        }
                        tableList.setItems(inCompList);
                    }
                }
        );

        //Update the table to allow for the first and last name fields
        //to be editable
        tableList.setEditable(true);
        desc.setCellFactory(TextFieldTableCell.forTableColumn());
        date.setCellFactory(TextFieldTableCell.forTableColumn());

        //This will allow the table to select single rows
        tableList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }


    public void saveList(ActionEvent event) throws IOException {
        //open file save but only allow it to save to .txt files
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save TableView");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = (Stage)aPane.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            saveTextToFile(file);
        }
        //get the absolute path of that file
        //write the observablelist to that file
    }

    private void saveTextToFile(File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        for (Items items : list) {
            writer.write(items.getDesc() + "," + items.getDate() + "\n");
        }
        writer.close();
    }

    public void openList(ActionEvent event) throws IOException {
        //open filechooser and get the absolute path from the .txt file
        //clear all values in the tableList
        //use buffered reader to read the .txt file and add it to the list for the tableView (tableList)
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open TableView");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = (Stage)aPane.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            tableList.getItems().clear();
            openFileToText(file);
        }
    }

    private void openFileToText(File file) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(String.valueOf(file)));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] names = line.split(",");

            // Add the student to the list
            list.add(new Items(names[0],names[1]));
        }
    }

    public void closeList(ActionEvent event) {
        Platform.exit();
    }

    public void editDesc(TableColumn.CellEditEvent<Items, String> itemsStringCellEditEvent) {
        Boolean validateEditItems = validateEditItems(itemsStringCellEditEvent.toString());
        //if validateEditItems == false then cancel the edit
        if (validateEditItems == false) {
            descriptionBox.cancelEdit();
        }
    }

    public Boolean validateEditItems(String toString) {
        //check if the string is greater than 0 and less than 256
        if (toString.length() < 1 || toString.length() > 256) {
            return false;
        }
        //return false
        //else
        else {
            //return true
            return true;
        }
    }

    public void editDate(TableColumn.CellEditEvent<Items, String> itemsStringCellEditEvent) {
        Boolean validateEditDate = validateEditDate(itemsStringCellEditEvent.toString());
        //if validateEditDate == false then cancel the edit
    }

    public Boolean validateEditDate(String toString) {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        //dateFormat.setLenient(false);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(toString.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
}
