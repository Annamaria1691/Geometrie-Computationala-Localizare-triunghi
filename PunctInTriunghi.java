import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PunctInTriunghi extends JFrame {
    Point a;
    Point b;
    Point c;
    private Point m = new Point(0, 0);
    private JPanel desen;


    PunctInTriunghi() {
        super("Punct in triunghi");

        desen = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int[] x = {a.getX(), b.getX(), c.getX()};
                int[] y = {a.getY(), b.getY(), c.getY()};
                g.drawPolygon(x, y, 3);
                g.setColor(Color.red);
                g.fillOval(m.getX()-3, m.getY(), 6, 6);
                Color roz=new Color(255, 0, 230);
                g.setColor(roz);
                g.drawString("M(x:" + m.getX() + " y:" + m.getY() + ")", m.getX() + 5, m.getY() - 5);
                g.setColor(Color.BLACK);
                g.drawString("A(x:" + a.getX() + " y:" + a.getY() + ")", a.getX() + 10, a.getY() + 2);
                g.drawString("B(x:" + b.getX() + " y:" + b.getY() + ")", b.getX() + 5, b.getY() - 5);
                g.drawString("C(x:" + c.getX() + " y:" + c.getY() + ")", c.getX() + 8, c.getY() - 3);

                g.drawString("1", (a.getX() + b.getX() + c.getX()) / 3, (a.getY() + b.getY() + c.getY()) / 3);
                g.drawString("2", a.getX() - 10, a.getY() + 50);
                g.drawString("3", b.getX() / 3, b.getY() / 2);
                g.drawString("4", c.getX() + 50, c.getY() / 2);
                g.drawString("5", b.getX(), (a.getY() + b.getY()) / 2);
                g.drawString("6", c.getX(), (a.getY() + c.getY()) / 2);
                g.drawString("7", (b.getX() + c.getX()) / 2, b.getY() - 10);
                g.setColor(Color.BLUE);

                g.drawLine(a.getX(), a.getY(), a.getX() + 50, 890);
                g.drawLine(a.getX(), a.getY(), a.getX() / 2, a.getY() + 100);
                g.drawLine(b.getX(), b.getY(), 0, 40);
                g.drawLine(b.getX(), b.getY(), 45, 0);
                g.drawLine(c.getX(), c.getY(), 600, 162);
                g.drawLine(c.getX(), c.getY(), 350, 0);

            }
        };

        JLabel text = new JLabel("Introduceti coordonatele punctului M:");
        JLabel xLabel = new JLabel("x:");
        JLabel yLabel = new JLabel("y:");
        JTextField xField = new JTextField(2);
        JTextField yField = new JTextField(2);
        JButton verifica = new JButton("Verifică Poziția Punctului M");

        verifica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int xM = Integer.parseInt(xField.getText());
                    int yM = Integer.parseInt(yField.getText());
                    m.setX(xM);
                    m.setY(yM);
                    desen.repaint();
                    verificaDeterminantTriunghi();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(PunctInTriunghi.this, "Introduceti coordonate valide!", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel coordonateInputPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        coordonateInputPanel.add(text);
        coordonateInputPanel.add(xLabel);
        coordonateInputPanel.add(xField);
        coordonateInputPanel.add(yLabel);
        coordonateInputPanel.add(yField);
        coordonateInputPanel.add(verifica);

        add(desen, BorderLayout.CENTER);
        add(coordonateInputPanel, BorderLayout.NORTH);

        pack();
        setVisible(true);
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public int calculeazaDeterminant(Point a, Point b, Point c) {
        return ((a.getX() * b.getY()) + (b.getX() * c.getY()) + (a.getY() * c.getX())) - ((b.getY() * c.getX()) + (a.getY() * b.getX()) + (a.getX() * c.getY()));
    }

    public void verificaDeterminantTriunghi() {
        int determinantABC = calculeazaDeterminant(a, b, c);
        if (determinantABC == 0)
            JOptionPane.showMessageDialog(this, "Punctele A, B, C sunt coliniare", "Puncte coliniare", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantABC < 0) {
            JOptionPane.showMessageDialog(this, "Triunghiul are sens invers trigonometric!", "Sens invers", JOptionPane.ERROR_MESSAGE);
        } else {
            zonaSensDirect();
        }
    }
    public void zonaSensDirect() {
        int determinantMAB = calculeazaDeterminant(m, a, b);
        int determinantMBC = calculeazaDeterminant(m, b, c);
        int determinantMCA = calculeazaDeterminant(m, c, a);
        if (determinantMAB == 0 && determinantMCA == 0)
            JOptionPane.showMessageDialog(this, "Punctul M coincide cu varful A", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB == 0 && determinantMBC == 0)
            JOptionPane.showMessageDialog(this, "Punctul M coincide cu varful B", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMCA == 0 && determinantMBC == 0)
            JOptionPane.showMessageDialog(this, "Punctul M coincide cu varful C", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB > 0 && determinantMBC > 0 && determinantMCA > 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine zonei 1", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB < 0 && determinantMBC > 0 && determinantMCA < 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine zonei 2", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB < 0 && determinantMBC < 0 && determinantMCA > 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine zonei 3", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB > 0 && determinantMBC < 0 && determinantMCA < 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine zonei 4", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB < 0 && determinantMBC > 0 && determinantMCA > 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine zonei 5", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB > 0 && determinantMBC > 0 && determinantMCA < 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine zonei 6", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB > 0 && determinantMBC < 0 && determinantMCA > 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine zonei 7", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB == 0 && determinantMBC > 0 && determinantMCA > 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine Frontierei 1 - 5", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB > 0 && determinantMBC > 0 && determinantMCA == 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine Frontierei 1 - 6", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB > 0 && determinantMBC == 0 && determinantMCA > 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine Frontierei 1 - 7", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB < 0 && determinantMBC > 0 && determinantMCA == 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine Frontierei 2 - 5", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB == 0 && determinantMBC > 0 && determinantMCA < 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine Frontierei 2 - 6", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB < 0 && determinantMBC == 0 && determinantMCA > 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine Frontierei 3 - 5", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB == 0 && determinantMBC < 0 && determinantMCA > 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine Frontierei 3 - 7", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB > 0 && determinantMBC == 0 && determinantMCA < 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine Frontierei 4 - 6", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
        else if (determinantMAB > 0 && determinantMBC < 0 && determinantMCA == 0)
            JOptionPane.showMessageDialog(this, "Punctul M apartine Frontierei 4 - 7", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        PunctInTriunghi main = new PunctInTriunghi();
        main.a = new Point(100, 500);
        main.b = new Point(50, 50);
        main.c = new Point(300, 100);
    }
}