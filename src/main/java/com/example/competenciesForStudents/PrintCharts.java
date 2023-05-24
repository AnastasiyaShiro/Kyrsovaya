package com.example.competenciesForStudents;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;

/**
 * Класс для реализации интерфейса Printable (печати) для печати диаграмм.
 * @author Щербак Анастасия Романовна
 * @version 0.7
 */
public class PrintCharts implements Printable
{
    private BufferedImage img;

    public PrintCharts(BufferedImage img)
    {
        this.img = img;
    }

    public int print(Graphics g, PageFormat pf, int pageIndex)//используется для отправки содержимого для печати на принтер
    {
        if (pageIndex != 0) {
            return NO_SUCH_PAGE;//возвращает int, т.к. используется для определения статуса печати страницы.
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, 0, 0, null);//ImageObserver - интерфейс, используемый для получения информации о
//процессе загрузки изображений. null означает, что не хотим получать информацию о процессе загрузки изображения и ждать,
//пока изображение полностью загрузится, что может занимать некоторое время.
        return PAGE_EXISTS;//продолжает печать следующей страницы
    }

    public void printing(String name)
    {
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        Paper paper = new Paper();
        paper.setImageableArea(0.0, 0.0, 500, 800);
        pf.setPaper(paper);
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File(name));//для чтения изображения из файла по заданному имени файла и создания
            //объекта Image, который можно использовать для отображения изображения в графическом контексте.
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        Printable printable = new PrintCharts(img);
        job.setPrintable(printable, pf);
        try
        {
            job.print();
        }
        catch (PrinterException e)
        {
            e.printStackTrace();
        }
    }
}
