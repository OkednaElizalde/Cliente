/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import rmilibraryserver.rmi.Author;
import rmilibraryserver.rmi.AuthorService;
import rmilibraryserver.rmi.Book;
import rmilibraryserver.rmi.BookService;
import rmilibraryserver.rmi.InstancedBook;
import rmilibraryserver.rmi.LibraryBookService;
import rmilibraryserver.rmi.BookAuthoryService;
import rmilibraryserver.rmi.LibraryBook;

/**
 *
 * @author Willy
 */
public class CreateBook extends willy.gui.Ventana implements SwingConstants {

    private Book maybeBook = null;

    private final Cliente parent;
    private final BookService bookService;
    private final AuthorService authorService;
    private final LibraryBookService libraryService;
    private final BookAuthoryService authoryService;
    private final int action;
    private final InstancedBook editableBook;

    private final List<Author> saveAuthors;

    private final JLabel title = new JLabel("Edición de libro", CENTER);
    private final JLabel nameLabel = new JLabel("Nombre:", LEFT);
    private final JButton getDataFromExistingBook = new JButton("Obtener");
    private final JTextField name = new JTextField();
    private final JLabel publisherLabel = new JLabel("Editorial:", LEFT);
    private final JTextField publisher = new JTextField();
    private final JLabel editionLabel = new JLabel("Edición:", LEFT);
    private final JTextField edition = new JTextField();
    private final JLabel authorsLabel = new JLabel("Autores:", LEFT);
    private final JTextArea authorsTxt = new JTextArea("");
    private final JButton addAuthor = new JButton("Añadir autor");
    private final JButton createAuthor = new JButton("Crear autor");
    private final JLabel totalLabel = new JLabel("Libros en la biblioteca:", CENTER);
    private final JSpinner total = new JSpinner(new SpinnerNumberModel(1, 1, 2147483647, 1));
    private final JLabel borrowedLabel = new JLabel("Libros prestados:", CENTER);
    private final JSpinner borrowed = new JSpinner(new SpinnerNumberModel(0, 0, 2147483647, 1));

    private final JButton okButton = new JButton("Ok");
    private final JButton cancelButton = new JButton("Cancelar");

    public CreateBook(Cliente parent, BookService bookService,
            LibraryBookService libraryBookService, AuthorService authorService,
            BookAuthoryService authoryService) {
        super("Crear libro", 400, 370, false);
        super.getContentPane().setLayout(null);

        this.authorService = authorService;
        this.parent = parent;
        this.bookService = bookService;
        this.authoryService = authoryService;
        this.libraryService = libraryBookService;
        this.action = 0;
        this.editableBook = null;
        this.saveAuthors = new ArrayList<>();
    }

    public CreateBook(Cliente parent, BookService bookService,
            LibraryBookService libraryBookService, AuthorService authorService,
            BookAuthoryService authoryService, InstancedBook editableBook) {
        super("Crear libro", 400, 370, false);
        super.getContentPane().setLayout(null);

        this.authorService = authorService;
        this.parent = parent;
        this.bookService = bookService;
        this.authoryService = authoryService;
        this.libraryService = libraryBookService;
        this.action = 1;
        this.editableBook = editableBook;

        this.name.setText(editableBook.getBook().getName());
        this.publisher.setText(editableBook.getBook().getPublisher());
        for (Author author : editableBook.getAuthors()) {
            authorsTxt.append(author.getName() + "\n");
        }

        this.total.setValue(editableBook.getOnLibrary().getTotalOnLibrary());
        this.borrowed.setValue(editableBook.getOnLibrary().getBorrowed());
        this.getDataFromExistingBook.setVisible(false);
        this.edition.setText(editableBook.getOnLibrary().getEdition());
        this.saveAuthors = new ArrayList<>();

        saveAuthors.addAll(Arrays.asList(editableBook.getAuthors()));

    }

