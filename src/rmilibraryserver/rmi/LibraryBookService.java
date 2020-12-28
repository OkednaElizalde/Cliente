/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmilibraryserver.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 *
 * @author Willy
 */
public interface LibraryBookService extends Remote {

    public InstancedBook[] getLibraryBooks() throws RemoteException, SQLException;

    public InstancedBook[] searchBooks(final String search) throws RemoteException, SQLException;

    public void addLibraryBook(final LibraryBook book) throws RemoteException;

    public void modifyLibraryBook(final LibraryBook book) throws RemoteException;

    public void deleteLibraryBook(final int id) throws RemoteException;

    public void borrowBook(final int quantity) throws RemoteException;

}
