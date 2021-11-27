import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Week10 {
    /**
     * .
     *
     * @param fileContent .
     * @return .
     */
    public static List<String> getAllFunctions(String fileContent) {
        List<String> methods = new ArrayList<>();

        // static void printX(X args)
        Pattern pattern = Pattern.compile("(static).+\\((\\s+)?.+\\{");
        Matcher matcher = pattern.matcher(fileContent);
        StringBuilder s1static = new StringBuilder();
        while (matcher.find()) {
            String method = fileContent.substring(matcher.start(), matcher.end());
            // is in JavaDoc or in cmt
            int metPos = fileContent.indexOf(method);
            int firstCharPos = fileContent.lastIndexOf("\n", metPos) + 1;
            char firstChar = fileContent.charAt(firstCharPos);
            String line = fileContent.substring(firstCharPos,
                    fileContent.indexOf("\n", firstCharPos));
            if (line.startsWith("//") // cmt1
                    || (line.contains("*") && line.indexOf("*") < line.indexOf(method)) // java doc
                    || isInBigCmt(fileContent, metPos)
            ) {
                continue;
            }

            if (method.contains("(")) {
                method = method.replaceAll("\\s+", " ");
                s1static.append(method).append("\n");
            }
        }

        // method_name(para_type para_name,...)
        // static void printX(X args) -> printX(X args)
        pattern = Pattern.compile("\\S+\\((.+)*\\)");
        // pattern = Pattern.compile("\\S+\\((\\w+|\\s+|,+|.+|<+|>+)*\\)");
        matcher = pattern.matcher(s1static);
        StringBuilder s2method = new StringBuilder();
        while (matcher.find()) {
            String method = s1static.substring(matcher.start(), matcher.end());
            if (method.contains("...") || !method.contains(".")) {
                method = method.replaceAll("\\.\\.\\.", "");
                s2method.append(method.substring(0, method.indexOf(")") + 1)).append("\n");
            }
        }

        // method_name
        // printX(X args) -> printX
        pattern = Pattern.compile("^\\w+", 'm');
        matcher = pattern.matcher(s2method);
        StringBuilder s3methodName = new StringBuilder();
        while (matcher.find()) {
            String method = s2method.substring(matcher.start(), matcher.end()) + "\n";
            s3methodName.append(method);
        }

        // para_type para_name
        // printX(X args) -> X args
        pattern = Pattern.compile(
                "(\\w+\\s\\w+)|(\\w+\\[]+\\s\\w+)|(\\w+<(\\w+|\\?)>\\s\\w+)", 'm');
        matcher = pattern.matcher(s2method);
        StringBuilder s4para = new StringBuilder();
        while (matcher.find()) {
            s4para.append(s2method.substring(matcher.start(), matcher.end())).append("\n");
        }

        // para_name
        // X args hoặc X[] args -> args
        pattern = Pattern.compile("\\S+$", 'm');
        matcher = pattern.matcher(s4para);
        StringBuilder s5args = new StringBuilder();
        Set<String> setArgs = new HashSet<>();
        while (matcher.find()) {
            String arg = s4para.substring(matcher.start(), matcher.end());
            s5args.append(arg).append("\n");
            setArgs.add(arg);
        }
        // delete args -> sig
        String sig = s2method.toString();
        List<String> listArgs = new ArrayList<>(setArgs);
        Collections.sort(listArgs, Comparator.comparing(String::length).reversed());
        for (String arg : listArgs) {
            sig = sig.replaceAll(" " + arg, "");
        }
        sig = sig.replaceAll(" ", "");
        // System.out.println(s2method + "\n");
        // System.out.println(s4para + "\n");
        // System.out.println(s5args + "\n");
        // System.out.println(s1static + "\n");
        // System.out.println(sig + "\n");

        // para_type...
        // X args hoặc X[] args -> X
        Set<String> paras = new HashSet<>();
        pattern = Pattern.compile("(^\\w+)|(<\\w+>)", 'm');
        matcher = pattern.matcher(s4para);
        StringBuilder s6 = new StringBuilder();
        while (matcher.find()) {
            String para = s4para.substring(matcher.start(), matcher.end());
            if (para.contains("<")) {
                para = para.substring(1, para.length() - 1);
            }
            s6.append(para).append("\n");
            if ('A' <= para.charAt(0) && para.charAt(0) <= 'Z') {
                paras.add(para);
            }
        }
        List<String> listParas = new ArrayList<>(paras);
        Collections.sort(listParas, Comparator.comparing(String::length).reversed());

        // add import
        for (String paraType : listParas) {
            int i = fileContent.indexOf("." + paraType + ";");
            int imp1 = fileContent.lastIndexOf(" ", i) + 1;
            int imp2 = fileContent.indexOf(";", i);
            String importDataType = fileContent.substring(imp1, imp2);
            if (i == -1) {
                if (paraType.length() > 1) {
                    if (paraType.equals("Object")
                            || paraType.equals("Class")
                            || paraType.equals("String")
                            || paraType.equals("Integer")
                            || paraType.equals("Iterable")) {
                        importDataType = "java.lang." + paraType;
                    } else {
                        imp1 = fileContent.indexOf("package") + 8;
                        imp2 = fileContent.indexOf(";", imp1);
                        importDataType = fileContent.substring(imp1, imp2) + "." + paraType;
                    }
                } else {
                    continue;
                }
            }
            sig = sig.replaceAll("\\(" + paraType + ",", "(" + importDataType + ",");
            sig = sig.replaceAll("\\(" + paraType + "\\)", "(" + importDataType + ")");
            sig = sig.replaceAll("\\(" + paraType + "<", "(" + importDataType + "<");

            sig = sig.replaceAll("," + paraType + "\\)", "," + importDataType + ")");
            while (sig.contains("," + paraType + ",")) {
                sig = sig.replaceAll("," + paraType + ",", "," + importDataType + ",");
            }
            sig = sig.replaceAll("," + paraType + "<", "," + importDataType + "<");

            sig = sig.replaceAll("<" + paraType + ">", "<" + importDataType + ">");
        }

        Scanner scanner = new Scanner(sig);
        while (scanner.hasNextLine()) {
            methods.add(scanner.nextLine());
        }
        return methods;
    }

    private static boolean isInBigCmt(String fileContent, int metPos) {
        int openCmt = fileContent.lastIndexOf("/*", metPos);
        int closeCmt = fileContent.lastIndexOf("*/", metPos);
        if (openCmt == -1) {
            return false;
        }
        if (closeCmt == -1) {
            return true;
        }
        return !(openCmt < closeCmt);
    }
}
