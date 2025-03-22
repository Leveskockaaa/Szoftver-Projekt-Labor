package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        /* 
        Skeleton.initTestCase("function-call-test");
        Skeleton.logFunctionCall("user", "moveTo", "transix");
        Skeleton.logFunctionCall("insect", "placeInsect", "insect");
        Skeleton.logFunctionCall("transix", "neutralizeTectonEffects");
        Skeleton.logReturn("transix", "neutralizeTectonEffects");
        Skeleton.logFunctionCall("transix", "removeInsect");
        Skeleton.logFunctionCall("myself", "randomIntent");
        Skeleton.logReturn("myself", "randomIntent");
        Skeleton.logReturn("transix", "removeInsect");
        Skeleton.logFunctionCall("transix", "setNutrientMultiplier", "times");
        Skeleton.logReturn("transix", "setNutrientMultiplier");
        Skeleton.logReturn("insect", "placeInsect");

        Skeleton.initTestCase("branch-test");
        Skeleton.logFunctionCall("me", "ask", "you");
        Skeleton.logBranch("are you doing well?");
        Skeleton.logReturn("system", "ask");
*/
        String filePath = "src/main/resources/use-cases.txt";
        Map<String,String> useCaseMap = getUseCaseMap(filePath);
        printUseCases(useCaseMap);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            List<String> useCases = new ArrayList<>(useCaseMap.keySet());

            System.out.print("\nSelect use case (or type exit): ");
            String input = scanner.nextLine().trim();
            if (input.equals("exit")) break;

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

        }
        scanner.close();

        //Skeleton.insectEatHypahraSpore();
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