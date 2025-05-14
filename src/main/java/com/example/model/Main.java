package com.example.model;

import com.example.Controller;
import com.example.view.*;

import java.io.IOException;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.List;

import static com.example.model.TectonSize.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Divided Circles");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 900);

            GamePanel gamePanel = new GamePanel();
            frame.add(gamePanel);

            // Create an instance of the Controller
            Controller controller = new Controller();

            // Add the Controller as a KeyListener
            frame.addKeyListener(controller);


            Magmox t1 = new Magmox(GIANT, "Magmox");
            TectonView tv1 = new TectonView(t1, new Position(200,200));
            Mycologist m1 = new Mycologist("Hyphara");
            Hyphara h1 = new Hyphara(t1, m1, "Hyphara");
            gamePanel.add(tv1);

            Transix t2 = new Transix(SMALL, "Transix");
            TectonView tv2 = new TectonView(t2, new Position(400,200));
            Mycologist m2 = new Mycologist("Gilledon");
            Gilledon g1 = new Gilledon(t2, m2, "Gilledon");
            gamePanel.add(tv2);

            HypharaSpore s1 = new HypharaSpore(h1);
            HypharaSpore s2 = new HypharaSpore(h1);

            GilledonSpore s3 = new GilledonSpore(g1);
            GilledonSpore s4 = new GilledonSpore(g1);

            Mycologist m3 = new Mycologist("Capulon");
            Mycologist m4 = new Mycologist("Poralia");

            Mantleon t3 = new Mantleon(BIG, "Mantleon");
            Orogenix t4 = new Orogenix(MEDIUM, "Orogenix");

            Capulon c1 = new Capulon(t3, m3, "Capulon");
            Poralia p1 = new Poralia(t4, m4, "Poralia");

            CapulonSpore s5 = new CapulonSpore(c1);
            PoraliaSpore s6 = new PoraliaSpore(p1);

            t1.addSpore(s1);
            t1.addSpore(s2);
            t1.addSpore(s3);
            t1.addSpore(s4);
            t1.addSpore(s5);
            t1.addSpore(s6);
            t2.addSpore(s3);
            t2.addSpore(s4);

            frame.setVisible(true);
        });
    }
}
//    /**
//     * Beolvassa a megadott fájlból a használati esetek listáját és visszaadja egy Mapet.
//     *
//     * @param filePath A fájl elérési útja, amely a használati eseteket tartalmazza.
//     * @return Egy Map, amely a használati esetek nevét és a hozzájuk tartozó metódusneveket tartalmazza.
//     * @throws IOException Ha hiba történik a fájl beolvasása közben.
//     */
//    private static Map<String, String> getUseCaseMap(String filePath) throws IOException {
//        Map<String, String> useCaseMap = new LinkedHashMap<>();
//        List<String> lines = Files.readAllLines(Path.of(filePath));
//        for (String line : lines) {
//            String[] parts = line.split(",", 2);
//            String useCaseName = parts[0].trim();
//            String methodName = (parts.length > 1 && !parts[1].trim().isEmpty()) ? parts[1].trim() : null;
//            useCaseMap.put(useCaseName, methodName);
//        }
//        return useCaseMap;
//    }
//
//    /**
//     * Kiírja a konzolra az elérhető használati esetek listáját.
//     *
//     * @param useCaseMap A használati esetek nevét és a hozzájuk tartozó metódusneveket tartalmazó Map.
//     */
//    private static void printUseCases(Map<String,String> useCaseMap) {
//        System.out.println("Available use cases:");
//        int useCaseCount = 1;
//        for (String useCase : useCaseMap.keySet()) {
//            String format = useCaseCount < 10 ? "0" + useCaseCount : "" + useCaseCount;
//            System.out.println(format + "\t" + useCase);
//            useCaseCount++;
//        }
//    }
//
//    /**
//     * Meghívja a Skeleton osztályban található metódust a megadott metódusnév alapján.
//     *
//     * @param methodName A meghívandó metódus neve.
//     */
//    private static void invokeSkeletonMethod(String methodName) {
//        try {
//            Class<?> skeletonClass = Skeleton.class;
//            skeletonClass.getMethod(methodName).invoke(null);
//        } catch (NoSuchMethodException exception) {
//            System.out.println("No such method in Skeleton: " + methodName);
//        } catch (Exception exception) {
//            System.out.println("Error executing method: " + exception.getMessage());
//        }
//    }
//}