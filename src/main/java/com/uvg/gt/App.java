package com.uvg.gt;

import java.io.File;
import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

import com.uvg.gt.Model.Climates;
import com.uvg.gt.Model.DataParser;
import com.uvg.gt.Model.Node;
import com.uvg.gt.Model.Relationship;

public class App {

    public static void main(String[] args) {
        clearConsole();
        showTitle(_title, ANSI_PURPLE);
        final HashMap<Climates, String> translate = new HashMap<>() {
            {
                put(Climates.NORMAL, "Normal");
                put(Climates.BLIZZARD, "Nevado");
                put(Climates.RAIN, "Lluvia");
                put(Climates.STORM, "Tormenta");
            }
        };

        final var parser = new DataParser();
        final List<Relationship> relations = new ArrayList<>();
        while (true) {
            var filePath = formLabel("Ingrese la ruta al archivo con datos", ANSI_CYAN);
            try {
                var file = new File(filePath);
                var sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    relations.add(parser.parse(sc.nextLine()));
                }
                sc.close();
                break;
            } catch (Exception e) {
                writeErrorMessage("¡Por favor ingrese una ruta válida!");
                continue;
            }
        }

        final IGraph graph = new Graph(relations);
        final IPathFinder pathFinder = new PathFinder(graph);
        pathFinder.updateShortestPath();

