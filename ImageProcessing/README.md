##1.ImplementImageIO类实现了图片的读取myRead()与存储myWrite()。
>* 其中myRead函数通过路径得到图片的信息，从前54个byte中找到图像的大小，宽度，高度。然后根据从下至上，从左至右的顺序读取将图像的像素信息存入int类型数组。 最后利用Toolkit.getDefaultToolkit().createImage()函数创建并返回图像。
>* 而myWrite()函数直接利用java的API createGraphics()等来将图片存储。

##2.ImplementImageProcessor类实现对于图片色彩的处理。
>* 根据 Toolkit.getDefaultToolkit().createImage()函数中传入参数过滤器的不同来决定创建图片的色彩。
>* 而过滤器ColorFilter类继承抽象类RGBImageFilter，实现filterRGB()函数返回一个整数。

##3.ImageProcessingTest类是利用Junit来测试图像处理的正确性。
>* 测试的方法包括对于对比目标图像与生成图像的高度，宽度与像素点。
>* 高度与宽度可以通过API的getHeight(null)与getHeight(null)得到；
>* 而像素点的对比，我仿照myRead函数将图像先转化为FileImageInputStream，再来对比这个的正确性。