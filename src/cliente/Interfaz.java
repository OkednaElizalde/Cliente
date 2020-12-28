package cliente;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Nadia
 */
public class Interfaz extends JFrame {

    JPanel panel;
    JScrollPane scroll;
    JPanel libro;
    JSeparator sepa;
    JLabel bib;
    JLabel labelt;
    JLabel labelAut;
    JLabel labelid;
    JLabel total;
    JLabel prestados;
    JButton agregar;
    /*
    static void main(String[] args){
       Interfaz obj = new Interfaz();
       obj.setVisible(true);
      
    }*/
    public Interfaz() {

        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Cliente");
        setLocationRelativeTo(null);
        agregar();
        principal();
        DescripcionLibro("Holi", "Holi", "Holi", "Holi", "Holi");
    }

    private void agregar() {
        panel = new JPanel();
        panel.setLayout(null);
        
        this.getContentPane().add(panel);
        //Labels
        JLabel fondo = new JLabel();
        labelt = new JLabel("Título del libro");
        labelAut = new JLabel("Autor del libro");
        labelid = new JLabel("Editorial del libro");
        JSpinner tot = new JSpinner();
        JSpinner pres = new JSpinner();
        JTextField txtT = new JTextField();
        JTextField txtAut = new JTextField();
        JTextField txtid = new JTextField();
        total = new JLabel("Total");
        prestados = new JLabel("Libros prestados");
        JButton bagr = new JButton("Agregar");
        JButton bsc = new JButton("Buscar");
        JButton bcanc = new JButton("Cancelar");
        //ImageIcon ifondo = new ImageIcon();
        //SetFont
        //fondo.setIcon("", ifondo);
        //lblSetBounds
        labelt.setBounds(20, 20, 100, 10);
        labelAut.setBounds(20, 70, 100, 10);
        labelid.setBounds(20, 120, 100, 10);
        ///
        tot.setBounds(100, 180, 50, 30);
        pres.setBounds(300, 180, 50, 30);
        total.setBounds(100, 220, 100, 20);
        prestados.setBounds(280, 220, 200, 20);
        //fondo.setSize(500, 500);
        //txtsetBounds
        txtT.setBounds(150, 20, 150, 20);
        txtAut.setBounds(150, 70, 150, 20);
        txtid.setBounds(150, 120, 150, 20);

        //Botones
        bagr.setBounds(180, 260, 100, 40);

        //add
        panel.add(txtT);
        panel.add(txtAut);
        panel.add(txtid);
        panel.add(labelt);
        panel.add(labelAut);
        panel.add(labelid);
        panel.add(bagr);

        panel.add(total);
        panel.add(tot);
        panel.add(prestados);
        panel.add(pres);
    }

    public void principal() {

        panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        ///Label
        bib = new JLabel("Biblioteca");
        JLabel bus = new JLabel("Ingresa el libro que deseas buscar");
        scroll = new JScrollPane();
        ///Text
        JTextField txtbib = new JTextField();
        ///Botones
        agregar = new JButton("Agregar");
        JButton bsc = new JButton("Buscar");
        ///Size
        bib.setBounds(200, 20, 100, 10);
        bus.setBounds(20, 50, 500, 20);
        txtbib.setBounds(20, 80, 200, 20);
        scroll.setBounds(30, 120, 400, 250);
        bsc.setBounds(300, 80, 100, 20);
        agregar.setBounds(300, 420, 100, 20);

        ///
        ActionListener agre = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregar();
            }
        };
        agregar.addActionListener(agre);

        ///add
        panel.add(bib);
        panel.add(bus);
        panel.add(txtbib);
        panel.add(scroll);
        panel.add(agregar);
        panel.add(bsc);
    }

    public void DescripcionLibro(String stitulo, String sautor, String sedc, String slbp, String sttl) {

        sepa = new JSeparator();
        libro = new JPanel();
        libro.setLayout(null);
        libro.setPreferredSize(new Dimension(600,600));
        scroll.setViewportView(libro);
        JButton edit = new JButton("Editar");
        JButton eliminar = new JButton("Eliminar");
        JLabel titulo = new JLabel("Título: " + stitulo);
        JLabel Autor = new JLabel("Autor: " + sautor);
        JLabel Edicion = new JLabel("Edición: " + sedc);
        JLabel lbp = new JLabel("Libros prestados: " + slbp);
        JLabel ttl = new JLabel("Total de libros: " + sttl);
        ///
        sepa.setBounds(20, 180, 300, 10);
        titulo.setBounds(30, 20, 200, 20);
        Autor.setBounds(30, 50, 200, 20);
        Edicion.setBounds(30, 80, 200, 20);
        lbp.setBounds(30, 110, 200, 20);
        ttl.setBounds(30, 540, 200, 20);
        edit.setBounds(180, 200, 100, 20);
        eliminar.setBounds(290, 200, 100, 20);
        ///
        libro.add(titulo);
        libro.add(Autor);
        libro.add(Edicion);
        libro.add(lbp);
        libro.add(ttl);
        libro.add(edit);
        libro.add(eliminar);
        libro.add(sepa);
        ///ActionListener

    }

}
