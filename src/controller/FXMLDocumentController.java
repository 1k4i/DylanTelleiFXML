/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import static jdk.nashorn.tools.ShellFunctions.input;
import model.Recipe;

/**
 *
 * @author dylan
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
     @FXML
    private Button buttonCreate;

    @FXML
    private Button buttonRead;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Button buttonDelete;

    @FXML
    void createRecipe(ActionEvent event) {

        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        System.out.println("Enter Title:");
        String title = input.next();
        
        System.out.println("Enter CreatedBy:");
        String createdBy = input.next();

        System.out.println("Enter DateCreated:");
        String dateCreated = input.next();

        System.out.println("Enter Tags:");
        String tags = input.next();
        
        // create a student instance
        Recipe recipe = new Recipe();
        
        // set properties
        recipe.setId(id);
        recipe.setTitle(title);
        recipe.setCreatedby(createdBy);
        recipe.setDatecreated(dateCreated);
        recipe.setTags(tags);
        
        
        // save this student to database by calling Create operation        
        create(recipe);

        
    }

    @FXML
    void deleteRecipe(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
         // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        Recipe r = readById(id);
        System.out.println("This Recipe Will Be Deleted: "+ r.toString());
        delete(r);

    }

    
    @FXML
    void readByID(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        Recipe r = readById(id);
        System.out.println(r.toString());

    }
    

    @FXML
    void readByTitle(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter title:");
        String title = input.next();
        
        List<Recipe> r = readByTitle(title);
        System.out.println(r.toString());       
    }
    
    @FXML
    void readByTitleAndId(ActionEvent event) {
        // name and cpga
        
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        
        System.out.println("Enter Title:");
        String title = input.next();
        
        System.out.println("Enter id:");
        int id = input.nextInt();
        
        // create a student instance      
        List<Recipe> recipe =  readByTitleAndId(title, id);

    }
    @FXML
    private TextField textboxName;

    @FXML
    private TableView<Recipe> recipeTable;

    @FXML
    private TableColumn<Recipe, Integer> recipeId;

    @FXML
    private TableColumn<Recipe, String> recipeTitle;

    @FXML
    private TableColumn<Recipe, String> dateCreated;

    @FXML
    private TableColumn<Recipe, String> recipeTags;
    
    @FXML
    private TableColumn<Recipe, String> createdBy;
    

    @FXML
    void readAllRecipe(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        Recipe r = readById(id);
        System.out.println(r.toString());

    }

    @FXML
    void updateRecipe(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        System.out.println("Enter Title:");
        String title = input.next();
        
        System.out.println("Enter Tag:");
        String tag = input.next();
        
        // create a student instance
        Recipe recipe = new Recipe();
        
        // set properties
        recipe.setId(id);
        recipe.setTitle(title);
        recipe.setTags(tag);
        
        // save this student to database by calling Create operation        
        updateRecipe(recipe);
    }

    private ObservableList<Recipe> recipeData;

    
    public void setTableData(List<Recipe> recipeList) {

        recipeData = FXCollections.observableArrayList();
        
        recipeList.forEach(r -> {
            recipeData.add(r);
        });

        recipeTable.setItems(recipeData);
        recipeTable.refresh();
    }
        
    @FXML
    void search(ActionEvent event) {
        System.out.println("clicked");
        
        String name = textboxName.getText();

        // calling a db read operaiton, readByName
        List<Recipe> recipes = readByTitle(name);

        if (recipes == null || recipes.isEmpty()) {

            // show an alert to inform user 
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog Box");// line 2
            alert.setHeaderText("This is header section to write heading");// line 3
            alert.setContentText("No Recipe");// line 4
            alert.showAndWait(); // line 5
        } else {

            // setting table data
            setTableData(recipes);
        }        
    }
        @FXML
    void advancedSearch(ActionEvent event) {

    }
    
        @FXML
    void searchByNameAdvancedAction(ActionEvent event) {
        System.out.println("clicked");

        // getting the name from input box        
        String name = textboxName.getText();

        // calling a db read operaiton, readByName
        List<Recipe> recipes = readByNameAdvanced(name);

        // setting table data
        //setTableData(students);
        // add an alert
        if (recipes == null || recipes.isEmpty()) {

            // show an alert to inform user 
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog Box");// line 2
            alert.setHeaderText("This is header section to write heading");// line 3
            alert.setContentText("No Recipe");// line 4
            alert.showAndWait(); // line 5
        } else {
            // setting table data
            setTableData(recipes);
        }

    }

    @FXML
    void actionShowDetails(ActionEvent event) throws IOException {
        System.out.println("clicked");

        
        // pass currently selected model
        Recipe selectedRecipe = recipeTable.getSelectionModel().getSelectedItem();
        
        // fxml loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailModelView.fxml"));

        // load the ui elements
        Parent detailedModelView = loader.load();

        // load the scene
        Scene tableViewScene = new Scene(detailedModelView);

        //access the detailedControlled and call a method
        DetailModelController detailedControlled = loader.getController();


        detailedControlled.initData(selectedRecipe);

        // create a new state
        Stage stage = new Stage();
        stage.setScene(tableViewScene);
        stage.show();

    }

    @FXML
    void actionShowDetailsInPlace(ActionEvent event) throws IOException {
        System.out.println("clicked");
        
                // pass currently selected model
        Recipe selectedRecipe = recipeTable.getSelectionModel().getSelectedItem();

        
        // fxml loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailModelView.fxml"));

        // load the ui elements
        Parent detailedModelView = loader.load();

        // load the scene
        Scene tableViewScene = new Scene(detailedModelView);

        //access the detailedControlled and call a method
        DetailModelController detailedControlled = loader.getController();


        detailedControlled.initData(selectedRecipe);

        // pass current scene to return
        Scene currentScene = ((Node) event.getSource()).getScene();
        detailedControlled.setPreviousScene(currentScene);

        //This line gets the Stage information
        Stage stage = (Stage) currentScene.getWindow();

        stage.setScene(tableViewScene);
        stage.show();
    }
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
        
        Query query = manager.createNamedQuery("Recipe.findAll");
        List<Recipe> data = query.getResultList();
        
        for (Recipe r : data) {            
            System.out.println(r.getId() + " " + r.getTitle()+ " " + r.getTags());         
        }   
        
    }

    //Code used from Dr. Billah Guide
    EntityManager manager;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ////Code used from Prof Billah Guide
        manager = (EntityManager)
        Persistence.createEntityManagerFactory("DylanTelleiFXMLPU").createEntityManager();
    }
    
    // Code from lines 87 - end from guide as allowed in instructions..
    // modified to fit model class and table
    
    public void create(Recipe recipe) {
        try {
            // begin transaction
            manager.getTransaction().begin();
            
            // sanity check
            if (recipe.getId() != null) {
                
                // create student
                manager.persist(recipe);
                
                // end transaction
                manager.getTransaction().commit();
                
                System.out.println(recipe.toString() + " is created");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public Recipe readById(int id){
        Query query = manager.createNamedQuery("Recipe.findById");
        
        // setting query parameter
        query.setParameter("id", id);
        
        // execute query
        Recipe recipe = (Recipe) query.getSingleResult();
        if (recipe != null) {
            System.out.println(recipe.getId() + " " + recipe.getTitle() + " " + recipe.getTags());
        }
        
        return recipe;
    }
    
    public List<Recipe> readByTitle(String title){
        Query query = manager.createNamedQuery("Recipe.findByTitle");
        
        // setting query parameter
        query.setParameter("title", title);
        
        // execute query
        List<Recipe> recipe =  query.getResultList();
        for (Recipe r: recipe) {
            System.out.println(r.getId() + " " + r.getTitle() + " " + r.getTags());
        }
        
        return recipe;
    }
    
    public List<Recipe> readByTitleAndId(String title, Integer Id){
        Query query = manager.createNamedQuery("Recipe.findByTitleAndId");
        
        // setting query parameter
        query.setParameter("title", title);
        query.setParameter("id", Id);
        
        
        // execute query
        List<Recipe> recipes =  query.getResultList();
        for (Recipe r: recipes) {
            System.out.println(r.getId() + " " + r.getTitle() + " " + r.getTags());
        }
        
        return recipes;
    } 



    // Read Operations
    public List<Recipe> readAll(){
        Query query = manager.createNamedQuery("Recipe.findAll");
        List<Recipe> recipe = query.getResultList();

        for (Recipe r : recipe) {
            System.out.println(r.getId() + " " + r.getTitle() + " " + r.getCreatedby() + " " + r.getDatecreated() + " " + r.getTags());
        }
        
        return recipe;
    }
                 
    
    // Update operation
    public void updateRecipe(Recipe model) {
        try {

            Recipe existingRecipe = manager.find(Recipe.class, model.getId());

            if (existingRecipe != null) {
                // begin transaction
                manager.getTransaction().begin();
                
                // update all atttributes
                existingRecipe.setTitle(model.getTitle());
                existingRecipe.setTags(model.getTags());
                
                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    public void delete(Recipe recipe) {
        
        
        try {
            Recipe existingRecipe = manager.find(Recipe.class, recipe.getId());

            // sanity check
            if (existingRecipe != null) {
                
                // begin transaction
                manager.getTransaction().begin();
                
                //remove student
                manager.remove(existingRecipe);
                
                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     *
     * Quiz 4 begin
     */
    public List<Recipe> readByNameAdvanced(String name) {
        Query query = manager.createNamedQuery("Recipe.findByNameAdvanced");

        // setting query parameter
        query.setParameter("name", name);

        // execute query
        List<Recipe> recipes = query.getResultList();
        for (Recipe r : recipes) {
            System.out.println(r.getId() + " " + r.getTitle() + " " + r.getDatecreated());
        }

        return recipes;
    }


    
    
    
    
    
}
