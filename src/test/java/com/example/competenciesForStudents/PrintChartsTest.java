package com.example.competenciesForStudents;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

class PrintChartsTest {
    BufferedImage image;
    PrintCharts pc;
    Graphics g;
    PageFormat pf;
    int pageIndex;
    PrinterJob job = PrinterJob.getPrinterJob();
    Paper paper;
    String name;
    @BeforeEach
    void setUp()
    {
        name="Диаграмма пирога.png";
        try {
            image = ImageIO.read(new File(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pc=new PrintCharts(image);
        pf = job.defaultPage();
        paper = new Paper();
        paper.setImageableArea(0.0, 0.0, 500, 800);
        pf.setPaper(paper);
        pageIndex=1;
    }

    @Test
    void print()
    {
        Assertions.assertEquals(pc.print(g, pf,pageIndex),1);
    }
}