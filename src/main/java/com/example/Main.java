package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "src/main/resources/use-cases.txt";
        Map<String,String> useCaseMap = getUseCaseMap(filePath);
        printUseCases(useCaseMap);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            List<String> useCases = new ArrayList<>(useCaseMap.keySet());

            System.out.print("\nSelect use case (or type exit): ");
            String input = scanner.nextLine().trim();
            if (input.equals("exit")) break;
            while (input.matches("[a-zA-Z]+")) {
                System.out.print("Invalid input.\nSelect use case (or type exit): ");
                input = scanner.nextLine().trim();
            }
            int number = Integer.parseInt(input);
            if (number > useCases.size() || number < 1) {
                System.out.println("Invalid number");
                continue;
            }

            String useCase = useCases.get(number - 1);
            String methodName = useCaseMap.get(useCase);
            if (methodName == null) {
                System.out.println("Method not found");
                continue;
            }

            System.out.println("Selected use case: " + useCase + "\n");
            invokeSkeletonMethod(methodName);

            System.out.print("\nDo you want to continue? [y/n]: ");
            String proceed = scanner.nextLine().trim();
            boolean validInput = proceed.equals("y") || proceed.equals("n");
            while (!validInput) {
                System.out.print("Invalid input. \nDo you want to continue? [y/n]: ");
                proceed = scanner.nextLine().trim();
                validInput = proceed.equals("y") || proceed.equals("n");
            }
            if (proceed.equals("n")) {
                break;
            } else {
                printUseCases(useCaseMap);
            }

        }
        scanner.close();
    }

    private static Map<String, String> getUseCaseMap(String filePath) throws IOException {
        Map<String, String> useCaseMap = new LinkedHashMap<>();
        List<String> lines = Files.readAllLines(Path.of(filePath));
        for (String line : lines) {
            String[] parts = line.split(",", 2);
            String useCaseName = parts[0].trim();
            String methodName = (parts.length > 1 && !parts[1].trim().isEmpty()) ? parts[1].trim() : null;
            useCaseMap.put(useCaseName, methodName);
        }
        return useCaseMap;
    }

    private static void printUseCases(Map<String,String> useCaseMap) {
        System.out.println("Available use cases:");
        int useCaseCount = 1;
        for (String useCase : useCaseMap.keySet()) {
            String format = useCaseCount < 10 ? "0" + useCaseCount : "" + useCaseCount;
            System.out.println(format + "\t" + useCase);
            useCaseCount++;
        }
    }

    private static void invokeSkeletonMethod(String methodName) {
        try {
            Class<?> skeletonClass = Skeleton.class;
            skeletonClass.getMethod(methodName).invoke(null);
        } catch (NoSuchMethodException exception) {
            System.out.println("No such method in Skeleton: " + methodName);
        } catch (Exception exception) {
            System.out.println("Error executing method: " + exception.getMessage());
        }
    }
}