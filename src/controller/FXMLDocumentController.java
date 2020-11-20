/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    void readByTitleAndTag(ActionEvent event) {
        // name and cpga
        
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        
        System.out.println("Enter Title:");
        String title = input.next();
        
        System.out.println("Enter Tags:");
        String tags = input.next();
        
        // create a student instance      
        List<Recipe> recipe =  readByTitleAndTag(title, tags);

    }

    

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
    
    public List<Recipe> readByTitleAndTag(String title, String tags){
        Query query = manager.createNamedQuery("Recipe.findByTitleAndTags");
        
        // setting query parameter
        query.setParameter("title", title);
        query.setParameter("tags", tags);
        
        
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


    
    
    
    
    
}
