package com.example.renata.Model;

import javafx.concurrent.Task;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class PDFtoJPGConverter extends Task<List<Image>> {
    String in;

    public PDFtoJPGConverter(String in) {
        this.in = in;
    }

    public static Image convertToFxImage(BufferedImage image){
        WritableImage wr = null;
        if (image != null){
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x=0;x<image.getWidth();x++){
                for (int y=0;y< image.getHeight();y++){
                    pw.setArgb(x,y, image.getRGB(x,y));
                }
            }
        }
        return new ImageView(wr).getImage();
    }

    @Override
    protected List<Image> call() throws Exception {
        List<Image> imageList = new ArrayList<>();
        PDDocument pd = PDDocument.load(new File(in));
        PDFRenderer renderer = new PDFRenderer(pd);
        int count = pd.getNumberOfPages();
        for (int i=0;i<pd.getNumberOfPages();i++)
        {
            BufferedImage pdfImage =  renderer.renderImageWithDPI(i,96);
            imageList.add(convertToFxImage(pdfImage));
            this.updateProgress(i,count);

        }
        return imageList;
    }
}
