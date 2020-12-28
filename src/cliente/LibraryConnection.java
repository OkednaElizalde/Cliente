/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author Willy
 * @param <T> Tipo
 */
public class LibraryConnection<T> {
    //pinneapple.ddns.net

    private final T service;
    
    public LibraryConnection(final String URL) throws NotBoundException, MalformedURLException, RemoteException{
        service = (T) Naming.lookup(URL);
    }
    
    public T getService(){
        return service;
    }
    
}
