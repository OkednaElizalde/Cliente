/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmilibraryserver.rmi;

import java.io.Serializable;

/**
 *
 * @author Willy
 */
public class Book implements Serializable{

    private final int id;
    private String name;
    private String publisher;

    public Book(int id, String name, String publisher) {
        this.id = id;
        this.name = name;
        this.publisher = publisher;
    }
    
    public Book(String name, String publisher){
        this.id = -1;
        this.name = name;
        this.publisher = publisher;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

}
