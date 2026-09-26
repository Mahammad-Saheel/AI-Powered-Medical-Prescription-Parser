package com.md.prescriptionparser.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class OcrService {

    public String extractText(File file) throws Exception {

        Tesseract tesseract = new Tesseract();

        // Set this to the folder that contains eng.traineddata
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");

        tesseract.setLanguage("eng");

        return tesseract.doOCR(file);
    }
}