        while (true) {
            clearConsole();
            final var nodes = graph.getNodes();
            final var matrix = pathFinder.adjacencyMatrixAsTable();
            consoleWriteLine("La matriz de adyacencia para el grafo es:");
            consoleWriteLine(matrix);
            consoleWriteLine(SUB_DIVIDER);

            printSpaceSeparated("1)", "Calcular mejor ruta", ANSI_YELLOW);
            printSpaceSeparated("2)", "Encontrar ciudad central", ANSI_YELLOW);
            printSpaceSeparated("3)", "Remover carretera", ANSI_YELLOW);
            printSpaceSeparated("4)", "Construir carretera", ANSI_YELLOW);
            printSpaceSeparated("5)", "Ver clima", ANSI_YELLOW);
            printSpaceSeparated("6)", "Cambiar clima", ANSI_YELLOW);
            printSpaceSeparated("q)", "Salir", ANSI_YELLOW);

            var answer = formLabel("Por favor seleccione una opción", ANSI_CYAN);

            if (answer.equals("q")) {
                break;
            }

            if (answer.equals("1")) {
                showNodes(nodes);
                var limits = askForLimits(nodes);
                var path = pathFinder.constructPath(limits.getOrigin(), limits.getDestination());
                if (path.isEmpty()) {
                    writeErrorMessage("No se pudo identificar el mejor camino!");
                } else {
                    consoleWriteLine("El mejor camino es:");
                    for (int i = 0; i < path.get().size(); i++) {
                        final var postfix = i + 1 == path.get().size() ? "" : " -> ";
                        consoleWrite(String.format("(%s)", path.get().get(i).getLabel()), ANSI_YELLOW);
                        consoleWrite(postfix, ANSI_PURPLE);
                    }
                    consoleWriteLine("");
                    consoleWriteLine(String.format("Con una longitud de: %.0f",
                            pathFinder.getShortestPath(limits.getOrigin(), limits.getDestination())));
                }

            } else if (answer.equals("2")) {
                progressBar("Finding\t", FRAMES);
                var center = pathFinder.getCentralNode();
                consoleWriteLine(center.getLabel(), ANSI_GREEN);

            } else if (answer.equals("3")) {
                showNodes(nodes);
                var limits = askForLimits(nodes);

                graph.removeRelation(limits.getOrigin(), limits.getDestination());
                pathFinder.updateShortestPath();
                writeSuccessMessage("¡La carretera fue removida satisfactoriamente!");
            } else if (answer.equals("4")) {
                showNodes(nodes);
                var limits = askForLimits(nodes);

                consoleWriteLine(SUB_DIVIDER);
                consoleWriteLine("Tiempos de recorrido", ANSI_WHITE, ANSI_PURPLE_BACKGROUND);
                Function<String, Boolean> positiveIntCheck = s -> {
                    try {
                        var n = Integer.parseInt(s);
                        return n >= 0;
                    } catch (Exception e) {
                        writeErrorMessage("¡Por favor ingrese un tiempo válido!");
                        return false;
                    }
                };
                final var normalWeight = formLabel("Ingrese el tiempo normal", ANSI_CYAN, positiveIntCheck,
                        Integer::parseInt);
                final var rainWeight = formLabel("Ingrese el tiempo en llovizna", ANSI_CYAN, positiveIntCheck,
                        Integer::parseInt);
                final var blizzardWeight = formLabel("Ingrese el tiempo con nieve", ANSI_CYAN, positiveIntCheck,
                        Integer::parseInt);
                final var stormWeight = formLabel("Ingrese el tiempo en tormenta", ANSI_CYAN, positiveIntCheck,
                        Integer::parseInt);

                final HashMap<Climates, Integer> weights = new HashMap<>() {
                    {
                        put(Climates.NORMAL, normalWeight);
                        put(Climates.RAIN, rainWeight);
                        put(Climates.BLIZZARD, blizzardWeight);
                        put(Climates.STORM, stormWeight);
                    }
                };

                final var relation = new Relationship(limits.getOrigin(), limits.getDestination(), weights);
                graph.addRelation(relation);
                pathFinder.updateShortestPath();
                writeSuccessMessage("¡La carretera fue creada satisfactoriamente!");
            } else if (answer.equals("5")) {
                showNodes(nodes);

                Optional<Relationship> result = Optional.empty();
                while (result.isEmpty()) {
                    var limits = askForLimits(nodes);
                    result = graph.getRelation(limits.getOrigin(), limits.getDestination());

                    if (result.isEmpty()) {
                        writeErrorMessage(String.format("No existe una carretera entre %s y %s", limits.getOrigin(),
                                limits.getDestination()));
                    }
                }

                result.ifPresent(relation -> {
                    writeSuccessMessage("El clima es: " + translate.get(relation.getCurrentClimate()));
                });
            } else if (answer.equals("6")) {
                showNodes(nodes);

                Optional<Relationship> result = Optional.empty();
                while (result.isEmpty()) {
                    var limits = askForLimits(nodes);
                    result = graph.getRelation(limits.getOrigin(), limits.getDestination());

                    if (result.isEmpty()) {
                        writeErrorMessage(String.format("No existe una carretera entre %s y %s", limits.getOrigin(),
                                limits.getDestination()));
                    }
                }

                result.ifPresent(r -> {
                    printSpaceSeparated("1)", translate.get(Climates.NORMAL), ANSI_YELLOW);
                    printSpaceSeparated("2)", translate.get(Climates.BLIZZARD), ANSI_YELLOW);
                    printSpaceSeparated("3)", translate.get(Climates.RAIN), ANSI_YELLOW);
                    printSpaceSeparated("4)", translate.get(Climates.STORM), ANSI_YELLOW);

                    var selectedClimate = formLabel("Seleccione un clima", ANSI_CYAN, s -> {
                        try {
                            final var n = Integer.parseInt(s);
                            return n > 0 && n <= 4;
                        } catch (Exception e) {
                            writeErrorMessage("Por favor seleccione un clima válido!");
                            return false;
                        }
                    }, s -> {
                        switch (s) {
                            case "1":
                                return Climates.NORMAL;
                            case "2":
                                return Climates.BLIZZARD;
                            case "3":
                                return Climates.RAIN;
                            case "4":
                                return Climates.STORM;
                            default:
                                throw new NoSuchElementException();
                        }
                    });

                    r.setClimate(selectedClimate);
                });
                writeSuccessMessage("El clima ha sido cambiado!");
                pathFinder.updateShortestPath();
            } else {
                writeErrorMessage("¡Selecciona una opción válida!");
            }

            consoleWriteLine("Presione enter para continuar...");
            readLine();
        }

