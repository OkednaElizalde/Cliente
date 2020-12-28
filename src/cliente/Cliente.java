/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import rmilibraryserver.rmi.AuthorService;
import rmilibraryserver.rmi.Book;
import rmilibraryserver.rmi.BookAuthoryService;
import rmilibraryserver.rmi.BookService;
import rmilibraryserver.rmi.LibraryBookService;

/**
 *
 * @author Nadia
 */
public class Cliente extends willy.gui.Ventana implements SwingConstants {

    private final JScrollPane scroll = new JScrollPane();
    private final JLabel title = new JLabel("Biblioteca", CENTER);
    private final JButton addBook = new JButton("Agregar");
    private final JTextField txtSearch = new JTextField();
    private final JButton search = new JButton("Buscar");

    private final CreateBook cb = new CreateBook("Crear libro", 400, 350, false);
    private final Thread initBookCreation = new Thread(cb::mostrar);

    private final LibraryConnection<AuthorService> authorService;
    private final LibraryConnection<BookAuthoryService> authoryService;
    private final LibraryConnection<BookService> bookService;
    private final LibraryConnection<LibraryBookService> libraryBookService;

    public Cliente(String title, int w, int h, boolean resizable, String url) throws NotBoundException, MalformedURLException, RemoteException {
        super(title, w, h, resizable);
        super.getContentPane().setLayout(null);

        authorService = new LibraryConnection(url + "AuthorService");
        authoryService = new LibraryConnection(url + "AuthoryService");
        bookService = new LibraryConnection(url + "BookService");
        libraryBookService = new LibraryConnection(url + "LibraryBookService");
    }

    @Override
    public void setComp() {
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        title.setBounds(20, 20, 460, 25);
        super.addComp(title);

        txtSearch.setBounds(20, 60, 300, 30);
        super.addComp(txtSearch);

        search.setBounds(330, 60, 150, 30);
        super.addComp(search);

        scroll.setBounds(20, 100, 460, 350);
        scroll.setLayout(null);
        super.addComp(scroll);

        addBook.setBounds(330, 450, 150, 30);
        super.addComp(addBook);

        try {
            int i = 0;
            for (Book book : bookService.getService().getBooks()) {
                System.out.println(String.format("El libro es %d y el nombre es %s, de %s", book.getId(), book.getName(), book.getPublisher()));
                final JLabel currentBookLabel = new JLabel(String.format("El libro es %d y el nombre es %s, de %s", book.getId(), book.getName(), book.getPublisher()), CENTER);
                currentBookLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                currentBookLabel.setBounds(20, 5 + (25 * i), 420, 20);
                scroll.add(currentBookLabel);
                i++;
            }
        } catch (RemoteException | SQLException ex) {
            System.err.println("Murioooooo " + ex.getMessage());
        }
    }

    public static void main(String[] args) throws InterruptedException, InvocationTargetException, NotBoundException, MalformedURLException, RemoteException {
        String url = JOptionPane.showInputDialog(null, "Ingrese la url a conectarse", "URL", JOptionPane.PLAIN_MESSAGE);
        while (url == null ? false : url.isEmpty()) {
            url = JOptionPane.showInputDialog(null, "Ingrese la url a conectarse otra vez", "URL", JOptionPane.PLAIN_MESSAGE);
        }
        final Cliente cliente = new Cliente("Asistente de la biblioteca", 500, 500, false, url);
        final Thread t = new Thread(cliente::mostrar);
        SwingUtilities.invokeAndWait(t);
    }
}
