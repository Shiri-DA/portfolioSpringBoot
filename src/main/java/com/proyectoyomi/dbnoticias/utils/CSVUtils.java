package com.proyectoyomi.dbnoticias.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {
    // La lectura del CSV se basa en que cada nueva línea tiene un salto de línea, te da
    // los datos de cada línea en una sola String que posteriormente extrae mediante otra función
    public static List<String> readCSV(MultipartFile file) {
        List<String> records = new ArrayList<>();
        try {
            String line;
            InputStream is = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                records.add(line);
            }
        } catch (IOException e) {
            System.err.println(e);
        };
        return records;
    }

}
