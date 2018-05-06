import static org.junit.Assert.*;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.stream.FileImageInputStream;

import org.junit.Test;

import imagereader.IImageIO;
import imagereader.IImageProcessor;

public class ImageProcessingTest {
    private String pathPic1 = "/home/administrator/Desktop/bmptest/1.bmp";
    private String pathPic2 = "/home/administrator/Desktop/bmptest/2.bmp";
    //���Ժ�ɫ�˾�
    @Test
    public void testshowChanelR() throws IOException {
        IImageIO imageioer = new ImplementImageIO();
        //���Ե�һ��ͼƬ
        Image goalRed = imageioer.myRead("/home/administrator/Desktop/bmptest/goal/1_red_goal.bmp");
        Image sourceRed = imageioer.myRead(pathPic1);
        File fileRed1 = new File("/home/administrator/Desktop/bmptest/source/1_red_source.bmp");
        File fileRed2 = new File("/home/administrator/Desktop/bmptest/goal/1_red_goal.bmp");
        testFunc(fileRed1,fileRed2,goalRed,sourceRed); 
        //���Եڶ���ͼƬ
        Image goalRed2 = imageioer.myRead("/home/administrator/Desktop/bmptest/goal/2_red_goal.bmp");
        Image sourceRed2 = imageioer.myRead(pathPic2);
        File fileRed21 = new File("/home/administrator/Desktop/bmptest/source/2_red_source.bmp");
        File fileRed22 = new File("/home/administrator/Desktop/bmptest/goal/2_red_goal.bmp");
        testFunc(fileRed21,fileRed22,goalRed2,sourceRed2);
     }
        
    //������ɫ�˾�
    @Test
    public void testshowChanelG() throws IOException {
        IImageIO imageioer = new ImplementImageIO();
        //���Ե�һ��ͼƬ
        Image goalGreen = imageioer.myRead("/home/administrator/Desktop/bmptest/goal/1_green_goal.bmp");
        Image sourceGreen = imageioer.myRead(pathPic1);
        File fileGreen1 = new File("/home/administrator/Desktop/bmptest/source/1_green_source.bmp");
        File fileGreen2 = new File("/home/administrator/Desktop/bmptest/goal/1_green_goal.bmp");
        testFunc(fileGreen1,fileGreen2,goalGreen,sourceGreen);

        //���Եڶ���ͼƬ
        Image goalGreen2 = imageioer.myRead("/home/administrator/Desktop/bmptest/goal/2_green_goal.bmp");
        Image sourceGreen2 = imageioer.myRead(pathPic2);
        File fileGreen21 = new File("/home/administrator/Desktop/bmptest/source/2_green_source.bmp");
        File fileGreen22 = new File("/home/administrator/Desktop/bmptest/goal/2_green_goal.bmp");
        testFunc(fileGreen21,fileGreen22,goalGreen2,sourceGreen2);
    }
        
    //������ɫ�˾�
    @Test
    public void testshowChanelB() throws IOException {
        IImageIO imageioer = new ImplementImageIO();
        //���Ե�һ��ͼƬ
        Image goalBlue = imageioer.myRead("/home/administrator/Desktop/bmptest/goal/1_blue_goal.bmp");
        Image sourceBlue = imageioer.myRead(pathPic1);
        File fileBlue1 = new File("/home/administrator/Desktop/bmptest/source/1_blue_source.bmp");
        File fileBlue2 = new File("/home/administrator/Desktop/bmptest/goal/1_blue_goal.bmp");
        testFunc(fileBlue1,fileBlue2,goalBlue,sourceBlue);
                
        //���Եڶ���ͼƬ
        Image goalBlue2 = imageioer.myRead("/home/administrator/Desktop/bmptest/goal/2_blue_goal.bmp");
        Image sourceBlue2 = imageioer.myRead(pathPic2);
        File fileBlue21 = new File("/home/administrator/Desktop/bmptest/source/2_blue_source.bmp");
        File fileBlue22 = new File("/home/administrator/Desktop/bmptest/goal/2_blue_goal.bmp");
        testFunc(fileBlue21,fileBlue22,goalBlue2,sourceBlue2);
    }
    //���ԻҶ��˾�
    @Test
    public void testshowGray() throws IOException { 
        IImageIO imageioer = new ImplementImageIO();
        Image goalGray = imageioer.myRead("/home/administrator/Desktop/bmptest/goal/1_gray_goal.bmp");
        Image sourceGray = imageioer.myRead(pathPic1);
        File fileGray1 = new File("/home/administrator/Desktop/bmptest/source/1_gray_source.bmp");
        File fileGray2 = new File("/home/administrator/Desktop/bmptest/goal/1_gray_goal.bmp");
        testFunc(fileGray1,fileGray2,goalGray,sourceGray);

        Image goalGray2 = imageioer.myRead("/home/administrator/Desktop/bmptest/goal/2_gray_goal.bmp");
        Image sourceGray2 = imageioer.myRead(pathPic2);
        File fileGray21 = new File("/home/administrator/Desktop/bmptest/source/2_gray_source.bmp");
        File fileGray22 = new File("/home/administrator/Desktop/bmptest/goal/2_gray_goal.bmp");
        testFunc(fileGray21,fileGray22,goalGray2,sourceGray2);
    }
    public void testFunc(File file1, File file2, Image goal, Image source) throws IOException{
        IImageProcessor processor = new ImplementImageProcessor();
        //���Ե�һ��ͼƬ
        Image target = processor.showGray(source);
        //����λͼ�ĸ߶��Լ�����Ƿ�һ��
        assertEquals(goal.getHeight(null), target.getHeight(null));
        assertEquals(goal.getWidth(null), target.getWidth(null));
        //��������ֵ�Ƿ�һ��
        int size= goal.getHeight(null) * goal.getWidth(null);
        //��ȡ����ͼƬ��λͼ���ݡ�
        byte imagecolor0[] = new byte[size];
        byte imagecolor1[] = new byte[size];
        FileImageInputStream fileSource = new FileImageInputStream(file1);
        FileImageInputStream fileGoal = new FileImageInputStream(file2);
        //����������λͼ��Ϣ�Ĳ���
        byte temp[] = new byte[500];
        fileSource.read(temp,0,messageLength);
        fileGoal.read(temp,0,messageLength);
        fileSource.read(imagecolor0, 0, size);
        fileGoal.read(imagecolor1, 0, size);
        assertEquals(imagecolor1, imagecolor1);
        fileSource.close();
        fileGoal.close();
    }
}

