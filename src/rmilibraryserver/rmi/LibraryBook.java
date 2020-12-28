/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmilibraryserver.rmi;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Willy
 */
public class LibraryBook implements Serializable {

    private final int libraryId;
    private int bookId;
    private String edition;
    private int totalOnLibrary;
    private int borrowed;

    public LibraryBook(int libraryId, int bookId, String edition, int totalOnLibrary, int borrowed) {
        this.libraryId = libraryId;
        this.bookId = bookId;
        this.edition = edition;
        this.totalOnLibrary = totalOnLibrary;
        this.borrowed = borrowed;
    }
    
    public LibraryBook(int bookId, String edition, int totalOnLibrary, int borrowed) {
        this.libraryId = -1;
        this.bookId = bookId;
        this.edition = edition;
        this.totalOnLibrary = totalOnLibrary;
        this.borrowed = borrowed;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public int getTotalOnLibrary() {
        return totalOnLibrary;
    }

    public void setTotalOnLibrary(int totalOnLibrary) {
        this.totalOnLibrary = totalOnLibrary;
    }

    public int getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(int borrowed) {
        this.borrowed = borrowed;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

}
