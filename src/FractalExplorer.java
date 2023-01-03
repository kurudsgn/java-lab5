import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {

    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(600);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
    private int size;
    private JImageDisplay image;
    private FractalGenerator gen;
    private Rectangle2D.Double range;

    private JComboBox<FractalGenerator> fractalSelector;
    public FractalExplorer(int s) {
        size = s;
        gen = new Mandelbrot();
        range = new Rectangle2D.Double();
        gen.getInitialRange(range);
        image = new JImageDisplay(size, size);
    }

    public void createAndShowGUI() {
        image.setLayout(new BorderLayout());
        JFrame frame = new JFrame("Fractal Explorer");
        frame.add(image, BorderLayout.CENTER);
        JButton reset = new JButton("Reset");
        JButton save = new JButton("Save");
        JLabel label = new JLabel("Fractal:");
        fractalSelector = new JComboBox<>();
        fractalSelector.addItem(new Mandelbrot());
        fractalSelector.addItem(new Tricorn());
        fractalSelector.addItem(new BurningShip());

        ButtonClick resetHandler = new ButtonClick();
        ButtonClick saveHandler = new ButtonClick();
        ComboBoxClick comboHandler = new ComboBoxClick();
        reset.addActionListener(resetHandler);
        save.addActionListener(saveHandler);
        fractalSelector.addActionListener(comboHandler);




        MouseHandler click = new MouseHandler();
        image.addMouseListener(click);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel myPanel = new JPanel();
        myPanel.add(label, BorderLayout.CENTER);
        myPanel.add(fractalSelector, BorderLayout.CENTER);
        frame.add(myPanel, BorderLayout.NORTH);

        JPanel myBottomPanel = new JPanel();
        myBottomPanel.add(reset);
        myBottomPanel.add(save);
        frame.add(myBottomPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                double xCoord = gen.getCoord(range.x,
                        range.x + range.width, size, x);
                double yCoord = gen.getCoord(range.y,
                        range.y + range.height, size, y);

                int numIters = gen.numIterations(xCoord, yCoord);
                if (numIters == -1) {
                    image.drawPixel(x, y, 0);
                } else {
                    float hue = 0.7f + (float) numIters / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);

                    image.drawPixel(x, y, rgbColor);
                }
            }
            image.repaint();

        }
    }

    private class ButtonClick implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            String command = e.getActionCommand();


            if (command.equals("Reset")) {
                gen.getInitialRange(range);
                drawFractal();
            }
            if (command.equals("Save")){
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("PNG Images", "png");
                fileChooser.setFileFilter(fileFilter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int t = fileChooser.showSaveDialog(image);
                if (t == JFileChooser.APPROVE_OPTION) {
                    try {
                        ImageIO.write(image.img, "png", fileChooser.getSelectedFile());
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(
                                image,
                                ee.getMessage(),
                                "Error saving fractal",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        }
    }
    private class ComboBoxClick implements ActionListener
    {
        public void actionPerformed(ActionEvent a){
            gen = (FractalGenerator) fractalSelector.getSelectedItem();
            gen.getInitialRange(range);
            drawFractal();
        }
    }
    private class MouseHandler extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            double xCoord = gen.getCoord(range.x,
                    range.x + range.width, size, x);

            int y = e.getY();
            double yCoord = gen.getCoord(range.y, range.y + range.height, size, y);

            gen.recenterAndZoomRange(range, xCoord, yCoord, 0.5);

            drawFractal();
        }
    }
}