        consoleWriteLine("Gracias por usar nuestro servicio");
    }

    private static void showNodes(List<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            printSpaceSeparated(i + 1 + ")", nodes.get(i).getLabel(), ANSI_CYAN);
        }
    }

    private static Limits<Node> askForLimits(List<Node> nodes) {
        return askForLimits(nodes, "Seleccione la ciudad de origen", "Seleccione la ciudad de destino");
    }

    private static Limits<Node> askForLimits(List<Node> nodes, String originLabel, String destinationLabel) {
        final Function<String, Boolean> isIntAndInList = s -> {
            try {
                var n = Integer.parseInt(s);
                return n > 0 && n <= nodes.size();
            } catch (Exception e) {
                writeErrorMessage("¡Por favor seleccione una opción válida!");
                return false;
            }
        };
        final var originIndex = formLabel(originLabel, ANSI_CYAN, isIntAndInList,
                Integer::parseInt) - 1;
        final var destinationIndex = formLabel(destinationLabel, ANSI_CYAN,
                isIntAndInList, Integer::parseInt) - 1;

        return new Limits<>(nodes.get(originIndex), nodes.get(destinationIndex));
    }

    private static class Limits<T> {
        private T origin;
        private T destination;

        public Limits(T origin, T destination) {
            this.origin = origin;
            this.destination = destination;

        }

        public T getOrigin() {
            return origin;
        }

        public T getDestination() {
            return destination;
        }
    }

    public static ArrayList<Object> _title = new ArrayList<>() {
        {
            add("..######..####.########.####.########..######.");
            add(".##....##..##.....##.....##..##.......##....##");
            add(".##........##.....##.....##..##.......##......");
            add(".##........##.....##.....##..######....######.");
            add(".##........##.....##.....##..##.............##");
            add(".##....##..##.....##.....##..##.......##....##");
            add("..######..####....##....####.########..######.");
        }
    };

    public static void writeWarningMessage(Object message) {
        consoleWriteLine(message, ANSI_YELLOW);
    }

    public static <T> void printList(Object title, Iterable<T> list) {
        consoleWriteLine(title, ANSI_CYAN);
        for (T itemT : list) {
            System.out.println(itemT.toString() + " ");
        }
    }

    public static <T> void printTable(Object title, Iterable<T> list, int columnWidth, String separator) {
        consoleWriteLine(title, ANSI_CYAN);
        int i = 0;
        for (T itemT : list) {
            if (i % columnWidth == 0) {
                System.out.println();
            }
            System.out.print(itemT.toString() + separator);
            i++;
        }
        System.out.println();
    }

    public static void writeErrorMessage(Object message) {
        consoleWriteLine(message, ANSI_WHITE, ANSI_RED_BACKGROUND);
    }

    public static void writeSuccessMessage(Object message) {
        consoleWriteLine(message, ANSI_WHITE, ANSI_GREEN_BACKGROUND);
    }

    public static void showTitle(ArrayList<Object> titulo, String foreground) {
        for (Object line : titulo) {
            consoleWriteLine(line, foreground);
        }
    }

    public static <T> void progressBar(Object prefix, Iterable<Object> frames) {
        progressBar(prefix, frames, 3000, ANSI_YELLOW);
    }

    public static <T> void progressBar(Object prefix, Iterable<Object> frames, long durationInMs, Object color) {
        final long START_MS = Clock.systemUTC().instant().toEpochMilli();
        boolean startAgain = true;

        System.out.print(String.format("%s%s", color, prefix));
        while (startAgain) {
            for (Object frame : frames) {
                final String stringFrame = frame.toString();
                System.out.print(stringFrame);

                final long CURRENT_MS = Clock.systemUTC().instant().toEpochMilli();
                if (CURRENT_MS - START_MS > durationInMs) {
                    System.out.println(ANSI_RESET);
                    startAgain = false;
                    break;
                }

                try {
                    Thread.sleep(200, 0);
                } catch (Exception e) {
                }

                for (int i = 0; i < stringFrame.length(); i++) {
                    System.out.print("\b \b"); // \b is a not destructive backspace, that's why we need the space
                }
            }
        }

    }

    public static <T> void showInTable(T item, Function<T, LinkedHashMap<Object, Object>> convertToMap) {
        Integer maxLength = null;
        var rows = convertToMap.apply(item);
        if (maxLength == null) {
            maxLength = rows.keySet().stream()
                    .map(o -> o.toString().length())
                    .max(Integer::compare)
                    .orElse(0) + 1;
        }

        for (Object rowName : rows.keySet()) {
            int spacing = maxLength - rowName.toString().length() + rows.get(rowName).toString().length();
            var format = "%" + spacing + "s";
            consoleWrite(rowName + ":", ANSI_CYAN);
            consoleWriteLine(String.format(format, rows.get(rowName)));
        }
    }

    public static <T> void showInTable(Iterable<T> list,
            Function<T, LinkedHashMap<Object, Object>> convertToMap) {
        Integer maxLength = null;
        for (T itemT : list) {
            var rows = convertToMap.apply(itemT);
            if (maxLength == null) {
                maxLength = rows.keySet().stream()
                        .map(o -> o.toString().length())
                        .max(Integer::compare)
                        .orElse(0) + 1;
            }
            for (Object rowName : rows.keySet()) {
                int spacing = maxLength - rowName.toString().length() + rows.get(rowName).toString().length();
                var format = "%" + spacing + "s";
                consoleWrite(rowName + ":", ANSI_CYAN);
                consoleWriteLine(String.format(format, rows.get(rowName)));
            }
            consoleWriteLine(SUB_DIVIDER);
        }
    }

    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_BLACK = "\u001B[30m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_PURPLE = "\u001B[35m";
    static final String ANSI_CYAN = "\u001B[36m";
    static final String ANSI_WHITE = "\u001B[37m";
    static final Scanner sc = new Scanner(System.in);

    static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    static final int DIVIDER_LENGTH = 32;
    static final int DIVIDER_HALF_LENGTH = DIVIDER_LENGTH / 2;
    static final String DIVIDER = new String(new char[DIVIDER_LENGTH]).replace("\0", "=");
    static final String SUB_DIVIDER = new String(new char[DIVIDER_LENGTH]).replace("\0", "-");
    static final Iterable<Object> FRAMES = new ArrayList<>() {
        {
            add("-");
            add("\\");
            add("|");
            add("/");
        }
    };

    public static String formLabel(Object label, String color) {
        consoleWrite(label + ": ");
        return waitForInputColored(color);
    }

    public static String formLabel(Object label, String color, Function<String, Boolean> check) {
        String input = "";
        do {
            input = formLabel(label, color);
        } while (!check.apply(input));

        return input;
    }

    public static <T> T formLabel(Object label, String color, Function<String, Boolean> check,
            Function<String, T> conv) {
        String input = formLabel(label, color, check);
        return conv.apply(input);
    }

    public static String waitForInputColored(String color) {
        System.out.print(color);
        String s = readLine();
        System.out.print(ANSI_RESET);
        return s;
    }

    public static void printSpaceSeparated(Object identifier, Object option, String color) {
        consoleWrite(identifier + " ", color);
        consoleWriteLine(option.toString());
    }

    public static void printSpaceSeparatedFor(Object identifier, Object option, String color) {
        consoleWrite(identifier + " ");
        consoleWriteLine(option.toString(), color);
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void consoleWriteLine(Object message) {
        consoleWriteLine(message, "", "");
    }

    public static void consoleWriteLine(Object message, String foreground) {
        consoleWriteLine(message, foreground, "");
    }

    public static void consoleWriteLine(Object message, String foreground, String background) {
        System.out.println(background + foreground + message + ANSI_RESET);
    }

    public static void consoleWrite(Object message) {
        consoleWrite(message, "", "");
    }

    public static void consoleWrite(Object message, String foreground) {
        consoleWrite(message, foreground, "");
    }

    public static void consoleWrite(Object message, String foreground, String background) {
        System.out.print(background + foreground + message + ANSI_RESET);
    }

    public static String readLine() {
        return sc.nextLine();
    }
}