    @Override
    public void setComp() {
        authorsTxt.setEditable(false);
        
        if (action == 0) {
            super.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        } else {
            super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                try {
                    parent.updateBooksAndStuff();
                } catch (RemoteException | SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });

        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        title.setBounds(20, 20, 360, 25);
        super.addComp(title);

        nameLabel.setBounds(20, 60, 53, 20);
        super.addComp(nameLabel);

        name.setBounds(80, 60, 200, 20);
        super.addComp(name);

        getDataFromExistingBook.setBounds(290, 60, 90, 20);
        super.addComp(getDataFromExistingBook);

        publisherLabel.setBounds(20, 85, 53, 20);
        super.addComp(publisherLabel);

        publisher.setBounds(80, 85, 200, 20);
        super.addComp(publisher);

        editionLabel.setBounds(20, 110, 53, 20);
        super.addComp(editionLabel);

        edition.setBounds(80, 110, 200, 20);
        super.addComp(edition);

        authorsLabel.setBounds(20, 130, 55, 20);
        super.addComp(authorsLabel);

        final JScrollPane jsp = new JScrollPane(authorsTxt);
        jsp.setBounds(20, 150, 360, 100);
        jsp.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        jsp.setEnabled(false);
        super.addComp(jsp);

        addAuthor.setBounds(20, 255, 175, 20);
        super.addComp(addAuthor);

        createAuthor.setBounds(205, 255, 175, 20);
        super.addComp(createAuthor);

        totalLabel.setBounds(20, 285, 175, 20);
        super.addComp(totalLabel);

        borrowedLabel.setBounds(205, 285, 175, 20);
        super.addComp(borrowedLabel);

        total.setBounds(20, 310, 175, 20);
        super.addComp(total);

        borrowed.setBounds(205, 310, 175, 20);
        super.addComp(borrowed);

        cancelButton.setBounds(190, 340, 90, 20);
        super.addComp(cancelButton);

        okButton.setBounds(290, 340, 90, 20);
        super.addComp(okButton);

        createAuthor.addActionListener((ActionEvent ae) -> {
            String newAuthor = JOptionPane.showInputDialog(null, "Ingrese un nuevo autor", "Crear Autor", JOptionPane.PLAIN_MESSAGE);
            if (newAuthor == null) {
                return;
            }
            try {

                Author[] recentlyGottenAuthors = authorService.getAuthors();
                String[] authorNames = new String[recentlyGottenAuthors.length];
//                int[] ids = new int[recentlyGottenAuthors.length];
                boolean authorThatExist = false;
                for (int i = 0; i < authorNames.length; i++) {
                    authorNames[i] = recentlyGottenAuthors[i].getName();
                    if (newAuthor.equals(recentlyGottenAuthors[i].getName())) {
                        JOptionPane.showMessageDialog(null, "El autor ingresado ya existe");
                        authorThatExist = true;
                        break;
                    }
                }

                if (!authorThatExist) {
                    Author insertedAuthor = authorService.addAuthor(newAuthor);
                    if (insertedAuthor != null) {
                        saveAuthors.add(insertedAuthor);
                    } else {
                        JOptionPane.showMessageDialog(null, "Ocurrió un error, vuelva a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    authorsTxt.append(newAuthor + "\n");
                }

            } catch (HeadlessException | RemoteException | SQLException e) {
                System.err.println(e.getMessage());
            }
        });

        addAuthor.addActionListener((ActionEvent ae) -> {
            try {
                final Author[] recentlyGottenAuthors = authorService.getAuthors();
                int[] ids = new int[recentlyGottenAuthors.length];
                final String[] authorNames = new String[recentlyGottenAuthors.length];
                for (int i = 0; i < authorNames.length; i++) {
                    authorNames[i] = recentlyGottenAuthors[i].getId() + ": " + recentlyGottenAuthors[i].getName();
                }

                Object chosenObj = JOptionPane.showInputDialog(null, "Elige uno de los autores", "Elección de autores", JOptionPane.PLAIN_MESSAGE, null, authorNames, null);

                if (chosenObj == null) {
                    return;
                }

                String chosenString = (String) chosenObj;
                int chosenId = Integer.valueOf(chosenString.split("\\:")[0]);
                Author chosenAuthor = null;
                for (Author recentlyGottenAuthor : recentlyGottenAuthors) {
                    if (recentlyGottenAuthor.getId() == chosenId) {
                        chosenAuthor = recentlyGottenAuthor;
                        System.out.println(String.format("El autor elegido es %d:%s", chosenAuthor.getId(), chosenAuthor.getName()));
                        break;
                    }
                }

                while (chosenAuthor == null) {
                    System.out.println("No debes entrar aquí");
                    chosenObj = JOptionPane.showInputDialog(null,
                            "Ocurrió un error, vuelva a elegir uno de los autores", "Elección de autores",
                            JOptionPane.PLAIN_MESSAGE, null, authorNames, null);

                    if (chosenObj == null) {
                        return;
                    }

                    chosenString = (String) chosenObj;

                    chosenId = Integer.valueOf(chosenString.split("\\:")[0]);

                    chosenAuthor = null;
                    for (Author recentlyGottenAuthor : recentlyGottenAuthors) {
                        if (recentlyGottenAuthor.getId() == chosenId) {
                            chosenAuthor = recentlyGottenAuthor;
                            break;
                        }
                    }
                }

                if (!saveAuthors.contains(chosenAuthor)) {
                    authorsTxt.setText("");
                    final Author[] authors;
                    if (editableBook != null) {
                        System.out.println("Sí hay libro para editar en los autores");
                        authors = bookService.getAuthors(editableBook.getBook().getId());
                    } else {
                        System.out.println("No hay libro para editar en los autores");
                        authors = new Author[saveAuthors.size()];
                        for (int i = 0; i < saveAuthors.size(); i++) {
                            authors[i] = saveAuthors.get(i);
                        }

                    }
                    final Author[] newAuthors = new Author[authors.length + 1];

                    System.arraycopy(authors, 0, newAuthors, 0, authors.length);
                    newAuthors[authors.length] = chosenAuthor;
                    saveAuthors.add(chosenAuthor);
                    for (Author author : newAuthors) {
                        authorsTxt.append(author.getName() + "\n");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No puedes poner un autor que ya está", "Autor Repetidísimo", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (RemoteException | SQLException ex) {
                System.err.println(ex.getMessage());
            }
        });

        borrowed.addChangeListener((ChangeEvent ce) -> {
            if (((int) borrowed.getValue()) > ((int) total.getValue())) {
                JOptionPane.showMessageDialog(null, "No puedes prestar más libros de los que tienes");
                borrowed.setValue((int) total.getValue());
            }
        });

        total.addChangeListener((ChangeEvent ce) -> {
            if (((int) borrowed.getValue()) > ((int) total.getValue())) {
                borrowed.setValue((int) total.getValue());
            }
        });

        cancelButton.addActionListener((ActionEvent ae) -> {
            parent.mostrar();
            this.setVisible(false);
        });

        okButton.addActionListener((ActionEvent ae) -> {
            try {
                switch (action) {
                    case 0:
                        System.out.println("E1");
                        final Book insertedBook;
                        if (maybeBook == null) {
                            insertedBook = bookService.addBook(new Book(name.getText(), publisher.getText()));
                            final int[] authorIDs = new int[saveAuthors.size()];
                            for (int i = 0; i < saveAuthors.size(); i++) {
                                authorIDs[i] = saveAuthors.get(i).getId();
                            }
                            System.out.println("E4");
                            authoryService.setAuthory(insertedBook.getId(), authorIDs);
                        } else {
                            insertedBook = maybeBook;
                        }
                        System.out.println("E2");
                        System.out.println(insertedBook.getId());
                        libraryService.addLibraryBook(new LibraryBook(
                                insertedBook.getId(), edition.getText(),
                                (int) total.getValue(), (int) borrowed.getValue()));

                        System.out.println("E3");
                        break;
                    case 1:
                        editableBook.getBook().setName(name.getText());
                        editableBook.getBook().setPublisher(publisher.getText());
                        bookService.modifyBook(editableBook.getBook());

                        editableBook.getOnLibrary().setBorrowed((int) borrowed.getValue());
                        editableBook.getOnLibrary().setEdition(edition.getText());
                        editableBook.getOnLibrary().setTotalOnLibrary((int) total.getValue());
                        libraryService.modifyLibraryBook(editableBook.getOnLibrary());

                        final int[] authorIs = new int[saveAuthors.size()];
                        for (int i = 0; i < saveAuthors.size(); i++) {
                            authorIs[i] = saveAuthors.get(i).getId();
                        }
                        authoryService.setAuthory(editableBook.getBook().getId(), authorIs);
                        break;
                }
                this.dispose();
                try {
                    parent.updateBooksAndStuff();
                } catch (RemoteException | SQLException e) {
                }
            } catch (RemoteException e) {
                System.err.println(e);
            }
        });

        getDataFromExistingBook.addActionListener((ActionEvent ae) -> {
            try {
                Book[] recentlyGottenBooks = bookService.getBooks();
                int[] ids = new int[recentlyGottenBooks.length];
                String[] bookNames = new String[recentlyGottenBooks.length];
                for (int i = 0; i < bookNames.length; i++) {
                    bookNames[i] = recentlyGottenBooks[i].getId() + ": " + recentlyGottenBooks[i].getName();
                }

                Object chosenObj = JOptionPane.showInputDialog(null,
                        "Elige uno de los libros", "Elección de libros",
                        JOptionPane.PLAIN_MESSAGE, null, bookNames, null);

                if (chosenObj == null) {
                    return;
                }

                String chosenString = (String) chosenObj;

                int chosenId = Integer.valueOf(chosenString.split("\\:")[0]);

                Book chosenBook = null;
                for (Book recentlyGottenBook : recentlyGottenBooks) {
                    if (recentlyGottenBook.getId() == chosenId) {
                        chosenBook = recentlyGottenBook;
                        break;
                    }
                }

                while (chosenBook == null) {
                    chosenObj = JOptionPane.showInputDialog(null,
                            "Ocurrió un error, vuelva a elegir uno de los libros", "Elección de libros",
                            JOptionPane.PLAIN_MESSAGE, null, bookNames, null);

                    if (chosenObj == null) {
                        return;
                    }

                    chosenString = (String) chosenObj;

                    chosenId = Integer.valueOf(chosenString.split("\\:")[0]);

                    chosenBook = null;
                    for (Book recentlyGottenBook : recentlyGottenBooks) {
                        if (recentlyGottenBook.getId() == chosenId) {
                            chosenBook = recentlyGottenBook;
                            break;
                        }
                    }
                }

                maybeBook = chosenBook;

                name.setText(chosenBook.getName());
                publisher.setText(chosenBook.getPublisher());

                Author[] authors = bookService.getAuthors(chosenId);

                authorsTxt.setText("");
                for (Author author : authors) {
                    authorsTxt.append(author.getName() + "\n");
                }
                
                this.name.setEditable(false);
                this.publisher.setEditable(false);

            } catch (RemoteException | SQLException ex) {
                System.err.println(ex.getMessage());
            }
        });

    }

}
