/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.SwingUtilities;
import rmilibraryserver.rmi.AuthorService;
import rmilibraryserver.rmi.BookAuthoryService;
import rmilibraryserver.rmi.BookService;
import rmilibraryserver.rmi.InstancedBook;
import rmilibraryserver.rmi.LibraryBookService;

/**
 *
 * @author Nadia
 */
public class Cliente extends willy.gui.Ventana implements SwingConstants {

    private final JPanel scrollPanel = new JPanel(null);
    private final JScrollPane scroll = new JScrollPane(scrollPanel);
    private final JLabel title = new JLabel("Biblioteca", CENTER);
    private final JButton addBook = new JButton("Agregar");
    private final JTextField txtSearch = new JTextField();
    private final JButton search = new JButton("Buscar");

    private final LibraryConnection<AuthorService> authorService;
    private final LibraryConnection<BookAuthoryService> authoryService;
    private final LibraryConnection<BookService> bookService;
    private final LibraryConnection<LibraryBookService> libraryBookService;

    public Cliente(String title, int w, int h, boolean resizable, String url)
            throws NotBoundException, MalformedURLException, RemoteException {
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
        scrollPanel.setLayout(null);
        scrollPanel.setComponentOrientation(ComponentOrientation.UNKNOWN);
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
                scrollPanel.removeAll();
                displayBooks(libraryBookService.getService().searchBooks(txtSearch.getText()));
                scrollPanel.repaint();
                super.setVisible(false);
                super.setVisible(true);
            } catch (RemoteException | SQLException ex) {
                System.err.println(ex.getMessage());
            }
        });

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if (txtSearch.getText().isEmpty()) {
                    try {
                        scrollPanel.removeAll();
                        displayBooks(libraryBookService.getService().getLibraryBooks());
                        scrollPanel.repaint();
                        setVisible(false);
                        setVisible(true);
                    } catch (RemoteException | SQLException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
        });

        addBook.addActionListener((ActionEvent ae) -> {
            setVisible(false);
            final CreateBook cb;
            final Thread initBookCreation;

            cb = new CreateBook(this, bookService.getService(),
                    libraryBookService.getService(), authorService.getService(),
                    authoryService.getService());
            initBookCreation = new Thread(cb::mostrar);

            SwingUtilities.invokeLater(initBookCreation);
        });

    }

    private void displayBooks(InstancedBook[] books) {
        System.out.println("cliente.Cliente.displayBooks()");
        System.out.println(books.length);
        int i = 0;
        for (InstancedBook book : books) {

            final JPanel bookPanel = new JPanel(null);
            bookPanel.setBounds(0, 130 * i, 440, 130);

            String[] authorNames = new String[book.getAuthors().length];
            for (int j = 0; j < book.getAuthors().length; j++) {
                authorNames[j] = book.getAuthors()[j].getName();
            }

            final String bookDescription = String.format(String.format("El libro #%d:\n"
                    + "es %s.\n"
                    + "de: %s\n"
                    + "La editorial es: %s\n"
                    + "y la edición es %s",
                    book.getOnLibrary().getLibraryId(),
                    book.getBook().getName(),
                    String.join(", ", authorNames),
                    book.getBook().getPublisher(),
                    book.getOnLibrary().getEdition()
            ));
            final JTextArea currentBookArea = new JTextArea(bookDescription);
            final JScrollPane currentBookAreaScroll = new JScrollPane(currentBookArea);
            currentBookArea.setEditable(false);
            currentBookArea.setBackground(bookPanel.getBackground());
            currentBookAreaScroll.setBounds(10, 5 /*+ (55 * i)*/, 420, 90);
            bookPanel.add(currentBookAreaScroll);

            final JButton editButton = new JButton("Editar");
            editButton.setBounds(20, 95 /*+ (55 * i)*/, 100, 20);
            bookPanel.add(editButton);

            editButton.addActionListener((ActionEvent ae) -> {
                this.setVisible(false);
                CreateBook cb1 = new CreateBook(this, bookService.getService(),
                        libraryBookService.getService(), authorService.getService(),
                        authoryService.getService(), book);
                SwingUtilities.invokeLater(cb1::mostrar);
            });

            final JButton deleteButton = new JButton("Eliminar");
            deleteButton.setBounds(140, 95 /*+ (55 * i)*/, 100, 20);
            bookPanel.add(deleteButton);

            deleteButton.addActionListener((ActionEvent ae) -> {
                try {
                    libraryBookService.getService().deleteLibraryBook(book.getOnLibrary().getLibraryId());
                    displayBooks(libraryBookService.getService().getLibraryBooks());
                } catch (RemoteException | SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            });

            scrollPanel.add(bookPanel);

            i++;
        }
        scrollPanel.setPreferredSize(new Dimension(440, 130 * i));
    }

    public void updateBooksAndStuff() throws RemoteException, SQLException {
        displayBooks(libraryBookService.getService().getLibraryBooks());
        this.setVisible(false);
        this.setVisible(true);
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
