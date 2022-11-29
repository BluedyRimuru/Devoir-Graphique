package Vues;

import Controlers.CtrlGraphique;
import Entities.DatasGraph;
import Tools.ConnexionBDD;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultKeyedValues2DDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.util.Rotation;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

public class FrmGraphique extends JFrame{
    private JPanel pnlGraph1;
    private JPanel pnlGraph2;
    private JPanel pnlGraph3;
    private JPanel pnlGraph4;
    private JPanel pnlRoot;
    CtrlGraphique ctrlGraphique;

    public FrmGraphique() throws SQLException, ClassNotFoundException {
        this.setTitle("Devoir graphique");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        ConnexionBDD cnx = new ConnexionBDD();
        ctrlGraphique = new CtrlGraphique();

        // GRAPHIQUE NUMERO 1

        DefaultCategoryDataset donnees1 = new DefaultCategoryDataset();
        pnlGraph1.setLayout(new java.awt.BorderLayout());

        for (Map.Entry valeur : ctrlGraphique.GetDatasGraphique1().entrySet())
        {
            donnees1.setValue(Double.parseDouble(valeur.getValue().toString()), "", valeur.getKey().toString());
        }

        JFreeChart chart1 = ChartFactory.createLineChart("Moyenne des salaires par âge ", "Age", "Salaire (€)", donnees1);
        ChartPanel frame1 = new ChartPanel(chart1);
        pnlGraph1.add(frame1);
        pnlGraph1.validate();

        // GRAPHIQUE NUMERO 2

        DefaultCategoryDataset donnees2 = new DefaultCategoryDataset();

        String sexe;

//        for (Map.Entry valeur : ctrlGraphique.GetDatasGraphique4().entrySet())
//        {
//            nomSemestre = ((String[])valeur.getValue())[0].toString();
//            nomMagasin = ((String[])valeur.getValue())[1].toString();
//            prix = Double.parseDouble(((String[])valeur.getValue())[2].toString());
//            //donnees.setValue(prix,nomAction,nomTrader);
//            donnees4.setValue(prix,nomMagasin,nomSemestre);
//        }

        JFreeChart chart2 = ChartFactory.createBarChart(
                "Pyramide des âges",
                "Age",
                "Hommes / Femmes",
                donnees2,
                PlotOrientation.HORIZONTAL,
                true, true, false);
        ChartPanel frame2 = new ChartPanel(chart2);
        pnlGraph2.add(frame2);
        pnlGraph2.validate();

        // GRAPHIQUE NUMERO 3

        DefaultPieDataset donnees3 = new DefaultPieDataset();

        for (Map.Entry valeur : ctrlGraphique.GetDatasGraphique3().entrySet())
        {
            donnees3.setValue(valeur.getKey().toString(), Integer.parseInt(valeur.getValue().toString()));
        }

//        Modifcation en 3D pour les tests -> createPieChart3D
        JFreeChart chart3 = ChartFactory.createPieChart(
                "Pourcentage de femmes et d'hommes",
                donnees3,
                true,
                true,
                false

        );
        ChartPanel frame3 = new ChartPanel(chart3);
        frame3.setMouseWheelEnabled(true);

        // Rotation - Tentative :
//        PiePlot3D piePlot3d = (PiePlot3D)chart3.getPlot();
//
//        piePlot3d.setStartAngle(180);
//        piePlot3d.setDirection(Rotation.CLOCKWISE);

        // Afficher le graphique :
        pnlGraph3.add(frame3);
        pnlGraph3.validate();

        // GRAPHIQUE NUMERO 4

        DefaultCategoryDataset donnees4 = new DefaultCategoryDataset();

        Double prix;
        String nomSemestre;
        String nomMagasin;

        for (Map.Entry valeur : ctrlGraphique.GetDatasGraphique4().entrySet())
        {
            nomSemestre = ((String[])valeur.getValue())[0].toString();
            nomMagasin = ((String[])valeur.getValue())[1].toString();
            prix = Double.parseDouble(((String[])valeur.getValue())[2].toString());
            //donnees.setValue(prix,nomAction,nomTrader);
            donnees4.setValue(prix,nomMagasin,nomSemestre);
        }

        JFreeChart chart4 = ChartFactory.createBarChart(
                "Montant des ventes par magasin",
                "Magasin",
                "Semestre",
                donnees4,
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel frame4 = new ChartPanel(chart4);
        pnlGraph4.add(frame4);
        pnlGraph4.validate();


    }
}
