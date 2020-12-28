/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmilibraryserver.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Willy
 */
public interface BookAuthoryService extends Remote {

    public void setAuthory(final int bookId, final int... authorsIds) throws RemoteException;
}
