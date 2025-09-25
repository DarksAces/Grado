/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.*;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class Funciones {

    public static void createFolder(String fileName) {
        File folder = new File(fileName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public static void createFile(String path, String fileName, String content) throws IOException {
        File file = new File(path, fileName);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        bw.write(content);
        bw.newLine();
        bw.close();
    }

    public static String[] showListFiles(String path) {
        File folder = new File(path);
        if (folder.exists() && folder.isDirectory()) {
            return folder.list();
        }
        return null;
    }

    public static String showFile(String path, String fileName) throws IOException {
        File file = new File(path, fileName);
        if (!file.exists()) return "";
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        return sb.toString();
    }

    public static boolean overWriteFile(String path, String fileName, String newContent) throws IOException {
        File file = new File(path, fileName);
        if (!file.exists()) return false;
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(newContent);
        bw.close();
        return true;
    }

    public static void deleteFile(String path, String fileName) {
        File file = new File(path, fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    public static int countChars(String path, String fileName) throws IOException {
        File file = new File(path, fileName);
        if (!file.exists()) return -1;
        BufferedReader br = new BufferedReader(new FileReader(file));
        int count = 0;
        int c;
        while ((c = br.read()) != -1) {
            count++;
        }
        br.close();
        return count;
    }

    public static int countWords(String path, String fileName) throws IOException {
        File file = new File(path, fileName);
        if (!file.exists()) return -1;
        BufferedReader br = new BufferedReader(new FileReader(file));
        int count = 0;
        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.trim().split("\\s+");
            if (!line.isBlank()) count += words.length;
        }
        br.close();
        return count;
    }

    public static String swapWords(String path, String fileName, String oldWord, String newWord) throws IOException {
        File file = new File(path, fileName);
        if (!file.exists()) return "";
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            content.append(line.replace(oldWord, newWord)).append("\n");
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(content.toString());
        bw.close();

        return content.toString();
    }

//   public static void printPDF(String path, String fileName) {
//    File inputFile = new File(path, fileName);
//    if (!inputFile.exists()) return;
//
//    String outputFileName = fileName.replaceAll("\\.[^.]+$", "") + ".pdf";
//    File outputFile = new File(path, outputFileName);
//
//    try (
//        BufferedReader br = new BufferedReader(new FileReader(inputFile));
//        PdfWriter writer = new PdfWriter(outputFile);
//        PdfDocument pdf = new PdfDocument(writer);
//        Document document = new Document(pdf)
//    ) {
//        String line;
//        while ((line = br.readLine()) != null) {
//            document.add(new Paragraph(line));
//        }
//    } catch (IOException e) {
//      
//    }
//}
    
    
    //No me funciona bien debo revisarlo
}

