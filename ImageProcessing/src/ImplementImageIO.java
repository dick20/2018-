import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import imagereader.IImageIO;

public class ImplementImageIO implements IImageIO {
    private int messageLength = 54;
    private int [] tempByte = {24,16,8};

    @Override
    public Image myRead(String filePath) throws IOException {
        //��ȡ·��
        File file = new File(filePath);
        Image image = null;
        try{
            FileImageInputStream in = new FileImageInputStream(file);
            //���ڶ�ȡbyte�ļ�
            byte message[] = new byte[messageLength];
            in.read(message,0,messageLength);
            //��message����ȡͼ��Ĵ�С����ȣ��߶ȡ�����byteΪ8��bit����Ҫ��λ������
            int size = (int)((message[37] & 0xff)<<tempByte[0] | (message[36] & 0xff)<<tempByte[1]| (message[35] & 0xff)<<tempByte[2] | (message[34] & 0xff));
            int width = (int)((message[21] & 0xff)<<tempByte[0] | (message[20] & 0xff)<<tempByte[1] | (message[19] & 0xff)<<tempByte[2] | (message[18] & 0xff));
            int height = (int)((message[25] & 0xff)<<tempByte[0] | (message[24] & 0xff)<<tempByte[1] | (message[23] & 0xff)<<tempByte[2] | (message[22] & 0xff));
            //�������λ��
            int empty = size / height - 3 * width;
            //����λ��Ϊ4ʱ������Ϊ0
            if(empty == 4){
                empty = 0;
            }
            
            byte wholeImage[] = new byte[size];
            in.read(wholeImage,0,size);
            int numOfPixel = height*width;
            int pixel[] = new int[numOfPixel];
            int k = 0;
            //�������ϣ��������Ҵ洢��ͼ��
            for (int i = height-1; i >= 0 ; i--) {
                for (int j = 0; j < width; j++ ) {
                    pixel[i*width+j] = (int)((0xff)<<tempByte[0] | (wholeImage[k+2] & 0xff)<<tempByte[1] | (wholeImage[k+1] & 0xff)<<tempByte[2] | (wholeImage[k] & 0xff));
                    k += 3; 
                    //���ص���ǰһλ
                }
                k += empty; 
                //���һ�У����Ͽ����
            }
            in.close();
            //����ͼ��
            image = Toolkit.getDefaultToolkit().createImage((ImageProducer) new MemoryImageSource(width, height, pixel, 0, width));
        }
        catch (IOException e) {
            e.printStackTrace(); 
        } 
        return image;
    }

    @Override
    public Image myWrite(Image image, String filePath) throws IOException {
        try{
            File out = new File(filePath + ".bmp");
            //����java��api��ȡ��Ҫ�����ͼƬ
            BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D bGr = bimage.createGraphics();
            bGr.drawImage(image, 0, 0, null);
            bGr.dispose();
            ImageIO.write(bimage, "bmp", out);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return image;
    }
}
