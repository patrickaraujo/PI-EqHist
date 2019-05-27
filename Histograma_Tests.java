import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.opencv.core.Core; 
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs; 
import org.opencv.imgproc.Imgproc; 




/**
 * 
 */

/**
 * @author arauj
 *
 */
public class Histograma_Tests {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
		String nomeArquivo = "landscape.jpg";
		File arquivo = new File(nomeArquivo);
		if(!arquivo.exists()) {
			System.out.println("Arquivo \""+nomeArquivo+"\" não encontrado");
			System.exit(0);
		}
		
		//	BufferedImage imagem = ImageIO.read(arquivo);
		
		Mat src = Imgcodecs.imread(nomeArquivo, Imgcodecs.IMREAD_COLOR);
        Mat out = new Mat();
        List<Mat> channels = new ArrayList();
        
        int linhas = src.rows();
		int colunas = src.cols();
        
		/*
	        int linhas = imagem.getWidth();
			int colunas = imagem.getHeight();
		*/
		System.out.println("Dimensões da imagem\n\nLargura:\t"+colunas+" pixels\nAltura:\t"+linhas+" pixels");
		System.out.println("\nVoce quer uma imagem colorida? Digite: \n\n1 -\tSim\nOutro número -\tNão");
		Scanner entrada = new Scanner(System.in);
        int resposta = entrada.nextInt();
        boolean color = false;
        if(resposta == 1)
        	color = true;
        
        if(color) {
        	Imgproc.cvtColor(src, out, Imgproc.COLOR_RGB2HSV);
            Core.split(out, channels);
            Imgproc.equalizeHist(channels.get(2), channels.get(2));
            Core.merge(channels, out);
            Imgproc.cvtColor(out, out, Imgproc.COLOR_HSV2RGB);
        }
        else {
        	Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2GRAY);
        	Imgproc.equalizeHist(src, out);
        }
        HighGui.namedWindow("source");
        HighGui.namedWindow("output");
        HighGui.imshow("source", src);
        HighGui.imshow("output", out);
        Imgcodecs.imwrite("output.png", out);
        HighGui.waitKey();
	}

}
