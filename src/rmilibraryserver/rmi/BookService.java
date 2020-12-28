/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmilibraryserver.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Willy
 */
public interface BookService extends Remote {

    public Book[] getBooks() throws RemoteException, SQLException;
    
    public void addBook(final Book book) throws RemoteException;

    public void modifyBook(final Book book) throws RemoteException;

    public void deleteBook(final int id) throws RemoteException;
}
