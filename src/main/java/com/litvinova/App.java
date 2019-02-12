package com.litvinova;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class App {

    private void CreateFileSpecifiedSize(long size) {

        try (RandomAccessFile f = new RandomAccessFile("BigFile", "rw")) {
            f.setLength(size);
        } catch (Exception e) {
            try (FileWriter errorFile = new FileWriter("error.txt")) {
                errorFile.write(e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void PingDomain(String domain) {

        try {

            final int countPackages = 1;
            final int timeoutInSec = 10;
            final String pingCommand = "ping " + "-c "+ countPackages + " -W "+ timeoutInSec + " " + domain;

            Process p = Runtime.getRuntime().exec(pingCommand);
            InputStream inputStream = p.getInputStream();
            String output = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            System.out.println(output);

        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    private void XmlToJson(String xmlName, String jsonPath) {

        InputStream xmlStream = getClass().getResourceAsStream(xmlName);

        try {
            String xml = IOUtils.toString(xmlStream, StandardCharsets.UTF_8);

            JSONObject xmlToJsonObj = XML.toJSONObject(xml);

            try (FileWriter writer = new FileWriter(jsonPath)) {
                writer.write(xmlToJsonObj.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        App app = new App();
        app.CreateFileSpecifiedSize(1024L*1024L*1024L);
        app.PingDomain("infotecs.ru");
        app.XmlToJson("/xml.xml", "json.json");

    }
}
