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
public interface AuthorService extends Remote {

    public Author[] getAuthors() throws RemoteException, SQLException;

    public Author addAuthor(final String authorName) throws RemoteException;

    public void modifyAuthor(final Author author) throws RemoteException;

    public void deleteAuthor(final int id) throws RemoteException;
}
