/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;


import java.awt.Dimension;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.SwingUtilities;
import rmilibraryserver.rmi.AuthorService;
import rmilibraryserver.rmi.Book;
import rmilibraryserver.rmi.BookAuthoryService;
import rmilibraryserver.rmi.BookService;
import rmilibraryserver.rmi.InstancedBook;
import rmilibraryserver.rmi.LibraryBook;
import rmilibraryserver.rmi.LibraryBookService;

/**
 *
 * @author Nadia
 */
public class Cliente extends willy.gui.Ventana implements SwingConstants {

    private final JPanel scrollPanel = new JPanel(null);
    private final JScrollPane scroll = new JScrollPane(scrollPanel);
    private final JPanel scrollibro = new JPanel(null);
    private final JLabel title = new JLabel("Biblioteca", CENTER);
    private final JButton addBook = new JButton("Agregar");
    private final JTextField txtSearch = new JTextField();
    private final JButton search = new JButton("Buscar");

    private final LibraryConnection<AuthorService> authorService;
    private final LibraryConnection<BookAuthoryService> authoryService;
    private final LibraryConnection<BookService> bookService;
    private final LibraryConnection<LibraryBookService> libraryBookService;

    private final CreateBook cb;
    private final Thread initBookCreation;

    public Cliente(String title, int w, int h, boolean resizable, String url) throws NotBoundException, MalformedURLException, RemoteException {
        super(title, w, h, resizable);
        super.getContentPane().setLayout(null);

        authorService = new LibraryConnection(url + "AuthorService");
        authoryService = new LibraryConnection(url + "AuthoryService");
        bookService = new LibraryConnection(url + "BookService");
        libraryBookService = new LibraryConnection(url + "LibraryBookService");

        cb = new CreateBook(this, bookService.getService(), libraryBookService.getService(), authorService.getService());
        initBookCreation = new Thread(cb::mostrar);
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
        scrollibro.setLayout(null);
        scrollibro.setPreferredSize(new Dimension(600,600));
        scroll.setViewportView(scrollibro);
//        scroll.setPreferredSize(new Dimension(460, 350));
//        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        super.addComp(scroll);

        addBook.setBounds(330, 450, 150, 30);
        super.addComp(addBook);

        try {
            displayBooks(libraryBookService.getService().getLibraryBooks());
        } catch (RemoteException | SQLException ex) {
            System.err.println("Murioooooo " + ex.getMessage());
        }

        search.addActionListener((ActionEvent ae) -> {
            try {
                scrollibro.removeAll();
                scrollibro.repaint();
                displayBooks(libraryBookService.getService().searchBooks(txtSearch.getText()));
            } catch (RemoteException | SQLException ex) {
                System.err.println(ex.getMessage());
            }
        });

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if (txtSearch.getText().isEmpty()) {
                    try {
                        scrollibro.removeAll();
                        scrollibro.repaint();
                        displayBooks(libraryBookService.getService().getLibraryBooks());
                    } catch (RemoteException | SQLException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
        });

        addBook.addActionListener((ActionEvent ae) -> {
            setVisible(false);
            SwingUtilities.invokeLater(initBookCreation);
        });

    }

    private void displayBooks(InstancedBook[] books) {
        System.out.println("cliente.Cliente.displayBooks()");
        System.out.println(books.length);
        int i = 0;
        for (InstancedBook book : books) {

            final JLabel currentBookLabel = new JLabel(String.format("El libro es %d y el nombre es %s, de %s", book.getBook().getId(), book.getBook().getName(), book.getBook().getPublisher()), CENTER);
            currentBookLabel.setBounds(20, 5 + (55 * i), 420, 20);
            scrollibro.add(currentBookLabel);

            final JButton editButton = new JButton("Editar");
            editButton.setBounds(20, 25 + (55 * i), 100, 20);
            scrollibro.add(editButton);

            editButton.addActionListener((ActionEvent ae) -> {
                this.setVisible(false);
                CreateBook cb1 = new CreateBook(this, bookService.getService(),
                        libraryBookService.getService(), authorService.getService(), book);
                SwingUtilities.invokeLater(cb1::mostrar);
            });

            final JButton deleteButton = new JButton("Eliminar");
            deleteButton.setBounds(140, 25 + (55 * i), 100, 20);
            scrollibro.add(deleteButton);

            deleteButton.addActionListener((ActionEvent ae) -> {
                try {
                    bookService.getService().deleteBook(book.getOnLibrary().getBookId());
                    displayBooks(libraryBookService.getService().getLibraryBooks());
                } catch (RemoteException | SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            });

            i++;
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
