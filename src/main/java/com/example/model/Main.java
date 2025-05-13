package com.example.model;

import com.example.Controller;
import com.example.view.Position;
import com.example.view.TectonView;

import java.io.IOException;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;

import static com.example.model.TectonSize.GIANT;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Divided Circles");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 900);

            TectonView t1 = new TectonView(new Magmox(GIANT, "Magmox"));
            t1.draw(new Position(400, 400), 1.0f, frame);
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