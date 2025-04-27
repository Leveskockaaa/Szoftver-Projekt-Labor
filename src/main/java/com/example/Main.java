package com.example;

import java.io.IOException;
import java.util.*;

/**
 * A fő osztály, amely a program belépési pontját tartalmazza.
 */
public class Main {
    static Controller controller = new Controller();
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        Controller.setScanner(scanner);
        System.out.println("Játék vagy teszt üzemmódban szeretnéd elindítani a programot?");
        System.out.println("1. Játék üzemmód");
        System.out.println("2. Teszt üzemmód");
        int mode = scanner.nextInt();
        scanner.nextLine();
        while (true) {
            if (mode == 1){
                Controller.setTestMode(false);
                Controller.setIsRandomOn(true);
                if(scanner.hasNextLine()){
                    String nextCommand = scanner.nextLine();
                    if (nextCommand.equalsIgnoreCase("exit")) {
                        break;
                    }
                    controller.runCommand(nextCommand);
                }
            } else if (mode == 2) {
                Controller.setTestMode(true);
                Controller.setIsRandomOn(false);
                List<String> tests = controller.initTests("src/main/resources/test-cases.txt");

                System.out.println("Tesztesetek kiírásához üss egy entert!");
                scanner.nextLine();
                for (int i = 0; i < tests.size(); i++) {
                    System.out.println((i + 1) + ". teszt: " + tests.get(i));
                }
                String testCaseNumber = scanner.nextLine();
                int testCaseNumberInt;
                if (testCaseNumber.equals("exit")) {
                    break;
                } else {
                    testCaseNumberInt = Integer.parseInt(testCaseNumber);
                }
                controller.runTest(testCaseNumberInt);
            } else {
                System.exit(0);
            }
        }
        scanner.close();

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
}