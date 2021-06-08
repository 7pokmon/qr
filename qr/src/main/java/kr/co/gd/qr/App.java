package kr.co.gd.qr;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class App extends JFrame {
	static Logger log = LoggerFactory.getLogger(App.class);
    public static void main( String[] args ) throws WriterException, IOException {
    	
    	// 1. QR에 어떤 컨텐츠(문자열 영문자 4000자 정도)를 추가할 것인가? -> 다른API를 통해서 획득
    	
    	QrService qrService = new QrService();
    	String userName = qrService.getUserName();
    	String systemInfo = qrService.getSystemInfo();
        String networkInfo = qrService.getNetworkInfo();

    	StringBuffer contents = new StringBuffer();
    	contents.append(userName);
    	log.info(userName);
        contents.append(", "+systemInfo);
        log.info(systemInfo);
        contents.append(", "+networkInfo);
        log.info(networkInfo);
    	
    	// 2. QR 생성 -> QR에 컨텐츠 추가
    	
    	QRCodeWriter qrWriter = new QRCodeWriter();
    	// BitMatrix martix = qrWriter.encode(컨텐츠, QR종류(바코드포멧), w, h)
    	// 이미지
    	BitMatrix matrix = qrWriter.encode(contents.toString(), BarcodeFormat.QR_CODE, 300, 300);
    	// 설정(색상)
    	// MatrixToImageConfig config = new MatrixToImageConfig(0xFFFFFFFF, 0xFF000000); //qr색상(0x:16진수), 배경색상
    	// 두개의 설정 매개변수를 이용하여 이미지 생성
    	BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);
    	
    	// 3. QR 저장
    	String imamgeFileName = "myqr.png"; // 생성될 img 이름
    	ImageIO.write(qrImage, "png", new File(imamgeFileName)); // 메모리안의이미지, 확장자, 파일생성
    	
    	// 4. QR 출력 -> web이면 jsp view -> pcApp이면 swing frame -> android앱
    	
    	App app = new App();
    	app.setTitle("QR");
    	app.setLayout(new FlowLayout());
    	
    	ImageIcon icon = new ImageIcon(imamgeFileName);
    	JLabel imgLabel = new JLabel(icon);
    	app.add(imgLabel);
    	
    	app.setSize(400, 400);
    	app.setVisible(true);
    	app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
