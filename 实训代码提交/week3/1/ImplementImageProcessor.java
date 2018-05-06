import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

import imagereader.IImageProcessor;

public class ImplementImageProcessor implements IImageProcessor {

	@Override
	public Image showChanelR(Image sourceImage) {
		ColorFilter redFilter = new ColorFilter(1);
		//FilteredImageSource传入参数为原图像与滤镜
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(sourceImage.getSource(), redFilter)); 
	}

	@Override
	public Image showChanelG(Image sourceImage) {
		ColorFilter greenFilter = new ColorFilter(2);
		//FilteredImageSource传入参数为原图像与滤镜
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(sourceImage.getSource(), greenFilter));  
	}

	@Override
	public Image showChanelB(Image sourceImage) {
		ColorFilter blueFilter = new ColorFilter(3);
		//FilteredImageSource传入参数为原图像与滤镜
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(sourceImage.getSource(), blueFilter));
	}

	@Override
	public Image showGray(Image sourceImage) {
		ColorFilter grayFilter = new ColorFilter(4);
		//FilteredImageSource传入参数为原图像与滤镜
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(sourceImage.getSource(), grayFilter)); 
	}
	//继承抽象类RGBImageFilter
	class ColorFilter extends RGBImageFilter{  
        private int colorNum;  
      
        public ColorFilter(int choose){  
            colorNum = choose;  
            canFilterIndexColorModel = true;  
        }  
          
        public int filterRGB(int x, int y, int rgb){
        	//保留透明度Alpha与red
            if(colorNum==1){  
                return (rgb & 0xffff0000);  
            }
            //保留透明度Alpha与green
            else if(colorNum==2){  
                return (rgb & 0xff00ff00);  
            }
            //保留透明度Alpha与blue
            else if(colorNum==3){  
                return (rgb & 0xff0000ff);  
            }
            //利用NTSC推荐的彩色图到灰度图的转换公式，保留透明度Alpha与灰度
            else{  
                int g = (int)(((rgb & 0x00ff0000) >> 16)*0.299 + ((rgb & 0x0000ff00) >> 8)*0.587 + ((rgb & 0x000000ff))*0.114);  
                return ((rgb & 0xff000000) + (g << 16) + (g << 8) + g);  
            }  
        }
	}
}
