/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Willy
 */
public class CreateBook extends willy.gui.Ventana implements SwingConstants {

    private final JLabel title = new JLabel("Edición de libro", CENTER);
    private final JLabel nameLabel = new JLabel("Nombre:", LEFT);
    private final JTextField name = new JTextField();
    private final JLabel publisherLabel = new JLabel("Editorial:", LEFT);
    private final JTextField publisher = new JTextField();
    private final JLabel authorsLabel = new JLabel("Autores:", LEFT);
    private final JTextArea authorsTxt = new JTextArea();
    private final JButton addAuthor = new JButton("Añadir autor");
    private final JButton createAuthor = new JButton("Crear autor");
    private final JLabel totalLabel = new JLabel("Libros en la biblioteca:", CENTER);
    private final JSpinner total = new JSpinner(new SpinnerNumberModel(1, 1, 2147483647, 1));
    private final JLabel borrowedLabel = new JLabel("Libros prestados:", CENTER);
    private final JSpinner borrowed = new JSpinner(new SpinnerNumberModel(0, 0, 2147483647, 1));
    
    private final JButton okButton = new JButton("Ok");
    private final JButton cancelButton = new JButton("Cancelar");

    public CreateBook(String title, int w, int h, boolean resizable) {
        super(title, w, h, resizable);
        super.getContentPane().setLayout(null);
    }

    @Override
    public void setComp() {
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        title.setBounds(20, 20, 360, 25);
        super.addComp(title);

        nameLabel.setBounds(20, 60, 53, 20);
        super.addComp(nameLabel);

        name.setBounds(80, 60, 200, 20);
        super.addComp(name);

        publisherLabel.setBounds(20, 85, 53, 20);
        super.addComp(publisherLabel);

        publisher.setBounds(80, 85, 200, 20);
        super.addComp(publisher);

        authorsLabel.setBounds(20, 110, 55, 20);
        super.addComp(authorsLabel);

        final JScrollPane jsp = new JScrollPane(authorsTxt);
        jsp.setBounds(20, 130, 360, 100);
        jsp.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        jsp.setEnabled(false);
        super.addComp(jsp);

        addAuthor.setBounds(20, 235, 175, 20);
        super.addComp(addAuthor);

        createAuthor.setBounds(205, 235, 175, 20);
        super.addComp(createAuthor);

        totalLabel.setBounds(20, 265, 175, 20);
        super.addComp(totalLabel);

        borrowedLabel.setBounds(205, 265, 175, 20);
        super.addComp(borrowedLabel);

        total.setBounds(20, 290, 175, 20);
        super.addComp(total);

        borrowed.setBounds(205, 290, 175, 20);
        super.addComp(borrowed);
        
        cancelButton.setBounds(190, 320, 90, 20);
        super.addComp(cancelButton);

        okButton.setBounds(290, 320, 90, 20);
        super.addComp(okButton);

        borrowed.addChangeListener((ChangeEvent ce) -> {
            if (((int) borrowed.getValue()) > ((int) total.getValue())) {
                JOptionPane.showMessageDialog(null, "No puedes prestar más libros de los que tienes");
                borrowed.setValue((int) total.getValue());
            }
        });

    }

}
